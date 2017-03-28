package dz.esi.pfe.pfe_app.DAL.Model;

import java.sql.Date;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class Measure_Data {
    String codeSens;
    long id;
    double value;
    Date timestamp;
    String userID;

    public Measure_Data() {
    }

    public Measure_Data(String userID) {
        this.userID = userID;
    }

    public Measure_Data(String codeSens, long id, double value, Date timestamp) {
        this.codeSens = codeSens;
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Measure_Data(long id, String codeSens, double value, Date timestamp, String userID) {
        this.id = id;
        this.codeSens = codeSens;
        this.value = value;
        this.timestamp = timestamp;
        this.userID = userID;
    }

    public String getCodeSens() {
        return codeSens;
    }

    public void setCodeSens(String codeSens) {
        this.codeSens = codeSens;
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
