package dz.esi.pfe.pfe_app.BLL.DataProcessing;

import android.app.Service;
import android.content.Context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.CSVFile;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_FeatureExtraction;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.R;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */
public class C_Processing {
    Context context;
    List<double[]> window = new ArrayList<double[]>();
    double[][] fv;
    Activity act;

    public C_Processing(Context context) {
        this.context = context;
    }


    //TODO: @Widad change getWindwow's body according to MQTT
    /// Change the body of the method getWindow for online processing by the acquisition from the Communication Manager
    // Through the MQTT Client Library

    public void getWindow(int deb, int ws){
        InputStream inputStream;
        inputStream= context.getResources().openRawResource(R.raw.dataset);
        CSVFile csvFile = new CSVFile(inputStream);
        csvFile.read();
        window = csvFile.ReadfromTo(deb, ws);
    }

    public void processWindow(int deb, int ws, int nm){
        // Récupérer le vecteur de caractéristiques pour la fenêtre et les mesures en question
        fv=new U_FeatureExtraction(this.context,window,ws,nm).applyFE();
        // Lancer une tâche d'écriture des caractéristiques en base de donnée
        //TODO --> in service
        // Instancier une tâche de reconnaissance d'activité
        ActivityRecognition activityRecognitionTask=new ActivityRecognition(fv,this.context);
        // Récupérer l'activité reconnue
        act=activityRecognitionTask.getActivityByFeatures();
        // Lancer une tâche d'écriture de l'activité en base de donnée
        // TODO --> in service
    }
}
