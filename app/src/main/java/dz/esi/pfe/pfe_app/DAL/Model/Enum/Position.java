package dz.esi.pfe.pfe_app.DAL.Model.Enum;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public enum Position {
    ARM("Arm"),
    LEG("Leg"),
    WAIST("Waist");

    String name="";
    Position(String name)
    {
        this.name=name;
    }

    public String toString()
    {
        return name;
    }
}
