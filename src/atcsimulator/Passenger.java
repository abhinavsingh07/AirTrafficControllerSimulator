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
public class Passenger {

    private String passengerId;

    public Passenger() {

        this.passengerId = "Passg_" + AtcUtil.getRandomNumber()+"P"+ AtcUtil.getRandomNumber();
    }

    public String getPassengerId() {
        return passengerId;
    }
    
     @Override
    public String toString() {
        return "Passenger{" + "passengerId=" + passengerId + '}';
    }
}
