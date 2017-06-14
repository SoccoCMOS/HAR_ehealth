package dz.esi.pfe.pfe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import dz.esi.pfe.pfe_app.UI.C_Affichage;

public class PieChartView extends AppCompatActivity {

    PieChart courbe;
    Float data[];
    String labels[];
    String title="";
    String x="",y="";
    TextView comments,titre;
    C_Affichage c_affichage;
    private String datasetname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_view);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo_appbar);
        menu.setDisplayUseLogoEnabled(true);

        c_affichage=new C_Affichage(this);
        courbe=(PieChart) findViewById(R.id.piecourbe);
        comments=(TextView) findViewById(R.id.piecomm);
        titre=(TextView) findViewById(R.id.pietitle);
        title=getIntent().getStringExtra("title");
        titre.setText(title);

        switch(getIntent().getIntExtra("type",0)){
            case 0:{
                datasetname="Proportions des activités";
                process_activities();
                break;
            }
            case 1:{
                datasetname="Proportion des arythmies";
                process_arythmias();
                break;
            }
            default: break;
        }
    }

    /***** Process ACTIVITIES ****/
    private void process_activities(){
        labels=new String[]{"Debout","Assis","Allongé","Marche","Escalade","Penché","Main en air","Accroupi","Vélo","Jogging","Course","Sauts"};
        data=c_affichage.getPercentagesActivity();
        x="Temps";
        y="Taux";
        fill_comments();
        fill_chart();
    }

    /***** Process ARYTHMIAS *****/
    private void process_arythmias(){
        labels=new String[]{"bradycardie","tachycardie","bradyarythmie","tachyarythmie",""};
        data=c_affichage.getPercentagesArythmia();
        x="Temps";
        y="Taux";
        fill_comments();
        fill_chart();
    }

    private void fill_comments() {
        comments.setText("Commentaires");
    }
    private void fill_chart(){

        List<PieEntry> entries = new ArrayList<>();

        for (int i=0; i<data.length; i++) {
            if(data[i]!=0)
            entries.add(new PieEntry(data[i],labels[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, ""); // add entries to dataset
        dataSet.setValueFormatter(new PercentFormatter());
        //courbe.setDrawSliceText(false);
        dataSet.setColors(new int[]{
                ColorTemplate.rgb("#d50000"),
                ColorTemplate.rgb("#00e676"),
                ColorTemplate.rgb("#f50057"),
                ColorTemplate.rgb("#6a1b9a"),
                ColorTemplate.rgb("#ff9100"),
                ColorTemplate.rgb("#00c853"),
                ColorTemplate.rgb("#3e2723"),
                ColorTemplate.rgb("#37474f"),
                ColorTemplate.rgb("#aeea00"),
                ColorTemplate.rgb("#ffd600"),
                ColorTemplate.rgb("#9c27b0"),
                ColorTemplate.rgb("#f44336")
        });
        courbe.getDescription().setEnabled(false);
        courbe.getLegend().setEnabled(false);

        courbe.getLegend().setWordWrapEnabled(true);

        Legend legend=courbe.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(dataSet);
        courbe.setData(pieData);
        courbe.invalidate(); // refresh
    }
}
