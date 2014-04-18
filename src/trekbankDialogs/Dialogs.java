/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDialogs;

import trekbankDatabaseObjects.Customer;
import trekbankDatabaseObjects.User;
import trekbankDatabaseObjects.TestedObject;
import trekbankDatabaseObjects.Worker;
import trekbankDatabaseObjects.Project;
import TrekbankExceptions.NoLoggedInUserException;
import TrekbankExceptions.NoWorkerSelectedException;
import TrekbankExceptions.NoUserSelectedException;
import TrekbankExceptions.NoCustomerSelectedException;
import TrekbankExceptions.NoDataFromPICException;
import TrekbankExceptions.NoDateChosenException;
import TrekbankExceptions.NoObjectSelectedException;
import TrekbankExceptions.NoMeasureProfileDefined;
import TrekbankExceptions.NoProjectSelectedException;
import TrekbankExceptions.NoWorkOrderDefined;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Date;
import trekbank.MeasureSubProfile;
import trekbank.WorkOrder;

/**
 *
 * @author Johnny
 */
public class Dialogs {
    /**
     * Shows a dialog in which the user can search for (multiple) customers
     * @param parent the parent frame
     * @param multipleSelect whether the user is allowed to select mutliple customers or not
     * @return an arraylist of selected customers
     * @throws NoCustomerSelectedException if no customers were selected
     */
    public static ArrayList<Customer> showSearchCustomerDialog(Frame parent, boolean multipleSelect) throws NoCustomerSelectedException {
         return searchCustomerDialog.showDialog(parent, multipleSelect);
    }
    
    /**
     * Shows a dialog in which the user can search for (multiple) projects
     * @param parent the parent frame
     * @param multipleSelect whether the user is allowed to select mutliple projects or not
     * @return an arraylist of selected project
     * @throws NoProjectSelectedException if no projects were selected
     */
    public static ArrayList<Project> showSearchProjectDialog(Frame parent, boolean multipleSelect) throws NoProjectSelectedException {
         return searchProjectDialog.showDialog(parent, multipleSelect);
    }
    
    /**
     * Shows a dialog in which the user can add a new customer
     * @param parent the parent frame
     * @return returns true if there was a new customer added to database, otherwise false
     */
    public static boolean showAddNewCustomerDialog(Frame parent){
        return addNewCustomerDialog.showDialog(parent);
    }
    
    /**
     * Shows a dialog in which the user can add a new project
     * @param parent the parent frame
     * @return returns true if there was a new project added to database, otherwise false
     */
    public static boolean showAddNewProjectDialog(Frame parent){
        return addNewProjectDialog.showDialog(parent);
    }
    
    /**
     * Show a dialog in which the user can log in or create a new user
     * @param parent the parent frame
     * @return return the user that has logged in
     * @throws NoLoggedInUserException if no user has logged in
     */
    public static User showLoginScreen(Frame parent) throws NoLoggedInUserException{
        return loginDialog.showScreen(parent);
    }
    
    /**
     * Shows a dialog in which the user can search for (multiple) users
     * @param parent the parent frame
     * @return an arraylist of selected users
     * @throws NoUserSelectedException if no users were selected
     */
    public static ArrayList<User> showSearchUserDialog(Frame parent) throws NoUserSelectedException{
        return searchUserDialog.showDialog(parent);
    }
    
    public static ArrayList<Worker> showSearchWorkerDialog(Frame parent, boolean multipleSelect) throws NoWorkerSelectedException{
        return searchWorkerDialog.showDialog(parent, multipleSelect);
    }
    
    public static boolean showAddNewWorkerDialog(Frame parent, boolean message){
        return addNewWorkerDialog.showDialog(parent, message);
    }
    
    public static ArrayList<TestedObject> showSearchTestedObjectDialog(Frame parent, boolean multipleSelect) throws NoObjectSelectedException{
        return searchTestedObjectDialog.showDialog(parent, multipleSelect);
    }
    
    public static WorkOrder showNewMeasureProfileDialog(Frame parent) throws NoWorkOrderDefined {
        return newMeasureProfileDialog__NEW.showDialog(parent);
    }
    
    public static Date showDateChooser(Frame parent, Date startDate) throws NoDateChosenException {
        return DateChooser.showDialog(parent, startDate);
    }
}
