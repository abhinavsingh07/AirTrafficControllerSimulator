/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atcsimulator;

import java.util.Random;

/**
 *
 * @author Hp
 */
public class ATCSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        int noOfGatesAtAirport = 3;
        int initialNoOfPlanes = 6;

        //initialize airport object
        Airport airportObj = new Airport(noOfGatesAtAirport);
        AtcUtil.print(airportObj);
        AtcUtil.sleepThread(1000);

        //initialize ATC object
        AirTrafficController atcObj = new AirTrafficController(airportObj);
        AtcUtil.print(atcObj);
        AtcUtil.sleepThread(1000);

        //6 planes approching to airpot/atc
        for (int i = 1; i <= initialNoOfPlanes; i++) {
            //create threads of atc object
            Runnable r1 = () -> {
                //creating aeroplane object
                Aeroplane aero = new Aeroplane();
                AtcUtil.print("ATC-Thread :: " + aero.getAeroPlaneId() + " Approaching Airport " + airportObj.getAirportName());
                //calling method of atc class. Every thread calls this using same object so on synchronized on/y one thread enters the sync block
                atcObj.aeroplaneApproachAtc(aero);
            };
            new Thread(r1, "ATC-Thread").start();
        }

        //Process aeroplane object which is present in airport gates.
        boolean flagActivity = false;
        while (true) {

            for (int i = 0; i < airportObj.getGates().size(); i++) {
                //on looping diff objects creates of Aeroplane class
                Aeroplane planeObj = airportObj.getGates().get(i);
                //to stop operation on same aeroplane object which is already taken by thread
                if (!planeObj.isIsThreadVisited()) {
                    
                    planeObj.setIsThreadVisited(true);
                    //create Runnable for aerplane activity. 
                    Runnable aeroplaneAtivity = () -> {
                        planeObj.activityAfterOccupingGate(airportObj);
                    };
                    //create Runnable for refuel activity. 
                    Runnable refuelActivity = () -> {
                        planeObj.refuelAeroplane(airportObj);
                    };
                    //both operations happens concurrently
                    Thread t1 = new Thread(aeroplaneAtivity, "Aeroplane-Passenger-Thread-" + planeObj.getAeroPlaneId());
                    Thread t2 = new Thread(refuelActivity, "Aeroplane-Pilot-Thread-" + planeObj.getAeroPlaneId());
                    t1.start();
                    t2.start();
                }
                flagActivity = true;
                //AtcUtil.sleepThread(500);
            }
            //stop looping after all operations
            if (airportObj.getGates().size() == 0 && flagActivity) {
                atcObj.printStatistics();
                break;
            }

            AtcUtil.sleepThread(500);
        }
        //AtcUtil.print("MAIN EXIT");

    }

}
