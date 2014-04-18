/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankNetworkConnection;

import TrekbankExceptions.WrongDataException;
import TrekbankExceptions.CorruptedDataException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Calendar;
import trekbankDatabaseObjects.ComError;
import trekbankDatabaseObjects.Database;
/**
 *
 * @author Johnny
 */
public class UDPConnection {
 
    private InetAddress address;
    private int sendPort;
    private int recievePort;
    
//    private static DatagramSocket sendSocket;
//    private static DatagramSocket receiveSocket;
    
    private static final String iphoneAddress = "192.168.1.14";
    
    public static final String picAddress = "192.168.1.60";
    private static final int picReceivePort = 10001;
    private static final int pcReveivePort = 10001;
    
    private static final String isPCReady = "PtoC100";
    private static final String pcIsReady = "CtoP100";
    
    private static final String dataFromPic = "PtoC200";
    private static final String dataWrong = "PtoC300";
    private static final String dataOk = "PtoC400";
    /**
     * Creates a new udp connection to ip address host, with a receive and send port
     * @param host the ip address for the connection
     * @param sendPort the port to which it sends
     * @param receivePort the port from which it receives
     * @throws UnknownHostException if the host doesn't exist
     */
    public UDPConnection(String host, int sendPort, int receivePort) throws UnknownHostException, SocketException{
        address = InetAddress.getByName(host);
        this.recievePort = receivePort;
        this.sendPort = sendPort;
        
//        sendSocket = new DatagramSocket();
//        receiveSocket = new DatagramSocket(receivePort);
    }
    
//    private static void closeSockets(){
//        sendSocket.close();
//        receiveSocket.close();
//    }
    
    /**
     * You can call this message to send a message
     * @param msg the message to send
     * @throws SocketException if the socket couldn't be created
     * @throws IOException if there was an io error
     */
    public void sendMessage(String msg) throws SocketException, IOException{
        sendMessage(msg, address, sendPort);
    }
    
    /**
     * You can call this message to send a message to a specific host with a port
     * @param msg the message to sen
     * @param address to which the message is addressed
     * @param port the port of the host
     * @throws SocketException if the socket couldn't be created
     * @throws IOException if there was an io error
     */
    public static void sendMessage(String msg, InetAddress address, int port) throws SocketException, IOException{
        byte[] message = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
        try (DatagramSocket dsocket = new DatagramSocket()) {
            dsocket.send(packet);
        }
        //sendSocket.send(packet);
        //System.out.println(" verstuur bericht:: " + msg);
    }
    
