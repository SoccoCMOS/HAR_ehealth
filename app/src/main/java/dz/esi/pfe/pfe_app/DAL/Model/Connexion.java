package dz.esi.pfe.pfe_app.DAL.Model;

import java.sql.Date;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class Connexion {
    int idConnex;
    Date startConnex, finishConnex;
    int frequency;
    String userID;
    String codeSens;

    public Connexion() {
    }

    public Connexion(Date startConnex, Date finishConnex, int frequency, String userID, String codeSens) {
        this.startConnex = startConnex;
        this.finishConnex = finishConnex;
        this.frequency = frequency;
        this.userID = userID;
        this.codeSens = codeSens;
    }

    public Connexion(int id, Date startConnex, Date finishConnex, int frequency, String userID, String codeSens) {
        this.idConnex = id;
        this.startConnex = startConnex;
        this.finishConnex = finishConnex;
        this.frequency = frequency;
        this.userID = userID;
        this.codeSens = codeSens;
    }

    public int getId() {
        return idConnex;
    }

    public void setId(int id) {
        this.idConnex = id;
    }

    public Date getstartConnex() {
        return startConnex;
    }

    public void setstartConnex(Date startConnex) {
        this.startConnex = startConnex;
    }

    public Date getfinishConnex() {
        return finishConnex;
    }

    public void setfinishConnex(Date finishConnex) {
        this.finishConnex = finishConnex;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCodeSens() {
        return codeSens;
    }

    public void setCodeSens(String codeSens) {
        this.codeSens = codeSens;
    }
}
