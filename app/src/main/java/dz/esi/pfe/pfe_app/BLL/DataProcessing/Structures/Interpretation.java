package dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures;

/**
 * Created by DUALCOMPUTER on 5/20/2017.
 */
public class Interpretation {
    private Boolean normal=true;
    private String details="RAS";

    public Interpretation() {
    }

    public Interpretation(Boolean normal, String details) {
        this.normal = normal;
        this.details = details;
    }

    public Boolean getNormal() {
        return normal;
    }

    public void setNormal(Boolean normal) {
        this.normal = normal;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