    /**
     * Waits timeout milliseconds for a message and returns it
     * @param timeOut the timOut time
     * @return returns the received message
     * @throws SocketException if the socket couldn't be created
     * @throws SocketTimeoutException if a timeout occured
     * @throws IOException if there was an io error
     */
    public String receiveMessage(int timeOut) throws SocketException, SocketTimeoutException, IOException{
        String msg;
        try (DatagramSocket dsocket = new DatagramSocket(recievePort)) {
            if (timeOut != 0) {
                dsocket.setSoTimeout(timeOut);
            }
            //receiveSocket.setSoTimeout(timeOut);
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            msg = "";
            while (msg.isEmpty()){
                dsocket.receive(packet);
                dsocket.close();
                //receiveSocket.receive(packet);
                msg = new String(buffer, 0, packet.getLength());
                packet.setLength(buffer.length);
            }   
        } catch (SocketException ex) {
            System.err.println("SocketException in receiveMessage.. trying again after 1sec");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex1) {
                System.err.println("Exception trying to sleep 1 sec for receive message");
            }
            return receiveMessage(timeOut);
        }
        //System.out.println(" Ontvangen bericht:: " + msg);
        return msg; 
    }
    
    /**
     * Waits for a message and returns it
     * @return returns the received message
     * @throws SocketException if the socket couldn't be created
     * @throws IOException if there was an io error
     */
    public String receiveMessage() throws SocketException, IOException{
        return receiveMessage(0);
    }
    
    public static DataMessage startProtocol(boolean picOriPhone, int initTimeOut, int restTimeOut) throws WrongDataException, CorruptedDataException{
        UDPConnection conn = null;
        try {
            if (picOriPhone){
                conn = new UDPConnection(picAddress, picReceivePort, pcReveivePort);
            } else {
                conn = new UDPConnection(iphoneAddress, pcReveivePort, picReceivePort);
            }
        } catch (UnknownHostException e){
            System.err.println("The host was not found");
        } catch (SocketException ex) {
            System.err.println("SocketException occured in startProtocol..starting again");
            return startProtocol(picOriPhone, initTimeOut, restTimeOut);
        }    
        DataMessage dm = initProtocol(conn, initTimeOut, restTimeOut);
        //closeSockets();
        return dm;
    }
    
    private static DataMessage initProtocol(UDPConnection conn, int initTimeOut, int restTimeOut) throws WrongDataException, CorruptedDataException{
        try {
            String msg = conn.receiveMessage(initTimeOut);
            //String[] m = splitMessage(msg);
            if (msg.equals(isPCReady)){
                conn.sendMessage(pcIsReady);
                return secondProtocol(conn, initTimeOut, restTimeOut);
            } else {
                // other possiblities;
                System.err.println("in initProtocol...wrong type of msg recieved...starting again  msg = " + msg);
                Database.insertComError(new ComError(Long.MIN_VALUE, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString(), msg + "----WrongData"));
                //return initProtocol(conn, timeOut);
                throw new WrongDataException();
            }
            
        } catch (SocketTimeoutException ex) {
            System.err.println("TimeOut occured in initProtocol... starting again");
            return initProtocol(conn, initTimeOut, restTimeOut);
        } catch (SocketException ex) {
            System.err.println("SocketException occured in initProtocol...starting again");
            return initProtocol(conn, initTimeOut, restTimeOut);
        } catch (IOException ex) {
            System.err.println("IOException occured in initProtocol...starting again");
            return initProtocol(conn, initTimeOut, restTimeOut);
        }
    }
    
    private static DataMessage secondProtocol(UDPConnection conn, int initTimeOut, int restTimeOut) throws WrongDataException, CorruptedDataException{
        try {
            String msg = conn.receiveMessage(restTimeOut);
            String[] m = splitMessage(msg);
            
            if (m[0].equals(dataFromPic)) {
                DataMessage dataMessage = new DataMessage(msg);
                conn.sendMessage(dataMessage.returnMessage());
                return thirdProtocol(conn, initTimeOut, restTimeOut, dataMessage);
            } else {
                // other possiblities;
                System.err.println("in secondProtocol...wrong type of msg recieved...starting again  msg = " + msg);
                //return initProtocol(conn, timeOut);
                Database.insertComError(new ComError(Long.MIN_VALUE, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString(), msg + "----WrongData"));
                throw new WrongDataException();
            }
        } catch (SocketTimeoutException ex) {
            System.err.println("TimeOut occured in secondProtocol (200)... starting again with initProtocol");
            return initProtocol(conn, initTimeOut, restTimeOut);
        } catch (SocketException ex) {
            System.err.println("SocketException occured in secondProtocol");
            return initProtocol(conn, initTimeOut, restTimeOut);
        } catch (IOException ex) {
            System.err.println("IOException occured in secondProtocol");
            return initProtocol(conn, initTimeOut, restTimeOut);
        }
    }
    
    private static DataMessage thirdProtocol(UDPConnection conn, int initTimeOut, int restTimeOut, DataMessage dataMessage) throws CorruptedDataException, WrongDataException {
        try {
            String msg = conn.receiveMessage(restTimeOut);
            String[] m = splitMessage(msg);
            
            switch (m[0]) {
                case dataOk:
                    return dataMessage;
                case dataWrong:
                    System.err.println("in thirdProtocol...corrupted data received...starting again");
                    //return initProtocol(conn, timeOut);
                    Database.insertComError(new ComError(Long.MIN_VALUE, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString(), msg + "----CorruptedData"));
                    throw new CorruptedDataException();
                default:
                    // other possiblities;
                    System.err.println("in thirdProtocol...wrong type of msg recieved...starting again  msg = " + msg);
                    Database.insertComError(new ComError(Long.MIN_VALUE, new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).toString(), msg + "----WrongData"));
                    //return initProtocol(conn, timeOut);
                    throw new WrongDataException();
            }
        } catch (SocketTimeoutException ex) {
            System.err.println("TimeOut occured in thirdProtocol ... starting again with initProtocol");
            return initProtocol(conn, initTimeOut, restTimeOut);
        } catch (SocketException ex) {
            System.err.println("SocketException occured in thirdProtocol");
            return initProtocol(conn, initTimeOut, restTimeOut);
        } catch (IOException ex) {
            System.err.println("IOException occured in thirdProtocol");
            return initProtocol(conn, initTimeOut, restTimeOut);
        }
    }   
    
    private static String[] splitMessage(String msg){
        String[] m = msg.split(",");
        for (int i = 0; i < m.length; i++){
            m[i] = m[i].trim();
        }
        return m;
    }
    
    
    //    //<editor-fold defaultstate="collapsed" desc="Old Protocol code, works but bugged">
    /**
     * //     * This function commences the protocol between pc and PIC
     * //     * @param picOriPhone set wheter the ip adres from PIC or iphone to use
     * //     * @param timeOut the timeout in ms
     * //     * @return returns an arrayList of datamessages
     * //     * @throws NoDataFromPICException if no (use-able) data was received
     * //     */
    //    public static ArrayList<DataMessage> startProtocol(boolean picOriPhone, int timeOut) throws NoDataFromPICException, CorruptedDataException {
    //        msgs = new ArrayList<DataMessage>();
    //        UDPConnection conn = null;
    //        try {
    //            if (picOriPhone){
    //                conn = new UDPConnection(picAddress, picReceivePort, pcReveivePort);
    //            } else {
    //                conn = new UDPConnection(iphoneAddress, pcReveivePort, picReceivePort);
    //            }
    //        } catch (UnknownHostException e){
    //            System.err.println("The host was not found");
    //        }
    //
    //        try {
    //            return initProtocol(conn, timeOut, false);
    //
    //        } catch (SocketTimeoutException ex) {
    //            System.err.println("There was a timeOut receiving from host");
    //        } catch (SocketException ex) {
    //            System.err.println("Socket couldn't be created");
    //        } catch (IOException ex) {
    //            System.err.println("There was an IO Exception");
    //        }
    //        System.err.println("hier 1");
    //        throw new NoDataFromPICException();
    //
    //    }
    //
    //    private static ArrayList<DataMessage> initProtocol(UDPConnection conn, int timeOut, boolean reTry) throws IOException, SocketTimeoutException, NoDataFromPICException, CorruptedDataException {
    //        try {
    //            if (reTry || conn.receiveMessage(timeOut).equals(isPCReady)){
    //                /*if (send) {*/conn.sendMessage(pcIsReady);
    //                //send = true;
    //            }
    //
    //            return checkMessage(conn, timeOut);
    //        } catch (SocketTimeoutException ex) {
    //            System.err.println("There was no (use-able) data received from PIC due to time-out, try again...");
    //            return initProtocol(conn, timeOut, true);
    //        }
    //    }
    //    private static ArrayList<DataMessage> checkMessage(UDPConnection conn, int timeOut) throws SocketTimeoutException, NoDataFromPICException, IOException, CorruptedDataException{
    //        String msg = conn.receiveMessage(timeOut);
    //        String[] m = msg.split(",");
    //        for (int i = 0; i < m.length; i++){
    //            m[i] = m[i].trim();
    //        }
    //
    //        if (msg.equals(dataWrong)) {System.err.println("hier 2");throw new CorruptedDataException();}
    //        if (msg.equals(isPCReady)) initProtocol(conn, timeOut, true);
    //
    //        if (m[0].equals(dataFromPic)){
    //            DataMessage dataMsg = new DataMessage(msg);
    //            conn.sendMessage(dataMsg.returnMessage());
    //            msgs.add(dataMsg);
    //            return checkMessage(conn, timeOut);
    //        } else {
    //            if (msg.equals(dataOk)){
    //                return msgs;
    //            } else {
    //                System.err.println("hier 3");
    //                throw new NoDataFromPICException();
    //            }
    //        }
    //    }
    //</editor-fold>
}
