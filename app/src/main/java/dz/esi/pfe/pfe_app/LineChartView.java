package dz.esi.pfe.pfe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.esi.pfe.pfe_app.UI.C_Affichage;
import dz.esi.pfe.pfe_app.UI.DayAxisValueFormatter;
import dz.esi.pfe.pfe_app.UI.TimeAxisValueFormatter;

public class LineChartView extends AppCompatActivity {

    LineChart courbe;
    Float data[][];
    Float data2[][];
    String title="";
    String x="",y="";
    String datasetname[];
    TextView comments,titre;
    C_Affichage c_affichage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo_appbar);
        menu.setDisplayUseLogoEnabled(true);

        c_affichage=new C_Affichage(this);
        courbe=(LineChart) findViewById(R.id.courbe);
        comments=(TextView) findViewById(R.id.comm);
        titre=(TextView) findViewById(R.id.title);
        title=getIntent().getStringExtra("title");
        titre.setText(title);

        switch(getIntent().getIntExtra("type",0)){
            case 0:{
                datasetname=new String[1];
                datasetname[0]="Electrocardiogramme";
                process_ecg();
                break;
            }
            case 1:{
                datasetname=new String[1];
                datasetname[0]="Fréquence cardiaque";
                process_hr();
                break;
            }
            case 2:{
                datasetname=new String[1];
                datasetname[0]="Intervalles R-R";
                process_rr();
                break;
            }
            case 3:{
                datasetname=new String[2];
                datasetname[0]="Poids";
                datasetname[1]="Tour de taille";
                process_raw_fitness();
                break;
            }
            case 4:{
                datasetname=new String[2];
                datasetname[0]="Indice de Masse Corporelle";
                datasetname[1]="Indice de Masse Abdominale";
                process_index_fitness();
                break;
            }
            default: break;
        }
    }

    /********  Process ECG ******/
    private void process_ecg(){
        data=c_affichage.getECG();
        x="Temps(s)";
        y="Amplitude(mV)";
        fill_comments();
        fill_chart();
    }

    /********  Process HR ******/
    private void process_hr(){
        data=c_affichage.getHeartRate();
        x="Temps(min)";
        y="Pulsation (BPM)";
        fill_comments();
        fill_chart();
    }

    /********  Process RR ******/
    private void process_rr(){
        data=c_affichage.getRR();
        x="Temps(min)";
        y="R-R";
        fill_comments();
        fill_chart();
    }

    /***********  Process raw fitness ****/
    private void process_raw_fitness(){
        data=c_affichage.getWeights();
        data2=c_affichage.getWaists();
        x="Date(jours)";
        y="Raw";
        fill_comments();
        fill_chart2();
    }

    /***********  Process index fitness ****/
    private void process_index_fitness(){
        data=c_affichage.getIMCs();
        data2=c_affichage.getWHRs();
        x="Date(jours)";
        y="Index";
        fill_comments();
        fill_chart2();
    }

    private void fill_chart2() {
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        for (int i=0; i<data.length; i++) {
            entries.add(new Entry(data[i][0],data[i][1]));
            entries2.add(new Entry(data2[i][0],data2[i][1]));
        }

        LineDataSet dataSet = new LineDataSet(entries,datasetname[0]);
        dataSet.setColors(ColorTemplate.rgb("#E6AC1C"));
        dataSet.setCircleColor(ColorTemplate.rgb("#E6AC1C"));
        LineDataSet dataSet2 = new LineDataSet(entries2,datasetname[1]);
        dataSet2.setColors(ColorTemplate.rgb("#d32f2f"));
        dataSet2.setCircleColor(ColorTemplate.rgb("#d32f2f"));
        LineData lineData = new LineData(dataSet);
        lineData.addDataSet(dataSet2);

        courbe.setData(lineData);
        courbe.getDescription().setEnabled(false);
        courbe.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        courbe.getXAxis().setValueFormatter(new DayAxisValueFormatter(courbe));
        courbe.invalidate(); // refresh
    }

    private void fill_chart() {
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i<data.length; i++) {
            entries.add(new Entry(data[i][0],data[i][1]));
        }

        LineDataSet dataSet = new LineDataSet(entries,datasetname[0]);
        LineData pieData = new LineData(dataSet);
        courbe.setData(pieData);

        courbe.getDescription().setEnabled(false);
        courbe.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        courbe.getXAxis().setValueFormatter(new TimeAxisValueFormatter());
        courbe.invalidate(); // refresh
    }

    private void fill_comments() {
        comments.setText("Commentaires");
    }
}
