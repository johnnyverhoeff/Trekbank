/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

import java.util.ArrayList;
import trekbankDatabaseObjects.Database;
import trekbankDatabaseObjects.TestedObject;

/**
 *
 * @author Johnny
 */
public class CheckTestedObjects {
    public static void main(String... args){
        Database.connect("test", "Johnny", "870393");
        ArrayList<TestedObject> allTestedObjects = Database.getAllTestedObjects();
        int counter = allTestedObjects.size();
        int missedCounter = 0;
        for (int i = 1; i < counter; i++){
            
            if (allTestedObjects.get(i).getId() - allTestedObjects.get(i - 1).getId() != 1) {
                missedCounter++;
                System.out.println(allTestedObjects.get(i - 1));
                System.out.println(allTestedObjects.get(i));
            }
        }
        
        System.out.println("Total amount of tested objects: " + counter);
        System.out.println("Number of missed objects: " + missedCounter);
    }
}
