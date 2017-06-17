package dz.esi.pfe.pfe_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.Interpretation;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.UI.ActivityAxisValueFormatter;
import dz.esi.pfe.pfe_app.UI.C_Affichage;
import dz.esi.pfe.pfe_app.UI.DateTimeAxisValueFormatter;
import dz.esi.pfe.pfe_app.UI.MyMarker;
import dz.esi.pfe.pfe_app.UI.TimeAxisValueFormatter;

public class CombinedChartActivity extends AppCompatActivity {

    CombinedChart courbe;
    Float data[][];
    Float data2[][];
    String title="";
    int nbanormal=0;
    int nb=0;
    int order;

    String datasetname[];
    TextView comments,titre;
    C_Affichage c_affichage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_chart);
        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo_appbar);
        menu.setDisplayUseLogoEnabled(true);

        c_affichage=new C_Affichage(this);
        courbe=(CombinedChart) findViewById(R.id.courbe);
        comments=(TextView) findViewById(R.id.comm);
        titre=(TextView) findViewById(R.id.title);
        title=getIntent().getStringExtra("title");
        titre.setText(title);

        order=getIntent().getIntExtra("type",0);
        switch(order){
            case 0:{
                datasetname=new String[2];
                datasetname[0]="Fréquence cardiaque";
                datasetname[1]="Activité";
                process_ahr();
                break;
            }
            case 1:{
                datasetname=new String[2];
                datasetname[0]="Variabilité du rythme";
                datasetname[1]="Activité";
                process_arr();
                break;
            }
            default: break;
        }
    }

    private void process_arr() {
        data2=c_affichage.getActivities();
        data=c_affichage.getRR();
        fill_chart();
        fill_comments();
    }

    private void process_ahr() {
        data2=c_affichage.getActivities();
        data=c_affichage.getHeartRate();
        fill_chart();
        fill_comments();
    }

    private void fill_chart() {

        /// Draw Order
        courbe.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.BAR
        });

        ///  Legend
        Legend l = courbe.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        /// Dataset preparation
        CombinedData cdata = new CombinedData();
        List<Entry> centries = new ArrayList<>();
        List<BarEntry> barentries = new ArrayList<>();

        Entry entry;
        BarEntry entry2;

        for (int i=0; i<data.length; i++) {
            entry=new Entry(i,data[i][1]);
            entry2=new BarEntry(i,U_DecisionRules.getCategoryActivity(data2[i][1].intValue()));
            Log.d("heartrate ",""+data[i][1]);
            centries.add(entry);
            barentries.add(entry2);

            if(order==0){
                Interpretation inter=U_DecisionRules.interprete_hr(data[i][1].doubleValue(),data2[i][1].intValue(),25,Gender.Female);
                if(!inter.getNormal()) nbanormal++;
                nb++;
            }

            if(order==1){
                Interpretation inter=U_DecisionRules.interprete_rr(data[i][1].doubleValue());
                if(!inter.getNormal()) nbanormal++;
                nb++;
            }
        }

        LineDataSet dataSet = new LineDataSet(centries,datasetname[0]);
        LineData lineData = new LineData(dataSet);
        dataSet.setColors(ColorTemplate.rgb("#d32f2f"));
        dataSet.setCircleColor(ColorTemplate.rgb("#d32f2f"));
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet barDataSet = new BarDataSet(barentries,datasetname[1]);
        BarData barData = new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.rgb("#E6AC1C"));
        barDataSet.setBarBorderColor(ColorTemplate.rgb("#E6AC1C"));
        barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        /// Adding to the chart
        cdata.setData(lineData);
        cdata.setData(barData);

        courbe.setData(cdata);
        courbe.getDescription().setEnabled(false);
        courbe.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        courbe.getXAxis().setValueFormatter(new DateTimeAxisValueFormatter());
        courbe.getAxisRight().setValueFormatter(new ActivityAxisValueFormatter());
        YAxis yAxis = courbe.getAxisRight();
        yAxis.setGranularity(1f); // interval 1
        yAxis.setLabelCount(4, true);
        MarkerView mv=new MyMarker(this,R.layout.custom_marker_view_layout);
        courbe.setMarker(mv);
        courbe.invalidate(); // refresh
    }

    private void fill_comments() {
        comments.setText("Pourcentage de valeurs anormales: "+ Math.round(nbanormal*100/nb) + "%");
    }

    public void export(View view) {
        Bitmap bmp=courbe.getChartBitmap();
        savebmp(bmp);

    }

    private void savebmp(Bitmap bmp){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh(View view) {
        if (order==0){
            process_ahr();
        }
        else{
            process_arr();
        }
    }
}
