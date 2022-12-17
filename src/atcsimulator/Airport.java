/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atcsimulator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Hp
 */
public class Airport {
    
    private String airportId;
    private static final String AIRPORT_NAME = "Asia Pacific Airport";
    private static final int NO_OF_REFULLING_TRUCKS = 1;
    private static final int NO_OF_RUNWAYS = 1;
    private boolean isRunwayOccupied = false;
    private boolean isFuellingTruckOccupied = false;
    private int noOfGates;
    private List<Aeroplane> gatesList;
    private Map<String, Aeroplane> fightStats = new LinkedHashMap<>();

    public Airport(int noOfGates) {
        this.noOfGates = noOfGates;
        this.gatesList = new ArrayList<>();
        this.airportId = "Airport_" + AtcUtil.getRandomNumber();
    }

    public List<Aeroplane> getGates() {
        return gatesList;
    }

    public boolean isAirportGateAvailable() {
        return gatesList.size() < noOfGates;
    }

    public void occupyRunway() {
        isRunwayOccupied = true;
    }

    public void unOccupyRunway() {
        isRunwayOccupied = false;
    }

    public void occupyFuelTruck() {
        isFuellingTruckOccupied = true;
    }

    public void unOccupyFuelTruck() {
        isFuellingTruckOccupied = false;
    }

    public boolean isIsRunwayOccupied() {
        return isRunwayOccupied;
    }

    public boolean isIsFuellingTruckOccupied() {
        return isFuellingTruckOccupied;
    }

    public static String getAirportName() {
        return AIRPORT_NAME;
    }

    public Map<String, Aeroplane> getFightStats() {
        return fightStats;
    }

    public void setFightStats(Map<String, Aeroplane> fightStats) {
        this.fightStats = fightStats;
    }

    @Override
    public String toString() {
        return "Airport{" + "AirportId=" + airportId + ", No. of Gates=" + noOfGates + ", No. of Runways=" + NO_OF_RUNWAYS + ", Airport Name=" + AIRPORT_NAME + ", No. of Refuelling Trucks=" + NO_OF_REFULLING_TRUCKS +'}';
    }

}
