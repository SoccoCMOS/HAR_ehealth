package dz.esi.pfe.pfe_app.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_DecisionSupport;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.WindowActivity;
import dz.esi.pfe.pfe_app.DAL.S_DataAccess;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */
public class C_Affichage {
    Context context;

    public C_Affichage(Context context) {
        this.context = context;
    }

    /************          DAHSBOARD QUERIES                  ****************/

    public double get_mean_rhr()
    {
        //récupérer les id des fenêtres où l'activité reconnue est repos soit L1,L2 ou L3
        //récupérer les rythmes cardiaques dans ces fenêtres
        //calculer la moyenne
        return WindowData.lasthr;
    }

    public double get_mean_ahr()
    {
        //récupérer les id des fenêtres où l'activité reconnue n'est pas repos
        //récupérer les rythmes cardiaques dans ces fenêtres
        //calculer la moyenne
        return WindowData.lastahr;
    }

    public int get_nb_anomalies()
    {
        //calculer le nombre de lignes de la table notification avec la date d'aujourd'hui
        return WindowData.nbanomalies;
    }

    public String get_critical_activity()
    {
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int last_activity = preferences.getInt("lastactiv",0);
        return new Activity(last_activity).getActivityLabel();*/
        return WindowData.lastact;
    }

    public Float[] getActivitiesDuration() {
        //récupère les activités de la dernière semaine, les classe en catégorie et somme les durées de chacune=leur nombre
        return new Float[]{Float.valueOf(60), Float.valueOf(20), Float.valueOf(10), Float.valueOf(10),Float.valueOf(0)} ;
    }

    /************          LINECHARTVIEW QUERIES                  ****************/

    public Float[][] getECG(){
        //Récupère l'ECG de la dernière heure
        ArrayList<Measure_Data> ecgmd=new DatabaseHelper(context,"owldb",null,1).getECGMeasureData();
        Float ecg[][]=new Float[ecgmd.size()][2];
        for(int i=0; i<ecgmd.size(); i++) {
            //Log.d("timebefore",""+ecgmd.get(i).getTimestamp().getTime());
            //Log.d("valuebefore",""+ecgmd.get(i).getValue());
            ecg[i][0]=Float.parseFloat(new Timestamp(ecgmd.get(i).getTimestamp().getTime()).getTime()+"");
            ecg[i][1]=(float) ecgmd.get(i).getValue();
            Log.d("time",""+ecg[i][0]);
            Log.d("value",""+ecg[i][1]);
        }
        return ecg;
    }

    public Float[][] getHeartRate(){
        ArrayList<HeartRate> hr = null;
        hr=new DatabaseHelper(context,"owldb",null,1).getAllHeartRates();
        System.out.println("size of hr " + hr.size());
        Float[][] activ=new Float[hr.size()][2];
        for(int i=0; i<hr.size(); i++){
            activ[i][0]=(float)hr.get(i).getTimestamp().getTime();
            activ[i][1]=(float)hr.get(i).getValue();
        }
        return activ;
    }

    public Float[][] getRR() {
        // Récupère les pics R de la dernière journée
        Float rr[][]=new Float [100][2];
        for(int i=0; i<100; i++) {
            rr[i][0]=Float.valueOf(i*10000);
            rr[i][1]=(float)(0.1+Math.random()*1.4);
        }
        return rr;
    }

    public Float[][] getWeights() {
        // Récupère les poids de la dernière semaine
        Float w[][]=new Float [30][2];
        for(int i=0; i<30; i++) {
            w[i][0]=Float.valueOf(new Date(i).getTime());
            w[i][1]=(float)(65+i*Math.random());
        }
        return w;
    }


    public Float[][] getWaists() {
        // Récupère les tours de taille de la dernière journée
        Float w[][]=new Float [30][2];
        for(int i=0; i<30; i++) {
            w[i][0]=Float.valueOf(new Date(i).getTime());
            w[i][1]=(float)(39+i*Math.random());
        }
        return w;
    }

