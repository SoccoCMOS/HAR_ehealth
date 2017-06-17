package dz.esi.pfe.pfe_app.UI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.Communication.C_Communication;
import dz.esi.pfe.pfe_app.FollowUp;
import dz.esi.pfe.pfe_app.History;
import dz.esi.pfe.pfe_app.Monitoring;
import dz.esi.pfe.pfe_app.R;
import dz.esi.pfe.pfe_app.SettingsActivity;
import dz.esi.pfe.pfe_app.UI.C_Affichage;

public class DashboardActivity extends AppCompatActivity {

    PieChart daily;
    TextView rHR,aHR,anomalies,critical;
    C_Affichage c_affichage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo_appbar);
        menu.setDisplayUseLogoEnabled(true);

        daily= (PieChart) findViewById(R.id.daily);
        aHR=(TextView) findViewById(R.id.ahr);
        rHR=(TextView) findViewById(R.id.rhr);
        anomalies=(TextView) findViewById(R.id.anormal);
        critical =(TextView) findViewById(R.id.impact);
        c_affichage=new C_Affichage(this);
        fill_chart();
        fill_stats();
    }

    public void fill_stats(){
        aHR.setText(c_affichage.get_mean_ahr() + " Bpm");
        rHR.setText(c_affichage.get_mean_rhr() + " Bpm");
        anomalies.setText(""+c_affichage.get_nb_anomalies());
        critical.setText(c_affichage.get_critical_activity());
    }

    public void fill_chart(){
        String[] labels={"Repos","Faible","Modérée","Intense","Restant"};
        Float[] dataObjects = c_affichage.getActivitiesDuration();

        List<PieEntry> entries = new ArrayList<>();

        for (int i=0; i<dataObjects.length; i++) {
            if(dataObjects[i]!=0)
            entries.add(new PieEntry(dataObjects[i],labels[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, ""); // add entries to dataset
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        daily.getDescription().setEnabled(false);

        Legend legend=daily.getLegend();
        legend.setPosition(Legend.LegendPosition.PIECHART_CENTER);

        PieData pieData = new PieData(dataSet);
        daily.setData(pieData);
        daily.invalidate(); // refresh

    }

    public void launch_monitoring(View view) {
        Intent intent=new Intent(this, Monitoring.class);
        startActivity(intent);
    }

    public void launch_history(View view) {
        Intent intent=new Intent(this, History.class);
        startActivity(intent);
    }

    public void launch_followup(View view) {
        Intent intent=new Intent(this, FollowUp.class);
        startActivity(intent);
    }

    public void launch_help(View view) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            Intent intent=new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.action_sub){
            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit=sharedPreferences.edit();

            if(item.getTitle().equals("Unsubscribe")){
                Log.d("which","rah nsiyi nunsubscribi");
                new C_Communication().startActionUnSub(this);
                edit.putBoolean("acq",false);
                edit.commit();
                item.setTitle("Subscribe");
            }
            else if(item.getTitle().equals("Subscribe")){
                Log.d("which","rah nsiyi nsubscribi");
                new C_Communication().startActionSub(this);
                edit.putBoolean("acq",true);
                edit.commit();
                item.setTitle("Unsubscribe");
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
