package dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.Communication.C_Communication;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;

/**
 * Created by DUALCOMPUTER on 5/16/2017.
 */
public class WindowData implements Serializable {

    int id;
    public static String lastact="Debout";
    public static double lasthr=70;
    public static double lastahr=80;
    public static int nbanomalies=0;

    //public static List<Double[]> hardata;
    public static HashMap<Long,List<Double[]>> hhardata;
    //public static List<Double[]> phydata;
    public static HashMap<Long,List<Double[]>> hphydata;
    public static List<List<Double>> hardataL;
    public static List<List<Double>> phydataL;
    public static ArrayList<Measure_Data> filtered_ecg;
    public static Context context;

    public static void intialize (){
        //hardata = new ArrayList<Double[]>();
        hardataL = new ArrayList<List<Double>>();
        phydataL = new ArrayList<List<Double>>();
        //phydata= new ArrayList<Double[]>();
        hhardata= new HashMap<Long,List<Double[]>>() ;
        hphydata= new HashMap<Long,List<Double[]>>() ;
    }

    public static int getSize() {
        return size;
    }
    private static int size;
    private static Long wid=new Long(0);

    public  void setSize(final int size){

        WindowData.size=size;

        Thread t = new Thread() {
            public void run() {
                if(WindowData.size== 23) {
                    List<Double[]> buff= new  ArrayList<Double[]>();
                    for(int i=0; i<21;i++) {
                        buff.add(i,ListToArrayDouble(hardataL.get(i)));
                    }
                    hhardata.put(wid,buff);
                    hphydata.put(wid, new ArrayList<Double[]>());
                    //phydata.add(0,ListToArrayDouble(phydataL.get(0)));
                    //phydata.add(1,ListToArrayDouble(phydataL.get(1)));
                    hphydata.get(wid).add(0,ListToArrayDouble(phydataL.get(0)));
                    hphydata.get(wid).add(1,ListToArrayDouble(phydataL.get(1)));
                    hardataL.clear();
                    phydataL.clear();

                    Log.d("WindowData", hhardata.size() + "  wid " + hhardata.get(wid).size());
                    Log.d("WindowData",hphydata.size() + "  wid " + hphydata.get(wid).size() + " " + hphydata.get(wid).get(0).length);
                    C_Communication.startActionComWindow(context,wid);
                    WindowData.size=0;
                    wid++;
                }
            }
        };
        t.start();
    }

    public Double[] ListToArrayDouble(List<Double> list){

        Double[] array =  list.toArray(new Double[list.size()]);

        return array;

    }

    public WindowData() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


