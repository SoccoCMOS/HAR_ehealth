package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;
import java.sql.Date;


/**
 * Created by DUALCOMPUTER on 6/15/2017.
 */

public class Notification implements Serializable {
    private int id;
    private Date stamp;
    private String description;
    private int criticity;
    private String user;
    private boolean seen=false;

    public Notification(int id, Date stamp, String description, int criticity, String user) {
        this.id = id;
        this.stamp = stamp;
        this.description = description;
        this.criticity = criticity;
        this.user = user;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCriticity() {
        return criticity;
    }

    public void setCriticity(int criticity) {
        this.criticity = criticity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
