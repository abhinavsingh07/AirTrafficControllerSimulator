/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atcsimulator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Hp
 */
public class AirTrafficController {

    private String atcId;
    private Airport airportobj;

    public AirTrafficController(Airport airportobj) {
        this.atcId = "ATC_" + new Random(System.currentTimeMillis()).nextInt(20000);
        this.airportobj = airportobj;
    }

    public void aeroplaneApproachAtc(Aeroplane aeroplaneObj) {
        long start = System.currentTimeMillis();
        boolean flag = false;
        AtcUtil.printActivity(aeroplaneObj.getAeroPlaneId() + " Requesting permission to land!");
        //if Runway occupied || Gates not available make aeroplane to wait
        if (airportobj.isIsRunwayOccupied() || !airportobj.isAirportGateAvailable()) {
            AtcUtil.printActivity(aeroplaneObj.getAeroPlaneId() + " May be Runway or Gate occupied! To avoid collison please wait and join the circle queue.");
        }
        
       
        //to stop while loop we are using flag.
        while (!flag) {
             //only one thread can enter this block otherwise while loop continues until thread gets lock.
             //this is object level lock
            synchronized (this) {
                //if Runway not occupied and gate is available enter this if block
                if (!airportobj.isIsRunwayOccupied() && airportobj.isAirportGateAvailable()) {
                    AtcUtil.printActivity("Runway clear! " + aeroplaneObj.getAeroPlaneId() + " You are clear to land.");
                    airportobj.occupyRunway();
                    AtcUtil.printActivity(aeroplaneObj.getAeroPlaneId() + " is Landing on Runway..");
                    AtcUtil.sleepThread(50);
                    airportobj.unOccupyRunway();
                    //add aeroplane to airport Gate/Terminal
                    airportobj.getGates().add(aeroplaneObj);
                    AtcUtil.printActivity(aeroplaneObj.getAeroPlaneId() + " coast to Assigned Gate and Dock");
                    AtcUtil.print("Airport Gates occupancy count=" + airportobj.getGates().size());
                    flag = true;
                    AtcUtil.sleepThread(80);
                }
            }

        }
        //calculating waiting time of aeroplane obejct
        long end = System.currentTimeMillis();
        float diff = (end - start) / 1000F;
        aeroplaneObj.setWaitingTimeOfPlane(diff);
        airportobj.getFightStats().put(aeroplaneObj.getAeroPlaneId(), aeroplaneObj);

    }


        public void printStatistics() {
        AtcUtil.printV1("-----------Final statistics-----------------");
        AtcUtil.printV1("Airport Gates Occupancy count= " + airportobj.getGates().size());
        Map.Entry firstObj = airportobj.getFightStats().entrySet().iterator().next();
        Aeroplane firstPlaneOj = (Aeroplane) firstObj.getValue();
        float minIime = Float.valueOf(firstPlaneOj.getWaitingTimeOfPlane()), maxTime = Float.valueOf(firstPlaneOj.getWaitingTimeOfPlane()), timeTakenEachPlane = 0;
        
        for (Map.Entry elm : airportobj.getFightStats().entrySet()) {
            Aeroplane planeOj = (Aeroplane) elm.getValue();
            float waitTimePlane = Float.valueOf(planeOj.getWaitingTimeOfPlane());
            if (minIime > waitTimePlane) {
                minIime = waitTimePlane;
            }

            if (maxTime < waitTimePlane) {
                maxTime = waitTimePlane;
            }
            timeTakenEachPlane+=waitTimePlane;
            AtcUtil.printV1("Aircraft:"+elm.getKey()+" waiting time= "+String.format("%.2f", planeOj.getWaitingTimeOfPlane())+" seconds");
            AtcUtil.printV1("Aircraft:"+elm.getKey()+" passenger borded count= "+planeOj.getPsgList().size());
        }
        AtcUtil.printV1("Minimum waiting time of all Planes= "+String.format("%.2f", minIime)+" seconds");
        AtcUtil.printV1("Average waiting time of all Planes= "+String.format("%.2f", (timeTakenEachPlane/airportobj.getFightStats().size()))+" seconds");
        AtcUtil.printV1("Maximum waiting time of all Planes= "+String.format("%.2f", maxTime)+" seconds");
        AtcUtil.printV1("Total No. of planes served= " + airportobj.getFightStats().size());

    }
    
    @Override
    public String toString() {
        return "AirTrafficController{" + "atcId=" + atcId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.atcId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AirTrafficController other = (AirTrafficController) obj;
        if (!Objects.equals(this.atcId, other.atcId)) {
            return false;
        }
        return true;
    }

}
