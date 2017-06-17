package dz.esi.pfe.pfe_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.UI.C_Affichage;
import dz.esi.pfe.pfe_app.UI.DayAxisValueFormatter;
import dz.esi.pfe.pfe_app.UI.MyMarker;
import dz.esi.pfe.pfe_app.UI.TimeAxisValueFormatter;

public class LineChartView extends AppCompatActivity {

    CombinedChart courbe;
    Float data[][];
    Float data2[][];
    String title="";
    String x="",y="";
    String datasetname[];
    TextView comments,titre;
    C_Affichage c_affichage;
    Float max[];
    Float min[];
    Float mid[];
    String meanings[];
    int nbn=0,nbm=0,nbl=0,nbh=0;
    double iref[],ref[];
    String iclasses[],classes[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);

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
        // Update max & min
        max=new Float[1];
        max[0]=10.f;

        min=new Float[1];
        min[0]=-10.f;

        mid=new Float[1];
        mid[0]=-5.f;

        meanings=new String[]{"Faible","Normal","Elevé"};

        fill_chart();
        fill_comments();
    }

    /********  Process HR ******/
    private void process_hr(){
        data=c_affichage.getHeartRate();
        x="Temps(min)";
        y="Pulsation (BPM)";
        // Update max & min
        int ind= U_DecisionRules.getIndexCategorieAge(25);
        int[] thresholds=U_DecisionRules.thresholds.get(ind);

        max=new Float[1];
        max[0]=Float.valueOf(thresholds[2]);

        min=new Float[1];
        min[0]=Float.valueOf(thresholds[1]);

        mid=new Float[1];
        mid[0]=min[0]+5;

        meanings=new String[]{"Bradycardie","Normal","Tachycardie"};

        fill_chart();
        fill_comments();
    }

    /********  Process RR ******/
    private void process_rr(){
        data=c_affichage.getRR();
        x="Temps(min)";
        y="R-R";
        // Update max & min
        max=new Float[1];
        max[0]=1.2f;
        min=new Float[1];
        min[0]=0.42f;
        mid=new Float[1];
        mid[0]=0.6f;

        meanings=new String[]{"Court","Normal","Prolongé"};
        fill_chart();
        fill_comments();
    }

    /***********  Process raw fitness ****/
    private void process_raw_fitness(){
        data=c_affichage.getWeights();
        data2=c_affichage.getWaists();
        x="Date(jours)";
        y="Raw";

        // Update max & min
//        max=new Float[2];
//        min=new Float[2];
//        mid=new Float[2];
        meanings=new String[]{"Bas","Normal","Elevé"};
        fill_chart2();
        fill_comments();
    }

    /***********  Process index fitness ****/
    private void process_index_fitness(){
        data=c_affichage.getIMCs();
        data2=c_affichage.getWHRs();
        x="Date(jours)";
        y="Index";

        ref=U_DecisionRules.wh_ref;
        classes=U_DecisionRules.wh_classes;

        iref=U_DecisionRules.ref;
        iclasses=U_DecisionRules.classes;

        // Update max & min
//        max=new Float[2];
//        min=new Float[2];
//        mid=new Float[2];

        fill_chart2();
        fill_comments();
    }

    private void fill_chart2() {

        /// Draw Order
        courbe.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
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
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();

            for (int i = 0; i < data.length; i++) {
                entries.add(new Entry(data[i][0], data[i][1]));
                entries2.add(new Entry(data2[i][0], data2[i][1]));
            }

        int colors[]=new int[]{Color.CYAN,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.RED,Color.GRAY,Color.DKGRAY};
        LimitLine ll;

        try{
            for(int j=0; j<iref.length; j++){
                courbe.getAxis(YAxis.AxisDependency.LEFT).addLimitLine(getLimitLineAt((float)iref[j],iclasses[j],colors[j]));
            }
        }
        catch (NullPointerException exc){
            Log.d("Raw","Raw features don't have norms, please refer to index");
        }

        LineDataSet dataSet = new LineDataSet(entries,datasetname[0]);
        dataSet.setColors(ColorTemplate.rgb("#E6AC1C"));
        dataSet.setCircleColor(ColorTemplate.rgb("#E6AC1C"));
        LineDataSet dataSet2 = new LineDataSet(entries2,datasetname[1]);
        dataSet2.setColors(ColorTemplate.rgb("#d32f2f"));
        dataSet2.setCircleColor(ColorTemplate.rgb("#d32f2f"));
        LineData lineData = new LineData(dataSet);
        lineData.addDataSet(dataSet2);

        // Adding to the chart
        cdata.setData(lineData);
        courbe.setData(cdata);
        courbe.getDescription().setEnabled(false);
        courbe.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        courbe.getXAxis().setValueFormatter(new DayAxisValueFormatter(courbe));

        //courbe.setMarker(new MyMarker(this,R.layout.custom_marker_view_layout));
        courbe.invalidate(); // refresh
    }

    private void fill_chart() {

        /// Draw Order
        courbe.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
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
        List<Entry> entries = new ArrayList<>();
        List<Entry> nentries = new ArrayList<>();
        List<Entry> lentries = new ArrayList<>();
        List<Entry> mentries = new ArrayList<>();
        List<Entry> hentries = new ArrayList<>();

        Entry entry;

        for (int i=0; i<data.length; i++) {
            entry=new Entry(i,data[i][1]);
            if(data[i][1]<min[0]){
                lentries.add(entry);
                nbl++;
            }
            else if(data[i][1]>=min[0] & data[i][1]<=mid[0]){
                mentries.add(entry);
                nbm++;
            }
            else if(data[i][1]>mid[0] & data[i][1]<max[0]){
                nentries.add(entry);
                nbn++;
            }
            else{
                hentries.add(entry);
                nbh++;
            }
            entries.add(entry);
        }

        LineDataSet dataSet = new LineDataSet(entries,datasetname[0]);
        if(datasetname[0].equals("Electrocardiogramme")) dataSet.setColor(Color.RED);
        else dataSet.setColor(Color.GRAY);
        dataSet.setDrawCircles(false);
        //dataSet.setDrawValues(false);

        ScatterDataSet nscatterDataSet = new ScatterDataSet(nentries,meanings[1]);
        nscatterDataSet.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        nscatterDataSet.setColor(Color.GREEN);

        ScatterDataSet lscatterDataSet = new ScatterDataSet(lentries,meanings[0]);
        lscatterDataSet.setScatterShape(ScatterChart.ScatterShape.CHEVRON_DOWN);
        lscatterDataSet.setColor(Color.BLUE);

        ScatterDataSet mscatterDataSet = new ScatterDataSet(mentries,"Tolérable");
        mscatterDataSet.setScatterShape(ScatterChart.ScatterShape.TRIANGLE);
        mscatterDataSet.setColor(Color.YELLOW);

        ScatterDataSet hscatterDataSet = new ScatterDataSet(hentries,meanings[2]);
        hscatterDataSet.setScatterShape(ScatterChart.ScatterShape.CHEVRON_UP);
        hscatterDataSet.setColor(Color.RED);

        LineData pieData = new LineData(dataSet);
        ScatterData sd=new ScatterData();
        //sd.addDataSet(nscatterDataSet);
        sd.addDataSet(hscatterDataSet);
        sd.addDataSet(lscatterDataSet);
        sd.addDataSet(mscatterDataSet);

        /// Adding to the chart
        cdata.setData(pieData);
        cdata.setData(sd);

        courbe.setData(cdata);
        courbe.getDescription().setEnabled(false);
        courbe.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        courbe.getXAxis().setValueFormatter(new TimeAxisValueFormatter());
        courbe.invalidate(); // refresh
    }

    private void fill_comments() {
        try {
            comments.setText("Pourcentage de caractéristiques critiquement anormales: " + 100 * (nbl + nbh) / (nbm + nbl + nbh + nbn) + "% ");
        } catch (ArithmeticException ae) {
            comments.setText("");
        }
    }

    @NonNull
    private LimitLine getLimitLineAt(float xIndex, String label, int color) {
        LimitLine ll = new LimitLine(xIndex); // set where the line should be drawn
        ll.setLineColor(color);
        ll.setLineWidth(1);
        ll.setLabel(label);
        return ll;
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
}
