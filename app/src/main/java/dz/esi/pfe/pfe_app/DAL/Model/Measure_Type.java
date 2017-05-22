package dz.esi.pfe.pfe_app.DAL.Model;

import java.io.Serializable;

import dz.esi.pfe.pfe_app.DAL.Model.Enum.Position;

/**
 * Created by DUALCOMPUTER on 3/27/2017.
 */
public class Measure_Type implements Serializable {
    String codeSens;
    String label;
    String unit;
    Position position;

    public Measure_Type(String codeSens, String label, String unit, Position position) {
        this.codeSens = codeSens;
        this.label = label;
        this.unit = unit;
        this.position = position;
    }

    public String getCodeSens() {
        return codeSens;
    }

    public void setCodeSens(String codeSens) {
        this.codeSens = codeSens;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}


