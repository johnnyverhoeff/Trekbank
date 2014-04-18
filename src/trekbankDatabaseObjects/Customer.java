/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDatabaseObjects;

/**
 *
 * @author Johnny
 */
public class Customer {
    private long id;
    private String name;
    //private ArrayList<Project> projects;

    /**
     *
     * @param id is the primary key for the Customer, this is a unique number
     * @param name is the name for the Customer
     */
    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
        //projects = new ArrayList<>();
    }
    
//    /**
//     *
//     * @param id is the primary key for the Customer, this is a unique number
//     * @param name is the name for the Customer
//     * @param projects is the list of projects that are related to this customer
//     */
//    public Customer(long id, String name, ArrayList<Project> projects) {
//        this.id = id;
//        this.name = name;
//        //this.projects = projects;
//    }

    /**
     *
     * @return returns the unique primary key for the Customer
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return returns the name of the Customer
     */
    public String getName() {
        return name;
    }
    
//    /**
//     *
//     * @return returns the list of projects from this customer
//     */
//    public ArrayList<Project> getProjects() {
//        return projects;
//    }
    
    

    /**
     *
     * @return returns a string representation of the Customer
     */
    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + '}';
    }
    
    
    
    
}
