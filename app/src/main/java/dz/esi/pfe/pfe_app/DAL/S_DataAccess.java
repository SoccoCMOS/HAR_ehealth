package dz.esi.pfe.pfe_app.DAL;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_DecisionSupport;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.Model.Account;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.DAL.Model.Connexion;
import dz.esi.pfe.pfe_app.DAL.Model.FitnessMeasure;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Type;
import dz.esi.pfe.pfe_app.DAL.Model.RPeaks;
import dz.esi.pfe.pfe_app.DAL.Model.User;
import dz.esi.pfe.pfe_app.DAL.Model.WindowActivity;


public class S_DataAccess extends IntentService {

    private static final String ACTION_CREATE = "create" ;
    private static final String ACTION_INSERT = "insert";
    private static final String ACTION_RETRIEVE= "retrieve";
    private static final String ACTION_ACTIVITY="activity";

    public S_DataAccess() {
        super("S_DataAccess");
    }

    public static void startActionCreate(Context context) {
        Intent intent = new Intent(context, S_DataAccess.class);
        intent.setAction(ACTION_CREATE);
        Log.d("onstartcreate", "called start action create");
        // pass parameters here
        context.startService(intent);
    }

    public static void startActionInsert(Context context, String table, Object data) {
        Intent intent = new Intent(context, S_DataAccess.class);
        intent.setAction(ACTION_INSERT);
        // pass parameters here
        intent.putExtra("table", table);
        if(!table.equals("measuredatas")) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) data);
            intent.putExtra("bundle", bundle);
        }
        context.startService(intent);
        //Log.d("inserttion", String.valueOf(data));
    }

    public static void startActionRetrieve(Context context, String table) {
        Intent intent = new Intent(context, S_DataAccess.class);
        intent.setAction(ACTION_RETRIEVE);
        // pass parameters here
        intent.putExtra("table", table);
        context.startService(intent);
    }

    public static void startActionActivity(Context context, HeartRate hr) {
        Intent intent = new Intent(context, S_DataAccess.class);
        intent.setAction(ACTION_ACTIVITY);
        // pass parameters here
        Bundle bundle=new Bundle();
        bundle.putSerializable("hr",hr);
        intent.putExtra("hr", bundle);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            String table=intent.getStringExtra("table");
            Object d=null;
            if (ACTION_INSERT.equals(action)) {
                // Récupérer les paramètres depuis l'intent
                if(!table.equals("measuredatas")) {
                    Bundle bundle = intent.getExtras().getBundle("bundle");
                    d = bundle.getSerializable("data");
                }
                // call function that handles it
                handleActionInsert(table,d);
            }
            else if (ACTION_CREATE.equals(action)){
                // call function that handles it
                handleActionCreate();
                Log.d("onhandle", "called handle");
            }
            else if (ACTION_ACTIVITY.equals(action)){
                HeartRate hr=(HeartRate)intent.getExtras().getBundle("hr").getSerializable("hr");
                //handleActionActivity(hr);
            }
            else {
                // call function that handles it
                handleActionRetrieve(table);
            }
        }
    }

//    private void handleActionActivity(HeartRate hr) {
//        DatabaseHelper db=new DatabaseHelper(this,"owldb",null,1);
//        Activity a=db.getActivityByInstant(hr.getTimestamp());
//        S_DecisionSupport.startActionCallback(this,hr,Integer.valueOf(a.getCodeActivity()));
//        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
//        Log.d("activity","activity of instant"+sdfDate.format(hr.getTimestamp())+" = "+a.getActivityLabel());
//    }

    private void handleActionCreate() {
        DatabaseHelper db=new DatabaseHelper(this,"owldb",null,1);
        Log.d("onhandleaction","created");
    }

    private void handleActionInsert(String table, Object data) {
        DatabaseHelper db=new DatabaseHelper(this,"owldb",null,1);
        if(table.equals("measuretype"))
            db.insertMeasureType((Measure_Type)data);
        else if(table.equals("user"))
            db.insertUser((User) data);
        else if(table.equals("account"))
            db.insertAccount((Account) data);
        else if(table.equals("activity"))
            db.insertActivity((Activity) data);
        else if(table.equals("connexion"))
            db.insertConnexion((Connexion) data);
        else if(table.equals("fitnessmeasure"))
            db.insertFitnessMeasure((FitnessMeasure) data);
        else if(table.equals("measuredata"))
            db.insertMeasureData((Measure_Data) data);
        else if(table.equals("windowactivity"))
            db.insertWindowActivity((WindowActivity) data);
        else if(table.equals("rpeaks"))
            db.insertRPeak((RPeaks) data);
        else if(table.equals("heartrate")) {
            db.insertHeartrate((HeartRate) data);
        }
        else if (table.equals(("filteredecg"))){
            /*ArrayList<Measure_Data> md=WindowData.filtered_ecg.get(data);
            db.insertMeasureDatas(md);
            WindowData.filtered_ecg.remove(data);*/
        }
        else if(table.equals("rpeakss")){
            db.insertRPeaks((ArrayList<RPeaks>)data);
        }
        else if(table.equals("measuredatas")){
            //db.insertMeasureDatas((ArrayList<Measure_Data>)WindowData.hardata);
        }
        else throw new UnsupportedOperationException("Table not yet created");
    }

    private void handleActionRetrieve(String table) {
        DatabaseHelper db=new DatabaseHelper(this,"owldb",null,1);
        if(table.equals("measuretype")) {
            ArrayList<Measure_Type> data = db.getAllMeasureTypes();
        }
        else if(table.equals("user")) {
            ArrayList<User> data = db.getAllUsers();
        }
        else if(table.equals("account")) {
            ArrayList<Account> data = db.getAllAccounts();
        }
        else if(table.equals("activity")) {
            ArrayList<Activity> data = db.getAllActivities();
            Log.d("datasize","number of activities "+data.size());
        }
        else if(table.equals("connexion")) {
            ArrayList<Connexion> data = db.getAllConnexions();
        }
        else if(table.equals("fitnessmeasure")) {
            ArrayList<FitnessMeasure> data = db.getAllFitnessMeasures();
        }
        else if(table.equals("measuredata")) {
            ArrayList<Measure_Data> data = db.getAllMeasureData();
            Log.d("datasize","number of measures "+data.size());
        }
        else if(table.equals("windowactivity")) {
            ArrayList<WindowActivity> data = db.getAllWindowActivities();
        }
        else if(table.equals("rpeaks")) {
            ArrayList<RPeaks> data = db.getAllRPeaks();
            Log.d("datasize","number of rpeaks "+data.size());
        }
        else if(table.equals("heartrate")) {
            ArrayList<HeartRate> data = db.getAllHeartRates();
            Log.d("datasize","number of heart rates "+data.size());
        }
        else throw new UnsupportedOperationException("Table not yet created");
    }

}
