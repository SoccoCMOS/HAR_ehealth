package dz.esi.pfe.pfe_app.DAL.Model.Enum;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public enum Gender {

    Female("Femme"),
    Male("Homme");

    String name="";
    Gender(String name)
    {
        this.name=name;
    }

    public String toString()
    {
        return name;
    }
}
