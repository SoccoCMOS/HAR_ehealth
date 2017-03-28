package dz.esi.pfe.pfe_app.DAL.Model;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class Activity {

    String codeActivity;
    String activityLabel;

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
