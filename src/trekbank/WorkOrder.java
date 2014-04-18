/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

import java.util.ArrayList;

/**
 *
 * @author Johnny
 */
public class WorkOrder {
    
    private String klant;
    private String orderNumber;
    private ArrayList<MeasureSubProfile> measureObjects;

    public WorkOrder(String klant, String orderNumber, ArrayList<MeasureSubProfile> measureObjects) {
        this.klant = klant;
        this.orderNumber = orderNumber;
        this.measureObjects = measureObjects;
    }

    public String getKlant() {
        return klant;
    }

    public void setKlant(String klant) {
        this.klant = klant;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ArrayList<MeasureSubProfile> getMeasureObjects() {
        return measureObjects;
    }

    public void setMeasureObjects(ArrayList<MeasureSubProfile> measureObjects) {
        this.measureObjects = measureObjects;
    }

    @Override
    public String toString() {
        return "WorkOrder{" + "klant=" + klant + ", orderNumber=" + orderNumber + ", measureObjects=" + measureObjects + '}';
    }
    
    
}
