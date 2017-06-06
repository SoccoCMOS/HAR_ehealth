package dz.esi.pfe.pfe_app.UI;

import android.content.Context;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
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
        return 68;
    }

    public double get_mean_ahr()
    {
        //récupérer les id des fenêtres où l'activité reconnue n'est pas repos
        //récupérer les rythmes cardiaques dans ces fenêtres
        //calculer la moyenne
        return 80;
    }

    public int get_nb_anomalies()
    {
        //calculer le nombre de lignes de la table notification avec la date d'aujourd'hui
        return 1;
    }

    public String get_critical_activity()
    {
        //récupérer les codes d'activité des notifications
        //retourner la plus fréquente
        return "Course";
    }

    public Float[] getActivitiesDuration() {
        //récupère les activités de la dernière semaine, les classe en catégorie et somme les durées de chacune=leur nombre
        return new Float[]{Float.valueOf(30), Float.valueOf(20), Float.valueOf(15), Float.valueOf(25),Float.valueOf(10)} ;
    }

    /************          LINECHARTVIEW QUERIES                  ****************/

    public Float[][] getECG(){
        //Récupère l'ECG de la dernière heure
        ArrayList<Measure_Data> ecgmd=new DatabaseHelper(context,"owldb",null,1).getECGMeasureData();
        Float ecg[][]=new Float[ecgmd.size()][2];
        for(int i=0; i<ecgmd.size(); i++) {
            Log.d("timebefore",""+ecgmd.get(i).getTimestamp().getTime());
            Log.d("valuebefore",""+ecgmd.get(i).getValue());
            ecg[i][0]=(float) ecgmd.get(i).getTimestamp().getTime()+i;
            ecg[i][1]=(float) ecgmd.get(i).getValue()+i;
            Log.d("time",""+ecg[i][0]);
            Log.d("value",""+ecg[i][1]);
        }
        return ecg;
    }

    public Float[][] getHeartRate(){
        //Récupère la fc de la dernière journée
        Float hr[][]=new Float[100][2];
        for(int i=0; i<100; i++) {
            hr[i][0]=Float.valueOf(i*10000);
            hr[i][1]=Float.valueOf(i);
        }
        return hr;
    }

    public Float[][] getRR() {
        // Récupère les pics R de la dernière journée
        Float rr[][]=new Float [100][2];
        for(int i=0; i<100; i++) {
            rr[i][0]=Float.valueOf(i*10000);
            rr[i][1]=Float.valueOf(i);
        }
        return rr;
    }

    public Float[][] getWeights() {
        // Récupère les poids de la dernière journée
        Float w[][]=new Float [100][2];
        for(int i=0; i<100; i++) {
            w[i][0]=Float.valueOf(i*10000);
            w[i][1]=Float.valueOf(i*2);
        }
        return w;
    }


    public Float[][] getWaists() {
        // Récupère les tours de taille de la dernière journée
        Float w[][]=new Float [100][2];
        for(int i=0; i<100; i++) {
            w[i][0]=Float.valueOf(i*10000);
            w[i][1]=Float.valueOf(i*3);
        }
        return w;
    }

    public Float[][] getIMCs() {
        // Récupère les imcs de la dernière journée
        Float imc[][]=new Float [100][2];
        for(int i=0; i<100; i++) {
            imc[i][0]=Float.valueOf(i*10000);
            imc[i][1]=Float.valueOf(i*10);
        }
        return imc;
    }

    public Float[][] getWHRs() {
        // Récupère les tours de taille de la dernière journée
        Float whr[][]=new Float [100][2];
        for(int i=0; i<100; i++) {
            whr[i][0]=Float.valueOf(i*10000);
            whr[i][1]=Float.valueOf(i*4);
        }
        return whr;
    }

    /************          PIECHARTVIEW QUERIES                  ****************/
    public Float[] getPercentagesActivity(){
        // Récupère les durées des activités par activité
        Float[] dur=new Float[]{Float.valueOf(10), Float.valueOf(10),Float.valueOf(10),Float.valueOf(10),Float.valueOf(10),Float.valueOf(10)
        ,Float.valueOf(10),Float.valueOf(0),Float.valueOf(10),Float.valueOf(10),Float.valueOf(10),Float.valueOf(0)};
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
        Float[] result=new Float[12];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getDurationsPerCategory() {
        Float[] result=new Float[4];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getFCPerRest() {
        Float[] result=new Float[3];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getFCPerActiv() {
        Float[] result=new Float[9];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getFCPerCategory() {
        Float[] result=new Float[4];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getRRPerRest() {
        Float[] result=new Float[3];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getRRPerActiv() {
        Float[] result=new Float[9];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }

    public Float[] getRRPerCategory() {
        Float[] result=new Float[4];
        for (int i=0; i<result.length; i++){
            result[i]=Float.valueOf(i*5);
        }
        return result;
    }
}
