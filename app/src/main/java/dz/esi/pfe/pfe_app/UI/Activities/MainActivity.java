package dz.esi.pfe.pfe_app.UI.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.ActivityRecognition;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_FeatureExtraction;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.CloudStorage.SyncMySQL;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.Model.Account;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.DAL.Model.Connexion;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.BloodGroup;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Position;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Type;
import dz.esi.pfe.pfe_app.DAL.Model.User;
import dz.esi.pfe.pfe_app.R;

public class MainActivity extends AppCompatActivity {

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

        Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
        //DatabaseHelper db=new DatabaseHelper(getApplicationContext(),"patientActivity",null,1);
        //SQLiteDatabase data=db.getWritableDatabase();

        //SyncMySQL syncMySQL=new SyncMySQL(db);

        /*
        // Insert Activity
        ArrayList<HashMap<String,String>> arrayList= db.getAllActivities();
        */
        // Sync Dataabse
        //syncMySQL.syncActivitiesMySQLDB();

//        Calendar currenttime = Calendar.getInstance();
//        //Date sqldate = Date.valueOf("20170403");
//
//        // Insert Connexion
//        db.insertAccount(new Account("socco","sara.simoussi@gmail.com","x"));
//        Date date=new Date(currenttime.getTimeInMillis());
//        System.out.println("Date obtenue = "+date.toString());
//        db.insertUser(new User("socco", "Soccorito", Gender.Female, date, BloodGroup.B));
//
//        db.insertMeasureType(new Measure_Type("Gyr", "Gyroscope", "rad", Position.WAIST));
//
//        db.insertConnexion(new Connexion(3, date, date, 50, "socco", "Gyr"));
//
//        // Sync Database
//        syncMySQL.syncConnexionsMySQLDB();
//        Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
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
}
