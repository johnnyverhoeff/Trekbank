/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

import TrekbankExceptions.CorruptedDataException;
import trekbankNetworkConnection.UDPConnection;
import TrekbankExceptions.NoLoggedInUserException;
import TrekbankExceptions.NoMeasureProfileDefined;
import TrekbankExceptions.NoObjectSelectedException;
import TrekbankExceptions.NoUserSelectedException;
import TrekbankExceptions.NoWorkOrderDefined;
import TrekbankExceptions.NoWorkerSelectedException;
import TrekbankExceptions.WrongDataException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.write.WriteException;
import trekbankDatabaseObjects.Database;
import trekbankDatabaseObjects.TestedObject;
import trekbankDatabaseObjects.User;
import trekbankDatabaseObjects.Worker;
import trekbankDialogs.Dialogs;
import trekbankNetworkConnection.DataMessage;

/**
 *
 * @author Johnny
 */
public class MainScreen extends javax.swing.JFrame {

    private User currentUser;
    private Worker currentWorker;
    private Thread readDataThread;
    private Thread helpLabelsThread;
    private int helpLabelState;
    private int addedObjectsCounter = 0;
    private int corruptedDataCounter = 0;
    
    private WorkOrder workOrder;
    
    /**
     * Creates new form MainScreen
     */
    public MainScreen(User currentUser) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(new Point((screenSize.width - 1000)/2, (screenSize.height - 700)/2));
        setMinimumSize(getSize());
        setBackground(new Color(214,217,223));
        this.currentUser = currentUser;
        loggedInUser.setText("Gebruiker: " + currentUser.getName());
        selectedWorker.setText("Werknemer: GEEN WERKNEMER GESELECTEERD");
        addedObjectLabel.setText("Aantal metingen toegevoed aan database: " + addedObjectsCounter);
        
        doMeasure.setEnabled(false);
        stopMeasure.setEnabled(false);
        createNewMeasureProfile.setEnabled(false);        
        extraOptionButtonsEnabled(true);
        corruptedDataLabel.setVisible(false);

        helpLabelState = 0;
        
