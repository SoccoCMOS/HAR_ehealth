package dz.esi.pfe.pfe_app.UI.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ParseException;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.Communication.C_Communication;
import dz.esi.pfe.pfe_app.BLL.Communication.ResultsReceiver;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.CSVFile;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.Utilities;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.Notification;
import dz.esi.pfe.pfe_app.DAL.Model.WindowActivity;
import dz.esi.pfe.pfe_app.R;
import dz.esi.pfe.pfe_app.Unit_Tests.MQTT_Test;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Launcher extends AppCompatActivity {

    private View mContentView;
    private View mControlsView;

    ResultsReceiver receiver=null;
    Boolean receiverRegistered=false;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        receiver=new ResultsReceiver();
        U_DecisionRules.load_knowledge();
        Utilities.initialize(this);
        Utilities.sessionconfig(this);
        Utilities.fillData(this);

       Resources res = getResources();
        DatabaseHelper db=new DatabaseHelper(this,"owldb",null,1);
        long begin=new java.util.Date().getTime();
        InputStream in_s = res.openRawResource(R.raw.hrr);
        CSVFile csvFile =  new CSVFile(in_s);
        List<Double> hrr = csvFile.readOneC();
        for ( int i=0; i< hrr.size();i++){
            Log.d("hrvalues",""+hrr.get(i));
            HeartRate hr = new HeartRate(i,hrr.get(i),new Date(begin+i*60000),"socco");
            db.insertHeartrate(hr);
        }

        InputStream in_sa = res.openRawResource(R.raw.activ);
        CSVFile csv =  new CSVFile(in_sa);
        List<Double> act = csv.readOneC();
        for ( int i=0; i< act.size();i++){
            WindowActivity hr = new WindowActivity(i,new Date(begin+i*60000) ,new Date(begin+i*60000),act.get(i).toString(),"socco");
            db.insertWindowActivity(hr);
        }
        try {
            System.out.println("size of activities"+db.getAllWindowActivities().size());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        SharedPreferences.Editor edit=preferences.edit();
        edit.putBoolean("acq",true);
        edit.commit();

        MQTT_Test.context=this;
        MQTT_Test.MQTT_test();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!receiverRegistered) {
            registerReceiver(receiver,new IntentFilter("ECG_RECEIVED"));
            registerReceiver(receiver,new IntentFilter("ACTIVITY_RECEIVED"));
            receiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiverRegistered) {
            unregisterReceiver(receiver);
            receiverRegistered = false;
        }
    }

    public void launch(View view) {
        Boolean logged_in = preferences.getBoolean("login", false);
        Intent intent;
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit=preferences.edit();
        edit.putInt("lastactiv",1);
        edit.commit();

        if(!logged_in){
            intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            intent=new Intent(this,DashboardActivity.class);
            startActivity(intent);
        }
    }
}
