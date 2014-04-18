/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDatabaseObjects;

import java.sql.Timestamp;

/**
 *
 * @author Johnny
 */
public class TestedObject {

    @Override
    public String toString() {
        return "TestedObject{" + "id=" + id + ", timeStamp=" + timeStamp + ", userName=" + userName + ", workerName=" + workerName + ", trekKracht=" + trekKracht + ", beschrijving=" + beschrijving + '}';
    }
    
    public String toStringClean(){
        return "";
    }
    
    private long id;
    private Timestamp timeStamp;
    private String userName;
    private String workerName;
    private long trekKracht;
    private String beschrijving;

    public long getId() {
        return id;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public long getTrekKracht() {
        return trekKracht;
    }

    public String getBeschrijving() {
        return beschrijving;
    }
    
    
    
//    private long id;
//    private long projectId;
//    private long meetId;
//    private String beschrijving;
//    private Timestamp timeStamp;
//    private String userName;
//    private long trekKracht;
//
//    public TestedObject(long id, long projectId, long meetId, String beschrijving, String timeStamp, String userName, long trekKracht) {
//        this.id = id;
//        this.projectId = projectId;
//        this.meetId = meetId;
//        this.beschrijving = beschrijving;
//        this.timeStamp = Timestamp.valueOf(timeStamp);
//        this.userName = userName;
//        this.trekKracht = trekKracht;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public long getProjectId() {
//        return projectId;
//    }
//
//    public long getMeetId() {
//        return meetId;
//    }
//
//    public String getBeschrijving() {
//        return beschrijving;
//    }
//
//    public Timestamp getTimeStamp() {
//        return timeStamp;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public long getTrekKracht() {
//        return trekKracht;
//    }
//
//    @Override
//    public String toString() {
//        return "TestedObject{" + "id=" + id + ", projectId=" + projectId + ", meetId=" + meetId + ", beschrijving=" + beschrijving + ", timeStamp=" + timeStamp + ", userName=" + userName + ", trekKracht=" + trekKracht + '}';
//    }
//    

    public TestedObject(long id, String timeStamp, String userName, String workerName, long trekKracht, String beschrijving) {
        this.id = id;
        this.timeStamp = Timestamp.valueOf(timeStamp);
        this.userName = userName;
        this.workerName = workerName;
        this.trekKracht = trekKracht;
        this.beschrijving = beschrijving;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 41 * hash + (this.timeStamp != null ? this.timeStamp.hashCode() : 0);
        hash = 41 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 41 * hash + (this.workerName != null ? this.workerName.hashCode() : 0);
        hash = 41 * hash + (int) (this.trekKracht ^ (this.trekKracht >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestedObject other = (TestedObject) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.timeStamp != other.timeStamp && (this.timeStamp == null || !this.timeStamp.equals(other.timeStamp))) {
            return false;
        }
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        if ((this.workerName == null) ? (other.workerName != null) : !this.workerName.equals(other.workerName)) {
            return false;
        }
        if (this.trekKracht != other.trekKracht) {
            return false;
        }
        return true;
    }

    
    
    
    
}
