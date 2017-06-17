package dz.esi.pfe.pfe_app.BLL.DataProcessing.Controllers;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_FeatureExtraction;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */

public class C_Processing {
    Context context;
    List<Double[]> window = new ArrayList<Double[]>();
    Double[][] fv;
    Long wid;

    public C_Processing(Context context, Long wid) {
        this.context = context;
        this.wid=wid;
    }

    public void setWindow(List<Double[]> w){
        window=w;
    }

    public void processWindow(int nm, int ws,Date begin, Date finish){
        // Récupérer le vecteur de caractéristiques pour la fenêtre et les mesures en question
        Log.d("timebeginfehar",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new java.util.Date()));
        fv=new U_FeatureExtraction(this.context,window,ws,nm).applyFE();
        Log.d("timeendfehar",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new java.util.Date()));
        // Instancier une tâche de reconnaissance d'activité
        ActivityRecognition activityRecognitionTask=new ActivityRecognition(fv,this.context,begin,finish,wid);
        activityRecognitionTask.getActivityByFeatures();
    }
}
