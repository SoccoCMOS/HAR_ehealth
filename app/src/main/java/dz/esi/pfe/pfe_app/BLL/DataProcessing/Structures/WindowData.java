package dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;

/**
 * Created by DUALCOMPUTER on 5/16/2017.
 */
public class WindowData implements Serializable {

    int id;
    public static List<Double[]> hardata;
    public static List<Double[]> phydata;
    public static HashMap<Integer,ArrayList<Measure_Data>> filtered_ecg=new HashMap<>();

    public WindowData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
