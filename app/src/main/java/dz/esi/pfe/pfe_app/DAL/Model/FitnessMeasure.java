package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class FitnessMeasure implements Serializable {

    long idFM;
    Double weight, height,waist;
    Date dateUpdate;
    String userID;

    public FitnessMeasure(long id, Double weight, Double height, Double waist, Date dateUpdate, String userID) {
        this.idFM = id;
        this.weight = weight;
        this.height = height;
        this.waist = waist;
        this.dateUpdate = dateUpdate;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getIdFM() {
        return idFM;
    }

    public void setIdFM(long idFM) {
        this.idFM = idFM;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
