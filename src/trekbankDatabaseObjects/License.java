/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDatabaseObjects;

/**
 *
 * @author Johnny
 */
public class License {
    private int id;
    private String dueDate;
    private int active;
    private String key;
    
    public License(int id, String dueDate, int active, String key) {
        this.id = id;
        this.dueDate = dueDate;
        this.active = active;
        this.key = key;
    }

    public boolean isActive() {
        return (active == 1);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }
    
    public String getKey() {
        return key;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "License{" + "id=" + id + ", dueDate=" + dueDate + ", active=" + active + ", key=" + key + '}';
    }
    
    
}
