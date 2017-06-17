package dz.esi.pfe.pfe_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.UI.C_Affichage;

public class BarChartActivity extends AppCompatActivity {

    BarChart courbe;
    Float data[];
    Float indices[];
    String title="";
    String x="",y="";
    TextView comments,titre;
    C_Affichage c_affichage;
    private String datasetname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo_appbar);
        menu.setDisplayUseLogoEnabled(true);

        c_affichage=new C_Affichage(this);
        courbe=(BarChart) findViewById(R.id.barcourbe);
        comments=(TextView) findViewById(R.id.barcomm);
        titre=(TextView) findViewById(R.id.bartitle);
        title=getIntent().getStringExtra("title");
        titre.setText(title);

        switch (getIntent().getIntExtra("type",0)){
            case 0:{
                data=c_affichage.getDurationsPerActivity();
                indices=new Float[12];
                for (int i=1; i<=12; i++)
                    indices[i-1]=Float.valueOf(i);
                x="Activité";
                y="Durée";
                courbe.getXAxis().setValueFormatter(new ActivityValueFormatter());
                break;
            }
            case 1:{
                data=c_affichage.getDurationsPerCategory();
                indices=new Float[4];
                for (int i=1; i<=4; i++)
                    indices[i-1]=Float.valueOf(i);
                x="Catégorie";
                y="Durée";
                courbe.getXAxis().setValueFormatter(new CatActivityValueFormatter());
                break;
            }
            case 2:{
                data=c_affichage.getFCPerRest();
                indices=new Float[3];
                for (int i=1; i<=3; i++)
                    indices[i-1]=Float.valueOf(i);
                x="Activité";
                y="FC";
                courbe.getXAxis().setValueFormatter(new ActivityValueFormatter());
                break;
            }
            case 3:{
                data=c_affichage.getFCPerActiv();
                indices=new Float[9];
                for (int i=4; i<=12; i++)
                    indices[i-4]=Float.valueOf(i);
                x="Activité";
                y="FC";
                courbe.getXAxis().setValueFormatter(new ActivityValueFormatter());
                break;
            }
            case 4:{
                data=c_affichage.getFCPerCategory();
                indices=new Float[4];
                for (int i=1; i<=4; i++)
                    indices[i-1]=Float.valueOf(i);
                x="Catégorie";
                y="FC";
                courbe.getXAxis().setValueFormatter(new CatActivityValueFormatter());
                break;
            }
            case 5:{
                data=c_affichage.getRRPerRest();
                indices=new Float[3];
                for (int i=1; i<=3; i++)
                    indices[i-1]=Float.valueOf(i);
                x="Activité";
                y="RR";
                courbe.getXAxis().setValueFormatter(new ActivityValueFormatter());
                break;
            }
            case 6:{
                data=c_affichage.getRRPerActiv();
                indices=new Float[9];
                for (int i=4; i<=12; i++)
                    indices[i-4]=Float.valueOf(i);
                x="Activité";
                y="RR";
                courbe.getXAxis().setValueFormatter(new ActivityValueFormatter());
                break;
            }
            case 7:{
                data=c_affichage.getRRPerCategory();
                indices=new Float[4];
                for (int i=1; i<=4; i++)
                    indices[i-1]=Float.valueOf(i);
                x="Catégorie";
                y="RR";
                courbe.getXAxis().setValueFormatter(new CatActivityValueFormatter());
                break;
            }
            default: break;
        }
        fill_comments();
        fill_chart();
    }

    private void fill_comments() {
        comments.setText("Commentaires");
    }
    private void fill_chart(){

        List<BarEntry> entries = new ArrayList<>();

        for (int i=0; i<data.length; i++) {
            entries.add(new BarEntry(indices[i],data[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries,""); // add entries to dataset
        dataSet.setColors( new int[]{
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

        courbe.getLegend().setWordWrapEnabled(true);
        courbe.getDescription().setEnabled(false);
        courbe.getLegend().setEnabled(false);
        courbe.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        courbe.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        courbe.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        BarData barData = new BarData(dataSet);
        courbe.setData(barData);
        courbe.invalidate(); // refresh
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

    private class ActivityValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return new Activity(Math.round(value)).getActivityLabel();
        }
    }

    private class CatActivityValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if(Math.round(value)==1)
                return "Repos";
            else if(Math.round(value)==2)
                return "Faible";
            else if(Math.round(value)==3)
                return "Modéré";
            else if(Math.round(value)==4)
                return "Intense";
            else
                return "Inconnue";
        }
    }
}
