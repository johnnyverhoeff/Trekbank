/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankNetworkConnection;

/**
 *
 * @author Johnny
 */
public class DataMessage {
  
    private long id;
    private long trekKracht;
    private String MB;
    private String spare;
    private String returnMessage;
    
    public DataMessage(String[] msg){
        id = Long.parseLong(msg[1]);
        trekKracht = Long.parseLong(msg[2]);
        MB = msg[3];
        spare = msg[4];
    }
    
    public DataMessage(String msg){
        String[] m = msg.split(",");
        returnMessage = "CtoP300,";
        for (int i = 1; i < m.length; i++){
            returnMessage += m[i] + ",";
        }
        id = Long.parseLong(m[1].trim());
        trekKracht = Long.parseLong(m[2].trim());
        MB = m[3].trim();
        spare = m[4].trim();
    }
    
    public String returnMessage(){
        return "CtoP300," + String.valueOf(id) + "," + String.valueOf(trekKracht) + "," + MB + "," + spare + ",";
        //return returnMessage;
    }

    public long getId() {
        return id;
    }

    public long getTrekKracht() {
        return trekKracht;
    }

    @Override
    public String toString() {
        return "DataMessage{" + "id=" + id + ", trekKracht=" + trekKracht + ", MB=" + MB + ", spare=" + spare + '}';
    }
    
    
}
