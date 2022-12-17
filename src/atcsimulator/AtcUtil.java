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
public class AtcUtil {

    public static void printActivity(String str) {
        System.out.println(Thread.currentThread().getName() + " :: " + str);
    }

    public static void print(Object str) {
        System.out.println(str + "\n");
    }
    public static void printV1(Object str) {
        System.out.println(str);
    }

    public static void sleepThread(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
            System.out.println("Exception in sleepThread" + ex.getMessage());
        }
    }

    public static int getRandomNumber() {

        int random = new Random().nextInt(100 - 10) + 10;
        return random;
    }

}
