package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by DUALCOMPUTER on 5/20/2017.
 */
public class HeartRate implements Serializable {

    long id;
    double value;
    Date timestamp;
    String userID;

    public HeartRate(long id, double value, Date timestamp, String userID) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
