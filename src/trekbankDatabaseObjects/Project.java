/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDatabaseObjects;

/**
 *
 * @author Johnny
 */
public class Project {
    private long id;
    private long customerId;
    private String name;

    public Project(long id, long customerId, String name) {
        this.id = id;
        this.customerId = customerId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", customerId=" + customerId + ", name=" + name + '}';
    }
    
    
}
