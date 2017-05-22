package dz.esi.pfe.pfe_app.BLL.DataProcessing.Controllers;

import android.content.Context;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_FeatureExtraction;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */
public class C_Processing {
    Context context;
    List<Double[]> window = new ArrayList<Double[]>();
    Double[][] fv;

    public C_Processing(Context context) {
        this.context = context;
    }

    public void setWindow(List<Double[]> w){
        window=w;
    }

    public void processWindow(int ws, int nm, Date begin, Date finish){
        // Récupérer le vecteur de caractéristiques pour la fenêtre et les mesures en question
        fv=new U_FeatureExtraction(this.context,window,ws,nm).applyFE();

        // Instancier une tâche de reconnaissance d'activité
        ActivityRecognition activityRecognitionTask=new ActivityRecognition(fv,this.context,begin,finish);
        activityRecognitionTask.getActivityByFeatures();
    }
}
