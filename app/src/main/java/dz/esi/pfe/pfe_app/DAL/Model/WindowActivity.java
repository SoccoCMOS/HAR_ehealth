package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class WindowActivity implements Serializable {
    long idWindow;
    Date start, finish;
    String userID;
    String codeActivity;

    public WindowActivity(long idWindow, Date start, Date finish, String codeActivity, String userID) {
        this.idWindow = idWindow;
        this.start = start;
        this.finish = finish;
        this.codeActivity = codeActivity;
        this.userID = userID;
    }

    public long getIdWindow() {
        return idWindow;
    }

    public void setIdWindow(long idWindow) {
        this.idWindow = idWindow;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCodeActivity() {
        return codeActivity;
    }

    public void setCodeActivity(String codeActivity) {
        this.codeActivity = codeActivity;
    }
}
