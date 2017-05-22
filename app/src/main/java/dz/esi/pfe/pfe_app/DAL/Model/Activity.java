package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class Activity implements Serializable {

    String codeActivity;
    String activityLabel;

    public Activity(int code){
        codeActivity=""+code;

        switch (code){
            case 1:
                activityLabel="Debout";
                break;
            case 2:
                activityLabel="Assis";
                break;
            case 3:
                activityLabel="Allongé";
                break;
            case 4:
                activityLabel="Marche";
                break;
            case 5:
                activityLabel="Monte les escaliers";
                break;
            case 6:
                activityLabel="Penché en avant";
                break;
            case 7:
                activityLabel="Main en l'air";
                break;
            case 8:
                activityLabel="Genou plié";
                break;
            case 9:
                activityLabel="A vélo";
                break;
            case 10:
                activityLabel="Footing";
                break;
            case 11:
                activityLabel="Course";
                break;
            case 12:
                activityLabel="Saut avant-arrière";
                break;
            default:
                activityLabel="Unrecognized";
                break;
        }
    }
    public Activity(String codeActivity, String activityLabel) {
        this.codeActivity = codeActivity;
        this.activityLabel = activityLabel;
    }

    public String getCodeActivity() {
        return codeActivity;
    }

    public void setCodeActivity(String codeActivity) {
        this.codeActivity = codeActivity;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }
}
