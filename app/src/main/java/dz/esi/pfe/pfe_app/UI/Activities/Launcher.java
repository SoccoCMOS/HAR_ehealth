package dz.esi.pfe.pfe_app.UI.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dz.esi.pfe.pfe_app.BLL.Communication.ResultsReceiver;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.Utilities;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Launcher extends AppCompatActivity {

    private View mContentView;

    private View mControlsView;

    ResultsReceiver receiver=null;
    Boolean receiverRegistered=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        receiver=new ResultsReceiver();
        Utilities.initialize(this);
        Utilities.sessionconfig(this);
        Utilities.fillData(this);
        ArrayList<Measure_Data> ecg=new DatabaseHelper(this,"owldb",null,1).getECGMeasureData();
        Toast.makeText(this, ""+ecg.size()+" "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(ecg.get(0).getTimestamp()), Toast.LENGTH_LONG).show();
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean logged_in = preferences.getBoolean("login", false);
        Intent intent;
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
