/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbankDatabaseObjects;

/**
 *
 * @author Johnny
 */
public class ComError {
 
    private long id;
    private String date;
    private String msg;

    public ComError(long id, String date, String msg) {
        this.id = id;
        this.date = date;
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ComError{" + "id=" + id + ", date=" + date + ", msg=" + msg + '}';
    }
    
     
}
