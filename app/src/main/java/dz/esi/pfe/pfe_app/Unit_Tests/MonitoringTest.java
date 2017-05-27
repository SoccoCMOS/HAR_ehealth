package dz.esi.pfe.pfe_app.Unit_Tests;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.io.InputStream;
import java.sql.Date;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_Processing;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.CSVFile;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.Utilities;
import dz.esi.pfe.pfe_app.R;

/**
 * Created by DUALCOMPUTER on 5/21/2017.
 */
public class MonitoringTest {
/*
    public static Context context;
    public static int compt=0;

    public static void testMonitoring()
    {
        if(compt==0) {
            try {
                Utilities.initialize(context);
                Utilities.sessionconfig(context);
            } catch (SQLiteConstraintException e) {
                e.printStackTrace();
            }
        }

        // get window
        InputStream inputStream, inputStream1;
        inputStream= context.getResources().openRawResource(R.raw.dataset2);
        inputStream1= context.getResources().openRawResource(R.raw.ecg2);
        CSVFile csvFile = new CSVFile(inputStream);
        CSVFile csvFile1 = new CSVFile(inputStream1);
        csvFile.read();
        csvFile1.read();

        WindowData.hhardata=csvFile.ReadfromTo(0, 3000);
        Long begin=new java.util.Date().getTime();
        Long finish=begin+3000;
        S_Processing.startActionFeHar(context,begin,finish,compt);
        Log.d("beginhar", "" + begin);
        Log.d("finishhar",""+finish);

        WindowData.hphydata=csvFile1.ReadfromTo(0,3000);
        S_Processing.startActionFePhy(context, begin, finish,compt);
        compt++;
    }*/
}
