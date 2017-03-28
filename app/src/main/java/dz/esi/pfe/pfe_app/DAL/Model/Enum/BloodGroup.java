package dz.esi.pfe.pfe_app.DAL.Model.Enum;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public enum BloodGroup {

    B("B"),
    A("A"),
    O("O"),
    AB("AB)");

    String group="";
    String rhesus="";

    BloodGroup(String group)
    {
        this.group=group;
    }

    BloodGroup(String group, String rhesus)
    {
        this.group=group;
        this.rhesus=rhesus;
    }

    public String toString()
    {
        return group+rhesus;
    }
}
