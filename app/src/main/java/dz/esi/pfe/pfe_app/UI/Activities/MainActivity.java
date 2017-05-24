package dz.esi.pfe.pfe_app.UI.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Date;

import dz.esi.pfe.pfe_app.BLL.Communication.ResultsReceiver;
import dz.esi.pfe.pfe_app.BLL.Communication.subClientService;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_DecisionSupport;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_Processing;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.CSVFile;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.Utilities;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.R;
import dz.esi.pfe.pfe_app.Unit_Tests.MQTT_Test;
import dz.esi.pfe.pfe_app.Unit_Tests.MonitoringTest;
import dz.esi.pfe.pfe_app.Unit_Tests.QueryTests;
import dz.esi.pfe.pfe_app.Unit_Tests.U_DecisionTest;

public class MainActivity extends AppCompatActivity {

    ResultsReceiver receiver=null;
    Boolean receiverRegistered=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        receiver=new ResultsReceiver();

//        String topics[]=new String[]{
//                "M/MV/A/Acc-x","M/MV/A/Acc-y","M/MV/A/Acc-z","M/MV/W/Acc-x","M/MV/W/Acc-y","M/MV/W/Acc-z","M/MV/C/Acc-x","M/MV/C/Acc-y","M/MV/C/Acc-z",
//                "M/PHY/H/ECG-1","M/PHY/H/ECG-2",
//                "M/MV/A/Mag-x","M/MV/A/Mag-y","M/MV/A/Mag-z","M/MV/W/Mag-x","M/MV/W/Mag-y","M/MV/W/Mag-z",
//                "M/MV/A/Gyr-x","M/MV/A/Gyr-y","M/MV/A/Gyr-z","M/MV/W/Gyr-x","M/MV/W/Gyr-y","M/MV/W/Gyr-z"
//        };
//        for(int i=0; i<topics.length;i++) {
//            subClientService subclient = new subClientService(this, "tcp://192.168.43.124:1883", topics[i]);
//            subclient.subscribe();
//        }

        MonitoringTest.context=this;
        U_DecisionRules.load_knowledge();
        MonitoringTest.testMonitoring();

        //U_DecisionTest.context=this;
        //U_DecisionTest.test_RR_Rules();
//        QueryTests.context=this;
//        QueryTests.testActivityWindowInsert();
//        U_DecisionTest.context=this;
//        U_DecisionTest.test_checkHR();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
