/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import trekbankDatabaseObjects.Database;
import trekbankDatabaseObjects.License;
import trekbankDialogs.Dialogs;
import trekbankNetworkConnection.UDPConnection;

/**
 *
 * @author Johnny
 */
public class TrekBank{

    public static void main(String[] args) throws IOException /*throws NoLoggedInUserException*/ {
        //<editor-fold defaultstate="collapsed" desc="Connect to database">
        Database.connect("test", "Johnny", "870393");
        //</editor-fold>
        try {
            UDPConnection.sendMessage("ABC", InetAddress.getByName(UDPConnection.picAddress), 10001);
            MainScreen ms = new MainScreen(Dialogs.showLoginScreen(null)/*, MeasureSubProfile.testProfile2()*/);
            ms.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void CheckLicenses() {
        Timestamp today = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        
        ArrayList<License> inactiveLicenses = new ArrayList<License>();
        for (License license : Database.getAllLicenses()) {
            if (!license.isActive()) {
                inactiveLicenses.add(license);
            }
        }

        for (License l : inactiveLicenses) {
            Timestamp dueDate = Timestamp.valueOf(l.getDueDate() + " 00:00:00");
            if (today.after(dueDate)) {
                String ans = JOptionPane.showInputDialog(null, "Licentie van " + l.getDueDate() + " verlopen\nVoor de licensekey in:", "License verlopen", JOptionPane.WARNING_MESSAGE);
                if (ans == null) {
                    JOptionPane.showMessageDialog(null, "U heeft geen license key ingevoerd, het programa sluit nu af" , "Geen license key", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                } else if (ans.equals(l.getKey())) {
                        JOptionPane.showMessageDialog(null, "U heeft een goede license key ingevoerd" , "Een license key", JOptionPane.INFORMATION_MESSAGE);
                        Database.updateLicenseWithId(l.getId(), 1);
                } else {
                        JOptionPane.showMessageDialog(null, "U heeft foute license key ingevoerd" , "Foute license key", JOptionPane.WARNING_MESSAGE);
                        CheckLicenses();
                }
            
            }
        }
        
    }
//    
}