    public Float[][] getIMCs() {
        // Récupère les imcs de la dernière semaine
        Float imc[][]=new Float [30][2];
        for(int i=0; i<30; i++) {
            imc[i][0]=Float.valueOf(new Date(i).getTime());
            imc[i][1]=(float)(25+i*Math.random());
        }
        return imc;
    }

    public Float[][] getWHRs() {
        // Récupère les tours de taille de la dernière journée
        Float whr[][]=new Float [30][2];
        for(int i=0; i<30; i++) {
            whr[i][0]=Float.valueOf(new Date(i).getTime());
            whr[i][1]=(float)(0.5+i*Math.random());
        }
        return whr;
    }

    /************          PIECHARTVIEW QUERIES                  ****************/
    public Float[] getPercentagesActivity(){
        // Récupère les durées des activités par activité
        Float[] dur=new Float[]{Float.valueOf(10), Float.valueOf(20),Float.valueOf(30),Float.valueOf(15),Float.valueOf(0),Float.valueOf(5)
        ,Float.valueOf(10),Float.valueOf(0),Float.valueOf(0),Float.valueOf(0),Float.valueOf(10),Float.valueOf(0)};
        // calculer pourcentage satisfait par activité
        return dur;
    }

    public Float[] getPercentagesArythmia(){
        //Récupère le nombre d'arythmies de chaque type
        int nbarythmia[]=new int[]{0,0,1,0,49};
        Float[] results=new Float[]{Float.valueOf(0), Float.valueOf(0), Float.valueOf(2), Float.valueOf(0), Float.valueOf(98)};
        //Récupère le nombre de fenêtres
        int nbf=50;
        // Retourne les pourcentages
        return results;
    }

    public Float[] getDurationsPerActivity() {
        Float[] result=new Float[]{Float.valueOf(144),Float.valueOf(288),Float.valueOf(432),Float.valueOf(216),Float.valueOf(0),
                Float.valueOf(72),Float.valueOf(144),Float.valueOf(0),Float.valueOf(0),Float.valueOf(0),Float.valueOf(144)
        ,Float.valueOf(0)};
        return result;
    }

    public Float[] getDurationsPerCategory() {
        Float[] result=new Float[]{Float.valueOf(864),Float.valueOf(288),Float.valueOf(144),Float.valueOf(144)};
        return result;
    }

    public Float[] getFCPerRest() {
        Float[] result=new Float[3];
        for (int i=0; i<result.length; i++){
            result[i]=(float)(30+Math.random()*30);
        }
        return result;
    }

    public Float[] getFCPerActiv() {
        Float[] result=new Float[9];
        for (int i=0; i<result.length; i++){
            result[i]=(float)(40+Math.random()*60);
        }
        return result;
    }

    public Float[] getFCPerCategory() {
        Float[] result=new Float[4];
        for (int i=0; i<result.length; i++){
            result[i]=(float)(30+Math.random()*60);
        }
        return result;
    }

    public Float[] getRRPerRest() {
        Float[] result=new Float[3];
        for (int i=0; i<result.length; i++){
            result[i]=(float)(0.42+Math.random()*0.78);
        }
        return result;
    }

    public Float[] getRRPerActiv() {
        Float[] result=new Float[9];
        for (int i=0; i<result.length; i++){
            result[i]=(float)(0.42+Math.random()*0.78);
        }
        return result;
    }

    public Float[] getRRPerCategory() {
        Float[] result=new Float[4];
        for (int i=0; i<result.length; i++){
            result[i]=(float)(0.42+Math.random()*0.78);
        }
        return result;
    }

    public Float[][] getActivities() {
        ArrayList<WindowActivity> windowActivities = null;
        try {
            windowActivities=new DatabaseHelper(context,"owldb",null,1).getAllWindowActivities();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Float[][] activ=new Float[windowActivities.size()][2];
        for(int i=0; i<windowActivities.size(); i++){
            activ[i][0]=(float)i;
            activ[i][1]=Float.valueOf(windowActivities.get(i).getCodeActivity());
        }
        return activ;
    }
}
