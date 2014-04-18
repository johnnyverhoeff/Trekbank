/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDatabaseObjects;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Johnny
 */
public class Database {
    private static final String databaseLocationWindows = "E:\\Documents\\Dropbox\\Dropbox\\Trekbank\\Database\\";
    private static final String databaseLocationMac = "/Users/Johnny/Dropbox/Trekbank/Database/";
    
    private static Statement stat;
    
    /**
     * this number indicates the number of users allowed to use the system
     */
    public static long numberOfUsers(){
        return getAllUsers().size();
    }
    
    /**
     * this number indicates the number of workers allowed to do measurments
     */
    public static long numberOfWorkers(){
        return getAllWorkers().size();
    }
    /**
     * this number indicates the number of customers in the database
     */
    public static long numberOfCustomers(){
        return getAllCustomers().size();
    }
    /**
     * this number indicates the number of projects in the database
     */
    public static long numberOfProjects(){
        return getAllProjects().size();
    }
    
    /**
     * This function generates a unique number for primary key in database for table "project"
     * @return returns the unique id for a project
     */
    public static long getNextUniqueProjectId(){
        long maxId = 0;
        for (Project a : getAllProjects()){
            if (a.getId() > maxId){
                maxId = a.getId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * This function generates a unique number for primary key in database for table "customer"
     * @return returns the unique id for a customer
     */
    public static long getNextUniqueCustomerId(){
        long maxId = 0;
        for (Customer c : getAllCustomers()){
            if (c.getId() > maxId){
                maxId = c.getId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * This function generates a unique number for primary key in database for table "user"
     * @return returns the unique id for a user
     */
    public static long getNextUniqueUserId(){
        long maxId = 0;
        for (User user : getAllUsers()){
            if (user.getId() > maxId){
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }
    
    public static long getNextUniqueWorkerId(){
        long maxId = 0;
        for (Worker worker : getAllWorkers()){
            if (worker.getId() > maxId){
                maxId = worker.getId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * This function generates a unique number for primary key in database for table "getestvoorwerp"
     * @return returns the unique id for a tested object
     */
    public static long getNextUniqueTestedObjectId(){
        long maxId = 0;
        for (TestedObject to : getAllTestedObjects()){
            if (to.getId() > maxId){
                maxId = to.getId();
            }
        }
        return maxId + 1;
    }
    
    /**
     * This function connects to a database with specified parameters
     * @param dataBase this indicates the name of the database that you want to connect to
     * @param user this is the username for the database
     * @param password this is the password for the user
     */
    public static void connect(String dataBase, String user, String password){
        try {
            Class.forName("org.h2.Driver");
            String loc = new File("").getAbsolutePath() + "\\Database\\";
            //JOptionPane.showMessageDialog(null, loc);
            //System.exit(0);
            /*String osName = System.getProperties().getProperty("os.name");
            switch (osName) {
                case "Windows 7":
                    loc = databaseLocationWindows;
                    break;
                case "Mac OS X":
                    loc = databaseLocationMac;
                    break;
            }*/
            
            Connection conn = DriverManager.getConnection("jdbc:h2:" + loc + dataBase, user, password);
            stat = conn.createStatement();
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "classnotfoundexception", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            System.err.println("There was an error connecting to the database");
            int answer = JOptionPane.showConfirmDialog(null, "Er kan geen verbinding tot stand worden gebracht met de database. Wilt u het opnieuw proberen ?", "Database melding", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (answer == -1 || answer == 2) {
                System.exit(0);
            } else {
                connect(dataBase, user, password);
            }
        } 
    }
    
    /**
     *
     * @param dataBaseName deletes this database
     */
//    public static void deleteDatabase(String dataBaseName){
//        DeleteDbFiles.execute("~", dataBaseName, true);
//    }
    
    /**
     * You can use this function to get a list of all users that are allowed to use the system
     * @return return an arraylist of users, containing their id's, name's and passwords, if there was an error then it return an empty list 
     */
    public static ArrayList<User> getAllUsers(){
        String name = null;
        try {
            ResultSet rs = stat.executeQuery("select * from gebruikers");
            ArrayList<User> users = new ArrayList<User>();
            while (rs.next()){
                name = rs.getString("name");
                users.add(new User(Integer.parseInt(rs.getString("id")), name, rs.getString("password")));
            }
            return users;
        } catch (SQLException e) {
            System.err.println("There was an error while asking for the users");    
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for user with name: " + name);  
        }
        
        return new ArrayList<User>();
    } 
    
    /**
     * You can use this function to get a list of all workers that are allowed to do measurments
     * @return return an arraylist of workers, containing their id's and name's, if there was an error then it return an empty list 
     */
    public static ArrayList<Worker> getAllWorkers() {
        String name = null;
        try {
            ResultSet rs = stat.executeQuery("select * from workers");
            ArrayList<Worker> workers = new ArrayList<Worker>();
            while (rs.next()){
                name = rs.getString("name");
                workers.add(new Worker(Integer.parseInt(rs.getString("id")), name));
            }
            return workers;
        } catch (SQLException e) {
            System.err.println("There was an error while asking for the workers");    
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for worker with name: " + name);  
        }
        return new ArrayList<Worker>();
    }
    
    /**
     * This function inserts a new user in the database <br>
     * User has to make sure user id is unique <br>
     * @param user the user that has to be inserted
     * @return returns if it inserted a new user
     */
    public static boolean insertNewUser(User user){
        try {
            stat.execute("insert into gebruikers values(" + user.getId() + ",'" + user.getName() + "','" + user.getPassword() + "')");
            JOptionPane.showMessageDialog(null, "Nieuwe gebruiker: \"" + user.getName() + "\" is succevol aangemaakt");
            return true;
        } catch (SQLException e){
            System.err.println("Couldn't create user: \"" + user.getName() + "\"");
            return false;
        }
    }
    
    public static boolean insertNewWorker(Worker worker, boolean message){
        try {
            stat.execute("insert into workers values(" + worker.getId() + ",'" + worker.getName() + "')");
            if (message) JOptionPane.showMessageDialog(null, "Nieuwe werknemer: \"" + worker.getName() + "\" is succevol aangemaakt");
            return true;
        } catch (SQLException e){
            System.err.println("Couldn't create worker: \"" + worker.getName() + "\"");
            return false;
        }
    }
    
    public static long insertNewWorkerWithUniqueId(Worker worker, boolean message){
        long id = getNextUniqueWorkerId();
        Worker w = new Worker(id, worker.getName());
        if (insertNewWorker(w, message)){
            return id;
        } else {
            return -1;
        }  
    }
    
    
    
    /**
     * This function inserts a new user in the database <br>
     * A unique userId is ensured, it ignores the parameters userid <br>
     * @param user the user that has to be inserted, ignoring the userId
     * @return returns the unique userId that was inserted into the database
     */
    public static long insertNewUserWithUniqueId(User user){
        long id = getNextUniqueUserId();
        User u = new User(id, user.getName(), user.getPassword());
        if (insertNewUser(u)){
            return id;
        } else {
            return -1;
        }  
    }
    
    public static ArrayList<User> searchUserByName(String name, boolean caseInsensitive){
        String SQLName = null;
        try {
            ResultSet rs;
            if (caseInsensitive) {
                rs = stat.executeQuery("select * from gebruikers where lower(name) like '%" + name + "%'");
            } else {
                rs = stat.executeQuery("select * from gebruikers where name like '%" + name + "%'");
            }
            ArrayList<User> users = new ArrayList<User>();
            while (rs.next()){
                 SQLName = rs.getString("name");
                 users.add(new User(Long.parseLong(rs.getString("id")), rs.getString("name"), rs.getString("password")));
            }
            return users;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the users"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for user with name: " + SQLName);  
        }
        return new ArrayList<User>();       
    }
    
    public static ArrayList<Worker> searchWorkerByName(String name, boolean caseInsensitive){
        String SQLName = null;
        try {
            ResultSet rs;
            if (caseInsensitive) {
                rs = stat.executeQuery("select * from workers where lower(name) like '%" + name + "%'");
            } else {
                rs = stat.executeQuery("select * from workers where name like '%" + name + "%'");
            }
            ArrayList<Worker> workers = new ArrayList<Worker>();
            while (rs.next()){
                 SQLName = rs.getString("name");
                 workers.add(new Worker(Long.parseLong(rs.getString("id")), rs.getString("name")));
            }
            return workers;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the workers"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for woker with name: " + SQLName);  
        }
        return new ArrayList<Worker>();       
    }
    
    public static void deleteUserByName(String name, boolean messages){
        if (messages) {
            int answer = JOptionPane.showConfirmDialog(null, "Weet u zeker dat u deze gebruiker: \"" + name + "\" wil verwijderen ?\n", "Gebruiker verwijderen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == -1 || answer == 2) {
                return;
            }
        }
        try {
            stat.execute("delete from gebruikers where name = '" + name + "'");
        } catch (SQLException e){
            System.err.print("Error when asking for table: \"gebruiker\"");
            return;
        }
        if (messages){
            JOptionPane.showMessageDialog(null, "Verwijden van gebruiker: \"" + name + "\" is gelukt", "gebruiker verwijderen", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void deleteTestedObject(long id, String date, String discription,boolean messages){
        if (messages) {
            int answer = JOptionPane.showConfirmDialog(null, "Weet u zeker dat u het voorwerp met beschrijving: \"" + discription + "\" wil verwijderen ?\n", "Voorwerp verwijderen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == -1 || answer == 2) {
                return;
            }
        }
        try {
            stat.execute("delete from testedobject where id = " + id + "and discription = '" + discription + "' and date < '" + date + " 23:59:59.999' and date > '" + date + " 00:00:00.000'");
            //System.out.println("Succesfully deleted object with id: " + id + ", date: " + date);
        } catch (SQLException e){
            System.err.println("Error when deleting object with id: " + id + ", date: " + date);
            return;
        }
        if (messages){
            JOptionPane.showMessageDialog(null, "Verwijden van voorwerp met beschrijving: \"" + discription + "\" is gelukt", "Voorwerp verwijderen", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public static void deleteWorkerByName(String name, boolean messages){
        if (messages) {
            int answer = JOptionPane.showConfirmDialog(null, "Weet u zeker dat u deze werknemer: \"" + name + "\" wil verwijderen ?\n", "Werknemer verwijderen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == -1 || answer == 2) {
                return;
            }
        }
        try {
            stat.execute("delete from workers where name = '" + name + "'");
        } catch (SQLException e){
            System.err.print("Error when asking for table: \"workers\"");
            return;
        }
        if (messages){
            JOptionPane.showMessageDialog(null, "Verwijden van werknemer: \"" + name + "\" is gelukt", "Werknemer verwijderen", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static User searchUserByExactName(String name){
        try {
            ResultSet rs = stat.executeQuery("select * from gebruikers where name = '" + name + "'");
            User user = null;
            while (rs.next()) {
                user = new User(Long.parseLong(rs.getString("id")), rs.getString("name"), rs.getString("password"));
            }
            return user;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the users"); 
        }
        return null;
    }
    
    public static Worker searchWorkerByExactName(String name){
        try {
            ResultSet rs = stat.executeQuery("select * from workers where name = '" + name + "'");
            Worker w = null;
            while (rs.next()) {
                w = new Worker(Long.parseLong(rs.getString("id")), rs.getString("name"));
            }
            return w;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the workers"); 
        }
        return null;
    }
    
    
    /**
     * You can use this function to get a list of all customers that are stored in the database
     * @return return an arraylist of customers, containing their id's and name's, if there was an error then it return an empty list
     */
    public static ArrayList<Customer> getAllCustomers(){
        String name = null;
        try {
            ResultSet rs = stat.executeQuery("select * from klant");
            ArrayList<Customer> customers = new ArrayList<Customer>();
            while (rs.next()){
                name = rs.getString("name");
                customers.add(new Customer(Long.parseLong(rs.getString("id")), name));
            }
            return customers;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the customers"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for customer with name: " + name);  
        }
        return new ArrayList<Customer>();
    }
    
    /**
     * This function returns all customers if its name is about the same the searched name
     * @param name this is the search name
     * @param caseInsensitive wheter you want to search case insensitive or case sensitive
     * @return returns a list of customers, if there was an error returns an empty list
     */
    public static ArrayList<Customer> searchCustomerByName(String name, boolean caseInsensitive){
        String SQLName = null;
        try {
            ResultSet rs;
            if (caseInsensitive) {
                rs = stat.executeQuery("select * from klant where lower(name) like '%" + name + "%'");
            } else {
                rs = stat.executeQuery("select * from klant where name like '%" + name + "%'");
            }
            ArrayList<Customer> customers = new ArrayList<Customer>();
            while (rs.next()){
                SQLName = rs.getString("name");
                customers.add(new Customer(Long.parseLong(rs.getString("id")), SQLName));
            }
            return customers;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the customers"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for customer with name: " + SQLName);  
        }
        return new ArrayList<Customer>();
    }
    
    /**
     * This function search for a customer with exactly the name
     * @param name the name of the customer to be searched
     * @return return the found customer, if no customer found returns null
     */
    public static Customer searchCustomerByExactName(String name){
        try {
            ResultSet rs = stat.executeQuery("select * from klant where name = '" + name + "'");
            Customer customer = null;
            while (rs.next()) {
                customer = new Customer(Long.parseLong(rs.getString("id")), rs.getString("name"));
            }
            return customer;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the projects"); 
        }
        return null;
    }
    
    /**
     * This function inserts a new customer in the database <br>
     * If the name is not unique it wil show a dialog asking for a new name <br>
     * User has to make sure customer id is unique <br>
     * @param customer the customer that has to be inserted
     * @return returns if it inserted a new customer
     */
    public static boolean insertNewCustomer(Customer customer){
        if (searchCustomerByExactName(customer.getName()) != null){
            System.err.println("Name: \"" + customer.getName() + "\" is not a unique name");
            String name = JOptionPane.showInputDialog(null, "De naam voor de klant: \"" + customer.getName() + "\" is niet uniek\nVoer een andere naam in,\nof annuleer het aanmaken van deze klant ", "De naam is niet uniek ", JOptionPane.QUESTION_MESSAGE);
            if (name != null) {
                insertNewCustomer(new Customer(customer.getId(), name));
            }
            return false;
        } else {
            try {
                stat.execute("insert into klant values(" + customer.getId() + ", '" + customer.getName() + "')");
            } catch (SQLException e){
                System.err.println("Name: \"" + customer.getName() + "\" is not a unique name");
                String name = JOptionPane.showInputDialog(null, "De naam voor de klant is niet uniek. Voer een andere naam in: ", "De naam is niet uniek ", JOptionPane.QUESTION_MESSAGE);
                if (name != null) {
                    insertNewCustomer(new Customer(customer.getId(), name));
                }
                return false;
            }
            JOptionPane.showMessageDialog(null, "Aanmaken van klant: \"" + customer.getName() + "\" is gelukt", "Klant aanmaken", JOptionPane.INFORMATION_MESSAGE);
            return true;
    
        }
    }
    
    /**
     * This function inserts a new customer in the database <br>
     * A unique customerId is ensured, it ignores the parameters customerId <br>
     * If the name is not unique it wil show a dialog asking for a new name <br>
     * @param customer the customer that has to be inserted, ignoring the customerId
     * @return returns the unique customerId that was inserted into the database
     */
    public static long insertNewCustomerWithUniqueId(Customer customer){
        long id = getNextUniqueCustomerId();
        Customer c = new Customer(id, customer.getName());
        if (insertNewCustomer(c)){
            return id;
        } else {
            return -1;
        }     
    }
    
    /**
     * This function searches for name in paramlist and deletes it <br>
     * It also deletes all the projects corrosponding to this customer <br>
     * And also all tested items corrosponding to these pojects are deleted <br>
     * If succesfully deleted, shows information message
     * @param name the customername to be deleted
     */
    public static void deleteCustomerByName(String name){
        int answer = JOptionPane.showConfirmDialog(null, "Weet u zeker dat u deze klant: \"" + name + "\" wil verwijderen ?\nEn ook alle projecten en geteste voorwerpen van deze klant ?", "Klant verwijderen", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == -1 || answer == 2){
            return;
        }
         ArrayList<Project> projects = null;
        try {
            projects = getProjectByCustomerID(searchCustomerByExactName(name).getId());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Klant: \"" + name + "\" bestaat niet. Er is niks verwijderd", "Klant verwijderen", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            stat.execute("delete from klant where name = '" + name + "'");
            for (Project project : projects){
                deleteProjectByName(project.getName(), false);
            }
        } catch (SQLException e){
            System.err.print("Error when asking for table: \"project\"");
            return;
        }
        JOptionPane.showMessageDialog(null, "Verwijden van klant: \"" + name + "\" is gelukt", "Klant verwijderen", JOptionPane.INFORMATION_MESSAGE);
    }
    
       
    /**
     * You can use this function to get a list of all projects that are stored in the database
     * @return return an arraylist of projects, containing its id's, customer id's and name's, if there was an error then it return an empty list
     */
    public static ArrayList<Project> getAllProjects(){
        String name = null;
        try {
            ResultSet rs = stat.executeQuery("select * from project");
            ArrayList<Project> projects = new ArrayList<Project>();
            while (rs.next()){
                name = rs.getString("name");
                projects.add(new Project(Long.parseLong(rs.getString("id")), Long.parseLong(rs.getString("klantId")), name));
            }
            return projects;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the projects"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for project with name: " + name);  
        }
        return new ArrayList<Project>();
    }
    
    /**
     * You can call this function if you want to know which project belong to a scpecific customer
     * @param customerID the ID of the customer
     * @return returns a list of projects, if there was an error this is an empty list
     */
    public static ArrayList<Project> getProjectByCustomerID(long customerID){
        String name = null;
        try {
            ResultSet rs = stat.executeQuery("select * from project where klantId = " + customerID);
            ArrayList<Project> projects = new ArrayList<Project>();
            while (rs.next()){
                name = rs.getString("name");
                projects.add(new Project(Long.parseLong(rs.getString("id")), customerID, name));
            }
            return projects;
        } catch (SQLException e ){
            System.err.println("There was an error while asking for the projects"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for project with name: " + name);  
        }
        
        return new ArrayList<Project>();
    }
    
    /**
     * This function returns all projects if its name is about the same as the searched name
     * @param name this is the search name
     * @param caseInsensitive wheter you want to search case insensitive or case sensitive
     * @return returns a list of projects, if there was an error returns an empty list
     */
    public static ArrayList<Project> searchProjectByName(String name, boolean caseInsensitive){
        String SQLName = null;
        try {
            ResultSet rs;
            if (caseInsensitive) {
                rs = stat.executeQuery("select * from project where lower(name) like '%" + name + "%'");
            } else {
                rs = stat.executeQuery("select * from project where name like '%" + name + "%'");
            }
            ArrayList<Project> projects = new ArrayList<Project>();
            while (rs.next()){
                 SQLName = rs.getString("name");
                 projects.add(new Project(Long.parseLong(rs.getString("id")), Long.parseLong(rs.getString("klantId")), SQLName));
            }
            return projects;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the projects"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for project with name: " + SQLName);  
        }
        return new ArrayList<Project>();       
    }
    
    /**
     * This function search for a project with exactly the name
     * @param name the name of the project to be searched
     * @return return the found project, if no project found returns null
     */
    public static Project searchProjectByExactName(String name){
        try {
            ResultSet rs = stat.executeQuery("select * from project where name = '" + name + "'");
            Project project = null;
            while (rs.next()) {
                project = new Project(Long.parseLong(rs.getString("id")), Long.parseLong(rs.getString("klantId")), rs.getString("name"));
            }
            return project;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the projects"); 
        }
        return null;
    }
    
    /**
     * This function inserts a new project in the database <br>
     * If the name is not unique it wil show a dialog asking for a new name <br>
     * User has to make sure project id is unique <br>
     * @param project the project that has to be inserted
     */
    public static void insertNewProject(Project project){
        try {
            stat.execute("insert into project values(" + project.getId() + ", " + project.getCustomerId() + ", '" + project.getName() + "')");
        } catch (SQLException e){
            System.err.println("Name: \"" + project.getName() + "\" is not a unique name");
            String name = JOptionPane.showInputDialog(null, "De naam voor het project: \"" + project.getName() + "\" is niet uniek\nVoer een andere naam in\nof annuleer het aanmaken van dit project ", "De naam is niet uniek ", JOptionPane.QUESTION_MESSAGE);
            if (name != null) {
                insertNewProject(new Project(project.getId(), project.getCustomerId(), name));
            }
            return;
        }
        JOptionPane.showMessageDialog(null, "Aanmaken van project: \"" + project.getName() + "\" is gelukt", "Project aanmaken", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * This function inserts a new project in the database <br>
     * A unique projectId is ensured, it ignores the parameters projectId <br>
     * If the name is not unique it wil show a dialog asking for a new name <br>
     * @param project the project that has to be inserted, ignoring the projectId
     * @return returns the unique projectId that was inserted into the database
     */
    public static long insertNewProjectWithUniqueId(Project project){
        long id = getNextUniqueProjectId();
        Project p = new Project(id, project.getCustomerId(), project.getName());
        insertNewProject(p);
        return id;      
    }
    
    /**
     * This function searches for name in paramlist and deletes it <br>
     * NEEDS IMPLEMENTATION TO ALSO DELETE ALL TESTED ITEMS
     * If succesfully deleted, shows information message
     * @param name the projectname to be 
     * @param message whether to show a confirm message or not
     */
    @Deprecated
    public static void deleteProjectByName(String name, boolean message){
        try {
            stat.execute("delete from project where name = '" + name + "'");
        } catch (SQLException e){
            System.err.print("Error when asking for table: \"project\"");
            return;
        }
        if (message) {
            JOptionPane.showMessageDialog(null, "Verwijden van project: \"" + name + "\" is gelukt", "Project verwijderen", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    /**
     * You can use this function to get a list of all tested objects that are stored in the database
     * @return return an arraylist of testedobjects if there was an error then it return an empty list
     */
    public static ArrayList<TestedObject> getAllTestedObjects(){
        String name = null;
        try {
            //ResultSet rs = stat.executeQuery("select * from getestVoorwerp");
            ResultSet rs = stat.executeQuery("select * from testedObject");
            ArrayList<TestedObject> tested = new ArrayList<TestedObject>();
            while (rs.next()){
                //name = rs.getString("beschrijving");
                name = rs.getString("discription");
                //tested.add(new TestedObject(Long.parseLong(rs.getString("id")), Long.parseLong(rs.getString("projectId")), Long.parseLong(rs.getString("meetId")), rs.getString("beschrijving"), rs.getString("timestamp"), rs.getString("user"), Long.parseLong(rs.getString("trekkracht"))));
                tested.add(new TestedObject(Long.parseLong(rs.getString("id")), rs.getString("date"), rs.getString("userName"), rs.getString("workerName"), Long.parseLong(rs.getString("tonnage")), name));
            }
            return tested;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the tested objects"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for tested object with discription: " + name);  
        }
        return new ArrayList<TestedObject>();
    }
    
    /**
     * This function inserts a new tested object in the database <br>
     * User has to make sure testedobject id is unique <br>
     * @param to the testobject that has to be inserted
     * @return returns if it inserted a new testet object
     */
    public static boolean insertNewTestedObject(TestedObject to){
        try {
            //stat.execute("insert into getestvoorwerp values(" + to.getId() + "," + to.getProjectId() + "," + to.getMeetId() + ",'" + to.getBeschrijving() + "','" + to.getTimeStamp().toString() + "','" + to.getUserName() + "'," + to.getTrekKracht() + ")");
            stat.execute("insert into testedObject values(" + to.getId() + ",'" + to.getTimeStamp().toString() + "','" + to.getUserName() + "','" + to.getWorkerName() + "'," + to.getTrekKracht() + ",'" + to.getBeschrijving() + "')");
            return true;
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Toevoegen van voorwep met id: \"" + to.getId() + "\" is mislukt\nEr bestaat al een voorwerp met hetzelfde id nummer", "Voorwerp opslaan mislukt", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * This function inserts a new tested object in the database <br>
     * A unique Id is ensured, it ignores the parameters testedobjectId <br>
     * @param to the tested object that has to be inserted, ignoring the testedobjectId
     * @return returns the unique testedobjectId that was inserted into the database
     */
    @Deprecated
    public static long insertNewTestedProjectWithUniqueId(TestedObject to){
        long id = getNextUniqueTestedObjectId();
        //TestedObject t = new TestedObject(id, to.getProjectId(), to.getMeetId(), to.getBeschrijving(), to.getTimeStamp().toString(), to.getUserName(), to.getTrekKracht());
        TestedObject t = new TestedObject(id, to.getTimeStamp().toString(), to.getUserName(), to.getWorkerName(), to.getTrekKracht(), to.getBeschrijving());
        insertNewTestedObject(t);
        return id;      
    }
    
    
    /**
     * This function returns all tested objects if its name is about the same the searched name
     * @param discription this is the search name
     * @param caseInsensitive wheter you want to search case insensitive or case sensitive
     * @return returns a list of tested objects, if there was an error returns an empty list
     */
    public static ArrayList<TestedObject> searchTestedObjectByDiscription(String discription, boolean caseInsensitive){
        String SQLName = null;
        try {
            ResultSet rs;
            if (caseInsensitive) {
                rs = stat.executeQuery("select * from testedObject where lower(discription) like '%" + discription + "%'");
            } else {
                rs = stat.executeQuery("select * from testedObject where discription like '%" + discription + "%'");
            }
            ArrayList<TestedObject> tos = new ArrayList<TestedObject>();
            while (rs.next()){
                SQLName = rs.getString("discription");
                tos.add(new TestedObject(Long.parseLong(rs.getString("id")), rs.getString("date"), rs.getString("username"), rs.getString("workername"), Long.parseLong(rs.getString("tonnage")), SQLName));
            }
            return tos;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the tested objects"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for tested object with discription: " + SQLName);  
        }
        return new ArrayList<TestedObject>();
    }
    
    public static ArrayList<TestedObject> searchTestedObject(long id) {
        try {
            ResultSet rs;
            String q = "select * from testedObject where id = " + id;
            
            rs = stat.executeQuery(q);
            ArrayList<TestedObject> tos = new ArrayList<>();
            while (rs.next()){
                tos.add(new TestedObject(Long.parseLong(rs.getString("id")), rs.getString("date"), rs.getString("username"), rs.getString("workername"), Long.parseLong(rs.getString("tonnage")), rs.getString("discription")));
            }
            return tos;
            
        } catch (SQLException e){
            System.err.println("There was an error while asking for the tested objects"); 
        }
        return new ArrayList<>();
    }
    
    public static ArrayList<TestedObject> searchTestedObject(String discription, boolean caseInsensitive, String workerName, String fromDate, String toDate) {
        try {
            ResultSet rs;
            String q;
            if (caseInsensitive) q = "select * from testedObject where lower(discription) like '%" + discription + "%' and lower(workername) like '%" + workerName + "%'";
            else q = "select * from testedObject where discription like '%" + discription + "%' and workername like '%" + workerName + "%'";
            
            q += " and '" + fromDate + "' <= date and '" + toDate + "' >= date";
            rs = stat.executeQuery(q);
            ArrayList<TestedObject> tos = new ArrayList<>();
            while (rs.next()){
                tos.add(new TestedObject(Long.parseLong(rs.getString("id")), rs.getString("date"), rs.getString("username"), rs.getString("workername"), Long.parseLong(rs.getString("tonnage")), rs.getString("discription")));
            }
            return tos;
            
        } catch (SQLException e){
            System.err.println("There was an error while asking for the tested objects"); 
        }
        return new ArrayList<>();
    }
    
    
    @Deprecated
    public static void createDummyWorkers(){
        try { 
            stat.execute("insert into workers values(1, 'Klaasje')");
            stat.execute("insert into workers values(2, 'Jantje')");
            stat.execute("insert into workers values(3, 'Pietje')");
            stat.execute("insert into workers values(4, 'Johannes')");
        } catch (SQLException e){
            System.err.println("There was an error while adding to table \"workers\"");
        }
    }
    
    
    
    /**
     * This function (re)creates the table "gebruikers" with two users
     * @deprecated
     */
    @Deprecated
    public static void createDummyUsers(){
        try {
            stat.execute("create table gebruikers(id int primary key, name varchar(255), password varchar(255), unique (name))");
            stat.execute("insert into gebruikers values(1, 'Johnny Verhoeff', 'a')");
            stat.execute("insert into gebruikers values(2, 'Jan Verhoeff', 'b')");
        } catch (SQLException ex) {
            System.err.println("There was an error while creating table \"gebruikers\" or adding to this table");
        }
    }
    
    /**
     * This function (re)creates the table "klant" with three customers
     * @deprecated
     */
    @Deprecated
    public static void createDummyCustomers(){
        try {
            stat.execute("create table klant(id int primary key, name varchar(255), unique (name))");
            stat.execute("insert into klant values(1, 'Bedrijf 1')");
            stat.execute("insert into klant values(2, 'Bedrijf 2')");
            stat.execute("insert into klant values(3, 'Bedrijf 3')");
        } catch (SQLException ex) {
            System.err.println("There was an error while creating table \"klant\" or adding to this table");
        }
    }
    
    /**
     * This function (re)creates the table "project" with five projects
     * @deprecated
     */
    @Deprecated
    public static void createDummyProjects(){
        try {
            stat.execute("create table project(id int primary key, klantId int, name varchar(255), unique (name))");
            stat.execute("insert into project values(1, 1, 'Project 1')");
            stat.execute("insert into project values(2, 1, 'Project 2')");
            stat.execute("insert into project values(3, 1, 'Project 3')");

            stat.execute("insert into project values(4, 2, 'Project 4')");
            stat.execute("insert into project values(5, 3, 'Project 5')");
        } catch (SQLException ex) {
            System.err.println("There was an error while creating table \"project\" or adding to this table");
        }
    }
    
    /**
     * This function deletes a table
     * @param table the table that has to be deleted
     */
    public static void deleteTable(String table){
        try {
            stat.execute("drop table " + table);
        } catch (SQLException ex) {
            System.err.println("There was an error while deleting table: " + table);
        }
    }

    public static void insertComError(ComError err){
        try {
            stat.execute("insert into comerror values(" + err.getId() + ",'" + err.getDate() + "', '" + err.getMsg() + "')");
        } catch (SQLException e){
            System.err.println("SQLException when inserted error msg: " + err.getMsg());
        }
    }
    @Deprecated
    public static void insertDummyTestedObjects(){
        ArrayList<User> users = getAllUsers();
        ArrayList<Worker> workers = getAllWorkers();

        String[] discriptions = new String[]{"Harp", "Ketting", "Sluiting", "Schakel", "Staaldraad", "Haak"};
        
        int numberOfUsers = users.size();
        int numberOfWorkers = workers.size();

        Timestamp today = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        Date date = new Date(today.getTime());
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        long id = (3 + 1) * (365 + 1) * (60 + 1);
        for (int k = 0; k <= 3; k++) {
            for (int i = 0; i <= 365; i++) {
                for (int j = 0; j <= 60; j++) {
                    double r = Math.random();
                    String userName = users.get((int) (r * numberOfUsers)).getName();
                    String workerName = workers.get((int) (r * numberOfWorkers)).getName();
                    long tonnage = (long) ((long) 100000 * r);
                    int n = (int) (50 * r);
                    int m = (int) (5 * r);
                    TestedObject t = new TestedObject(id, sqlFormat.format(date), userName, workerName, tonnage, n + " keer: " + discriptions[m]);
                    Database.insertNewTestedObject(t);
                    id--;                    
                }
                date = new Date(date.getTime() - (1000 * 60 * 60 * 24));
            }
        }
    }
    
    
    /**
     * You can use this function to get a list of all licenses that are stored in the database
     * @return return an arraylist of licenses, containing if there was an error then it return an empty list
     */
    public static ArrayList<License> getAllLicenses(){
        try {
            ResultSet rs = stat.executeQuery("select * from license");
            ArrayList<License> licenses = new ArrayList<License>();
            while (rs.next()){
                licenses.add(new License(Integer.parseInt(rs.getString("id")), rs.getString("duedate"), Integer.parseInt(rs.getString("active")), rs.getString("key")));
            }
            return licenses;
        } catch (SQLException e){
            System.err.println("There was an error while asking for the licenses"); 
        } catch (NumberFormatException e){
            System.err.println("There was an faulty id stored in the database for a licenes");  
        }
        return new ArrayList<License>();
    }
    
    public static void updateLicenseWithId(int id, int active) {
        try {
            stat.executeUpdate("UPDATE license SET active=" + active +  "where id = " + id);
        } catch (SQLException e) {
            
        }
    }
    
}