        startHelpThread();
    }
    
    public MainScreen(User currentUser, Worker assignedWorker) {
        this(currentUser);
        currentWorker = assignedWorker;
        selectedWorker.setText("Werker: " + currentWorker.getName());
    }
    
    private void startHelpThread(){
        helpLabelsThread = new Thread("helpLabelThread") {
            @Override
            public void run(){
                labelMeasureStart.setVisible(false);
                labelWorkerSelect.setVisible(false);
                labelMeasureStop.setVisible(false);
                while(true){
                    if (!helpBox.isSelected()){
                        labelMeasureStart.setVisible(false);
                        labelWorkerSelect.setVisible(false);
                        labelMeasureStop.setVisible(false);
                        labelMeasureProfile.setVisible(false);
                    } else {
                        switch (helpLabelState) {
                            case 0: 
                                labelWorkerSelect.setVisible(true);
                                labelMeasureProfile.setVisible(false);
                                labelMeasureStart.setVisible(false);
                                labelMeasureStop.setVisible(false);
                                break;
                            case 1:
                                labelWorkerSelect.setVisible(false);
                                labelMeasureProfile.setVisible(true);
                                labelMeasureStart.setVisible(false);
                                labelMeasureStop.setVisible(false);
                                break;
                            case 2:
                                labelWorkerSelect.setVisible(false);
                                labelMeasureProfile.setVisible(false);
                                labelMeasureStart.setVisible(true);
                                labelMeasureStop.setVisible(false);
                                break;
                            case 3:
                                labelWorkerSelect.setVisible(false);
                                labelMeasureProfile.setVisible(false);
                                labelMeasureStart.setVisible(false);
                                labelMeasureStop.setVisible(true);
                                break;
                            case 4:
                                labelWorkerSelect.setVisible(true);
                                labelMeasureProfile.setVisible(true);
                                labelMeasureStart.setVisible(false);
                                labelMeasureStop.setVisible(false);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        };
        
        helpLabelsThread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loggedInUser = new javax.swing.JLabel();
        doMeasure = new javax.swing.JButton();
        selectWorker = new javax.swing.JButton();
        selectedWorker = new javax.swing.JLabel();
        stopMeasure = new javax.swing.JButton();
        labelMeasureStart = new javax.swing.JLabel();
        labelWorkerSelect = new javax.swing.JLabel();
        helpBox = new javax.swing.JCheckBox();
        labelMeasureStop = new javax.swing.JLabel();
        logOff = new javax.swing.JButton();
        deleteUsers = new javax.swing.JButton();
        confirmMessages = new javax.swing.JCheckBox();
        deleteWorkers = new javax.swing.JButton();
        addedObjectLabel = new javax.swing.JLabel();
        addNewWorker = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        searchObjects = new javax.swing.JButton();
        createNewMeasureProfile = new javax.swing.JButton();
        labelMeasureProfile = new javax.swing.JLabel();
        corruptedDataLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(214, 217, 223));
        setResizable(false);

        loggedInUser.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        loggedInUser.setText("Ingelogd: ");
        loggedInUser.setToolTipText("Dit is de gebruiker die op dit moment is ingelogd");

        doMeasure.setText("Meting verrichten");
        doMeasure.setToolTipText("Hiermee start u de meting");
        doMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doMeasureActionPerformed(evt);
            }
        });

        selectWorker.setText("Selecteer werknemer");
        selectWorker.setToolTipText("Hiermee kiest u de werknemer die de meting gaat uitvoeren bij de trekbank");
        selectWorker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectWorkerActionPerformed(evt);
            }
        });

        selectedWorker.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        selectedWorker.setText("Werker: ");
        selectedWorker.setToolTipText("Dit is de werker die de meting(en) gaat uitvoeren");

        stopMeasure.setText("Meting stoppen");
        stopMeasure.setToolTipText("Hiermee stopt u de meting");
        stopMeasure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopMeasureActionPerformed(evt);
            }
        });

        labelMeasureStart.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelMeasureStart.setText("< Nu klikt u op deze knop als u de meting wil starten");

        labelWorkerSelect.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelWorkerSelect.setText("<  Eerst selecteerd u een werknemer die de meting uitvoert");

        helpBox.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        helpBox.setSelected(true);
        helpBox.setText("Hulp aan");
        helpBox.setToolTipText("Hiermee selecteerd u of u hulp wilt hebben");

        labelMeasureStop.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelMeasureStop.setText("< U kunt op deze knop klikken wanneer de werknemer klaar is met meten");

        logOff.setText("Uitloggen");
        logOff.setToolTipText("Als u op deze knop klikt logt u uit en keert terug naar het inlogscherm");
        logOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOffActionPerformed(evt);
            }
        });

        deleteUsers.setText("Verwijder gebruiker(s)");
        deleteUsers.setToolTipText("Als u hierop klikt wordt er een menu getoond waar u gebruikers kunt selecteren en verwijderen");
        deleteUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUsersActionPerformed(evt);
            }
        });

        confirmMessages.setSelected(true);
        confirmMessages.setText("Bevestigings vragen");
        confirmMessages.setToolTipText("Hiermee kunt u aangeven of u de bevestigings berichten wil hebben");

        deleteWorkers.setText("Verwijder werknemer(s)");
        deleteWorkers.setToolTipText("Als u hierop klikt wordt er een menu getoond waar u werknemers kunt selecteren en verwijderen");
        deleteWorkers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteWorkersActionPerformed(evt);
            }
        });

        addedObjectLabel.setText("Aantal voorwerpen toegevoegd aan database:");
        addedObjectLabel.setToolTipText("Dit zijn het aantal metingen die tijdens de huidige meting toegevoegd zijn aan de database");

        addNewWorker.setText("Nieuwe werknemer toevoegen");
        addNewWorker.setToolTipText("Als u hierop klikt wordt er een menu getoond waar u een nieuwe werknemer kunt aanmaken");
        addNewWorker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewWorkerActionPerformed(evt);
            }
        });

        searchObjects.setText("Voorwerpen zoeken");
        searchObjects.setToolTipText("Als u hierop klikt wordt er een menu getoond waar u naar geteste voorwerpen kunt zoeken");
        searchObjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchObjectsActionPerformed(evt);
            }
        });

        createNewMeasureProfile.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        createNewMeasureProfile.setText("Meting profiel toevoegen");
        createNewMeasureProfile.setToolTipText("Hiermee maakt een nieuw meting profiel aan");
        createNewMeasureProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createNewMeasureProfileActionPerformed(evt);
            }
        });

        labelMeasureProfile.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelMeasureProfile.setText("< Nu klikt u hier om het meet profiel vast te stellen");

        corruptedDataLabel.setText("Aantal foute data overdrachten: ");
        corruptedDataLabel.setToolTipText("Dit zijn het aantal corrupte data overdrachten");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectWorker, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelWorkerSelect))
                            .addComponent(loggedInUser, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(helpBox)
                            .addComponent(selectedWorker, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(logOff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(deleteWorkers, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(deleteUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(addNewWorker)
                                        .addGap(18, 18, 18)
                                        .addComponent(searchObjects))
                                    .addComponent(confirmMessages)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 911, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(createNewMeasureProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelMeasureProfile))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(stopMeasure, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(doMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelMeasureStop)
                                    .addComponent(labelMeasureStart)))
                            .addComponent(addedObjectLabel)
                            .addComponent(corruptedDataLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(loggedInUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedWorker)
                .addGap(18, 18, 18)
                .addComponent(helpBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectWorker, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelWorkerSelect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createNewMeasureProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMeasureProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(doMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMeasureStart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stopMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMeasureStop))
                .addGap(18, 18, 18)
                .addComponent(addedObjectLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(corruptedDataLabel)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logOff, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmMessages))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteWorkers, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addNewWorker, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchObjects, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void doMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doMeasureActionPerformed
        doMeasure.setEnabled(false);
        createNewMeasureProfile.setEnabled(false);
        stopMeasure.setEnabled(true);
        selectWorker.setEnabled(false);
        extraOptionButtonsEnabled(false);
        
        
        helpLabelState = 3;
        readDataThread = new Thread("Berichten van PIC Lezen") {
            @Override
            public void run(){   
                int j = 1;
                for (int i = 0; i < workOrder.getMeasureObjects().size(); i += j) {  
                    MeasureSubProfile m = workOrder.getMeasureObjects().get(i);
                    j = 1;
                    try {
                        DataMessage dm = UDPConnection.startProtocol(true, 0, 5000);
                        String beschrijving = m.getBeschrijving();
                        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

                        TestedObject to = new TestedObject(dm.getId(), currentTimestamp.toString(), currentUser.getName(), currentWorker.getName(), dm.getTrekKracht(), m.getAantal() + " keer: " + beschrijving);
                        if (Database.insertNewTestedObject(to)){
                            //System.out.println("Added to database: " + dm);
                            addedObjectsCounter++;
                            addedObjectLabel.setText("Aantal metingen toegevoed aan database: " + addedObjectsCounter + " / " + workOrder.getMeasureObjects().size());
                        }
                    } catch (WrongDataException ex) {
                       System.err.println("There was a wrong message received at wrong stage");
                       j = 0;
                    } catch (CorruptedDataException ex) {
                       System.err.println("Data was corrupted");
                       corruptedDataCounter++;
                       corruptedDataLabel.setVisible(true);
                       corruptedDataLabel.setText("Aantal foute data overdachten: " + corruptedDataCounter);
                       j = 0;
                    }   
                }
                stopMeasureActionPerformed(null);
            }
        };  
        readDataThread.start();
        
    }//GEN-LAST:event_doMeasureActionPerformed

    private void selectWorkerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectWorkerActionPerformed
        try {
            currentWorker = Dialogs.showSearchWorkerDialog(null, false).get(0);
            selectedWorker.setText("Werker: " + currentWorker.getName());
            createNewMeasureProfile.setEnabled(true);
            helpLabelState = 1;
        } catch (NoWorkerSelectedException ex) {
            currentWorker = null;
        }    
    }//GEN-LAST:event_selectWorkerActionPerformed

    private void stopMeasureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopMeasureActionPerformed
        // TODO add your handling code here:
        stopMeasure.setEnabled(false);
        doMeasure.setEnabled(false);
        createNewMeasureProfile.setEnabled(true);
        selectWorker.setEnabled(true);
        
        helpLabelState = 4;
        extraOptionButtonsEnabled(true);
        addedObjectsCounter = 0;
        corruptedDataCounter = 0;
        corruptedDataLabel.setVisible(false);
        addedObjectLabel.setText("Aantal voorwerpen toegevoed aan database: " + addedObjectsCounter);
        readDataThread.stop();
    }//GEN-LAST:event_stopMeasureActionPerformed

    private void logOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOffActionPerformed
        if (readDataThread != null && readDataThread.isAlive()) {
            readDataThread.stop();
        }
        
        if (helpLabelsThread != null && helpLabelsThread.isAlive()) helpLabelsThread.stop();
        setVisible(false);
        try {
            new MainScreen(Dialogs.showLoginScreen(null)).setVisible(true);
        } catch (NoLoggedInUserException ex) {
            System.exit(0);
        } finally {
            dispose();
        }

    }//GEN-LAST:event_logOffActionPerformed

    private void deleteUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUsersActionPerformed
        boolean message = false;
        try {
            ArrayList<User> selectedUsersToDelete = Dialogs.showSearchUserDialog(null);
            for (User user : selectedUsersToDelete){
                
                if (!user.equals(currentUser)){
                    Database.deleteUserByName(user.getName(), confirmMessages.isSelected());
                } else if (user.equals(currentUser)) {
                    message = true;
                }
            }
            if (message) JOptionPane.showMessageDialog(null, "U kunt niet de ingelogde gebruiker verwijderen", "Gebruiker verwijderen", JOptionPane.WARNING_MESSAGE);
        } catch (NoUserSelectedException ex) {
            
        } 
            
    }//GEN-LAST:event_deleteUsersActionPerformed

    private void deleteWorkersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteWorkersActionPerformed
        try {
            ArrayList<Worker> selectedWorkersToDelete = Dialogs.showSearchWorkerDialog(null, true);
            for (Worker worker : selectedWorkersToDelete){
                    Database.deleteWorkerByName(worker.getName(), confirmMessages.isSelected());
                    if (worker.equals(currentWorker)){
                        currentWorker = null;
                        selectedWorker.setText("Werknemer: GEEN WERKNEMER GESELECTEERD");
                        helpLabelState = 0;
                        doMeasure.setEnabled(false);
                        stopMeasure.setEnabled(false);
                    }
            }
        } catch (NoWorkerSelectedException ex) {

        } 
    }//GEN-LAST:event_deleteWorkersActionPerformed

    private void addNewWorkerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewWorkerActionPerformed
        Dialogs.showAddNewWorkerDialog(null, confirmMessages.isSelected());
    }//GEN-LAST:event_addNewWorkerActionPerformed

    private void searchObjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchObjectsActionPerformed
        try {
            Dialogs.showSearchTestedObjectDialog(null, true);
        } catch (NoObjectSelectedException ex) {
           
        }
    }//GEN-LAST:event_searchObjectsActionPerformed

    private void createNewMeasureProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createNewMeasureProfileActionPerformed
        try {
            if (workOrder != null) {
                int opt = JOptionPane.showConfirmDialog(null, "Wilt u een nieuw meetprofiel aanmaken ?", "Nieuw meetprofiel", JOptionPane.OK_CANCEL_OPTION);
                if (opt == -1 || opt == 2) {
                    return;
                } else {
                    workOrder = Dialogs.showNewMeasureProfileDialog(null);
                    doMeasure.setEnabled(true);
                    helpLabelState = 2;
                    CreateFile();
                }
            } else {
                workOrder = Dialogs.showNewMeasureProfileDialog(null);
                doMeasure.setEnabled(true);
                helpLabelState = 2;
                CreateFile();
            }
            
        } catch (NoWorkOrderDefined ex) {
            workOrder = null;
            helpLabelState = 1;
            doMeasure.setEnabled(false);
        }
    }//GEN-LAST:event_createNewMeasureProfileActionPerformed

    
    private void extraOptionButtonsEnabled(boolean setEnabled){
        deleteUsers.setEnabled(setEnabled);
        deleteWorkers.setEnabled(setEnabled);
        logOff.setEnabled(setEnabled);
        confirmMessages.setEnabled(setEnabled);
        addNewWorker.setEnabled(setEnabled);
        searchObjects.setEnabled(setEnabled);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewWorker;
    private javax.swing.JLabel addedObjectLabel;
    private javax.swing.JCheckBox confirmMessages;
    private javax.swing.JLabel corruptedDataLabel;
    private javax.swing.JButton createNewMeasureProfile;
    private javax.swing.JButton deleteUsers;
    private javax.swing.JButton deleteWorkers;
    private javax.swing.JButton doMeasure;
    private javax.swing.JCheckBox helpBox;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelMeasureProfile;
    private javax.swing.JLabel labelMeasureStart;
    private javax.swing.JLabel labelMeasureStop;
    private javax.swing.JLabel labelWorkerSelect;
    private javax.swing.JButton logOff;
    private javax.swing.JLabel loggedInUser;
    private javax.swing.JButton searchObjects;
    private javax.swing.JButton selectWorker;
    private javax.swing.JLabel selectedWorker;
    private javax.swing.JButton stopMeasure;
    // End of variables declaration//GEN-END:variables

    private void CreateFile() {
        Timestamp today = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        SimpleDateFormat standardFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Geef de locatie en naam op");
        
        fileChooser.setSelectedFile(new File(standardFormat.format(today) + ".xls"));
        fileChooser.setCurrentDirectory(new File("").getAbsoluteFile());
       
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Excel file", "xls");
        fileChooser.addChoosableFileFilter(excelFilter);
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xls")) fileToSave = new File(fileToSave.getAbsolutePath() + ".xls");

            try {
                Excel.createExcelFile(workOrder, currentUser, currentWorker, fileToSave);
            } catch (IOException | WriteException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
}
