/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atcsimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Hp
 */
public class Aeroplane {

    private static final int aeroplaneCapacity = 50;
    private String aeroPlaneId;
    private boolean isThreadVisited = false;
    private boolean isPassgEmbAndDisembCompleted = false;
    private boolean isRefulingCompleted = false;
    private boolean isRefillSuppliesCompleted = false;
    private int airportGateNoAssigned;
    private List<Passenger> psgList;
    private float waitingTimeOfPlane;

    public Aeroplane() {

        this.aeroPlaneId = "Plane_" + AtcUtil.getRandomNumber() + "A" + AtcUtil.getRandomNumber();
        this.psgList = new ArrayList<>(aeroplaneCapacity);
        this.fillPassengersToAeroplane(false);
    }

    public void activityAfterOccupingGate(Airport airportobj) {
        //in actual scenario first all passenger deboard the plane
        this.passengerDisembarking();
        //then borad the plane.Thats why taken in same thread.
        this.passengerEmbarking();
        this.isPassgEmbAndDisembCompleted = true;
        this.confirmAndTakeOff(airportobj);

    }


    public void passengerEmbarking() {
        this.fillPassengersToAeroplane(true);

    }

    public void passengerDisembarking() {
        AtcUtil.printActivity("Passenger Disembarking Passenger count=" + this.psgList.size());

        for (int i = 0; i < this.psgList.size();) {
            Passenger psg = this.psgList.get(i);
            AtcUtil.printActivity("Deboarding " + psg.getPassengerId());
            this.psgList.remove(i);
            AtcUtil.sleepThread(80);
        }

    }

    public void refuelAeroplane(Airport airportobj) {
        if (airportobj.isIsFuellingTruckOccupied()) {
            AtcUtil.printActivity("Waiting for Refuelling Truck");
        }

        boolean flag = false;
        //looping continues until thread gets lock
        while (!flag) {
            //this is class level lock. Only one instance of aeroplane class at a time will allow to enter this block using thread.
            synchronized (Aeroplane.class) {
                if (!airportobj.isIsFuellingTruckOccupied()) {
                    AtcUtil.printActivity("Occupied Refuelling Truck");
                    airportobj.occupyFuelTruck();
                    AtcUtil.printActivity("Refulling aircraft...this takes some time");
                    AtcUtil.sleepThread(100);
                    AtcUtil.printActivity("Refulling completed!!");
                    airportobj.unOccupyFuelTruck();
                    this.isRefulingCompleted = true;
                    flag = true;
                }

            }
        }

    }

    public void confirmAndTakeOff(Airport airportobj) {
        boolean flag = false;
        while (!flag) {
            //this is class level lock. Only one instance of aeroplane class at a time will allow to enter this block using thread.
            synchronized (Aeroplane.class) {
                if (this.isPassgEmbAndDisembCompleted && this.isRefulingCompleted && !airportobj.isIsRunwayOccupied()) {
                    AtcUtil.printActivity("is ready to takeOff!");
                    AtcUtil.printActivity("approaching to Runway..");
                    airportobj.occupyRunway();
                    AtcUtil.sleepThread(200);
                    airportobj.getGates().remove(this);
                    airportobj.unOccupyRunway();
                    AtcUtil.printActivity("takeoff successfully!!!");
                    AtcUtil.print("Airport Gates occupancy count After takeoff=" + airportobj.getGates().size());
                    flag = true;
                }

            }
        }

    }

    private void fillPassengersToAeroplane(boolean printActivity) {
        Random r = new Random();
        int lowerLimitPass = 1;
        int higherLimitPass = 51;
        int noOfPassInPlane = r.nextInt(higherLimitPass - lowerLimitPass) + lowerLimitPass;
        for (int i = 0; i < noOfPassInPlane; i++) {
            Passenger passg = new Passenger();
            psgList.add(new Passenger());
            if (printActivity) {
                AtcUtil.printActivity("Boarding " + passg.getPassengerId() + " Now!");
                AtcUtil.sleepThread(50);
            }
        }

        if (printActivity) {
            AtcUtil.printActivity("New Passengers Boarded successfully Passenger count=" + this.psgList.size());
        }
    }

    public String getAeroPlaneId() {
        return aeroPlaneId;
    }

    public boolean isIsThreadVisited() {
        return isThreadVisited;
    }

    public void setIsThreadVisited(boolean isThreadVisited) {
        this.isThreadVisited = isThreadVisited;
    }

    public int getAirportGateNoAssigned() {
        return airportGateNoAssigned;
    }

    public void setAirportGateNoAssigned(int airportGateNoAssigned) {
        this.airportGateNoAssigned = airportGateNoAssigned;
    }

    public float getWaitingTimeOfPlane() {
        return waitingTimeOfPlane;
    }

    public void setWaitingTimeOfPlane(float waitingTimeOfPlane) {
        this.waitingTimeOfPlane = waitingTimeOfPlane;
    }

    public List<Passenger> getPsgList() {
        return psgList;
    }
    @Override
    public String toString() {
        return "Aeroplane{" + "aeroPlaneId=" + aeroPlaneId + ", psgList=" + psgList.size() + '}';
    }
}
