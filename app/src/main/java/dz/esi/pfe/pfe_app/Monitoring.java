package dz.esi.pfe.pfe_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Monitoring extends AppCompatActivity {

    ExpandableListView list_activity;//, list_heart, list_fitness;
    ExpandableListAdapter adapter_activity;//, adapter_heart, adapter_fitness;
    List<String> listDataHeader_activity;//, listDataHeader_heart, listDataHeader_fitness;
    HashMap<String, List<String>> listDataChild_activity;//, listDataChild_heart, listDataChild_fitness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo_appbar);
        menu.setDisplayUseLogoEnabled(true);

        list_activity=(ExpandableListView) findViewById(R.id.list_activity);
        //list_heart=(ExpandableListView) findViewById(R.id.list_heart);
        //list_fitness=(ExpandableListView) findViewById(R.id.list_fitness);
        
        prepareListActivity();
        //prepareListHeart();
        //prepareListFitness();

        adapter_activity = new dz.esi.pfe.pfe_app.UI.ExpandableListAdapter(this, listDataHeader_activity, listDataChild_activity);
        //adapter_heart = new dz.esi.pfe.pfe_app.UI.ExpandableListAdapter(this, listDataHeader_heart, listDataChild_heart);
        //adapter_fitness = new dz.esi.pfe.pfe_app.UI.ExpandableListAdapter(this, listDataHeader_fitness, listDataChild_fitness);

        // setting list adapters
        list_activity.setAdapter(adapter_activity);
        //list_heart.setAdapter(adapter_heart);
        //list_fitness.setAdapter(adapter_fitness);
        list_activity.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int order=0;
                Intent intent=null;
                if(groupPosition==1 & childPosition==0){
                    intent=new Intent(getApplicationContext(),LineChartView.class);
                    order=0;
                }
                else if (groupPosition==1 & childPosition==1){
                    intent=new Intent(getApplicationContext(),LineChartView.class);
                    order=1;
                }
                else if (groupPosition==1 & childPosition==6){
                    intent=new Intent(getApplicationContext(),LineChartView.class);
                    order=2;
                }
                else if (groupPosition==2 & childPosition==0){
                    intent=new Intent(getApplicationContext(),LineChartView.class);
                    order=3;
                }
                else if (groupPosition==2 & childPosition==1){
                    intent=new Intent(getApplicationContext(),LineChartView.class);
                    order=4;
                }
                else if(groupPosition==0 & childPosition==2){
                    intent=new Intent(getApplicationContext(),PieChartView.class);
                    order=0;
                }
                else if(groupPosition==1 & childPosition==11) {
                    intent = new Intent(getApplicationContext(), PieChartView.class);
                    order = 1;
                }
                else if(groupPosition==0 & childPosition==0){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=0;
                }
                else if(groupPosition==0 & childPosition==1){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=1;
                }
                else if(groupPosition==1 & childPosition==2){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=2;
                }
                else if(groupPosition==1 & childPosition==3){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=3;
                }
                else if(groupPosition==1 & childPosition==4){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=4;
                }
                else if(groupPosition==1 & childPosition==7){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=5;
                }
                else if(groupPosition==1 & childPosition==8){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=6;
                }
                else if(groupPosition==1 & childPosition==9){
                    intent = new Intent(getApplicationContext(), BarChartActivity.class);
                    order=7;
                }
                else if(groupPosition==1 & childPosition==5){
                    intent = new Intent(getApplicationContext(), CombinedChartActivity.class);
                    order=0;
                }
                else if(groupPosition==1 & childPosition==10){
                    intent = new Intent(getApplicationContext(), CombinedChartActivity.class);
                    order=1;
                }
                else return false;

                intent.putExtra("type",order);
                intent.putExtra("title",listDataChild_activity.get(
                        listDataHeader_activity.get(groupPosition)).get(
                        childPosition));
                startActivity(intent);
                return false;
            }
        });
    }

    private void prepareListActivity() {
        listDataHeader_activity = new ArrayList<String>();
        listDataChild_activity = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader_activity.add("Indicateurs d'activité");
        listDataHeader_activity.add("Indicateurs de santé cardiaque");
        listDataHeader_activity.add("Indicateurs de fitness");

        List<String> activity = new ArrayList<String>();
        activity.add("Durée quotidienne/hebdomadaire de pratique par activité");
        activity.add("Durée quotidienne/hebdomadaire de pratique par catégorie d’intensité de l’activité ");
        activity.add("Pourcentage du volume d’activité satisfait par rapport au volume recommandé par l’OMS");

        List<String> heart = new ArrayList<String>();
        heart.add("Tracé de l’électrocardiogramme débruité ");
        heart.add("Evolution temporelle seule de la fréquence cardiaque ");
        heart.add("Moyenne de la fréquence cardiaque durant les activités de repos");
        heart.add("Moyenne de la fréquence cardiaque durant les activités faible à intense");
        heart.add("Moyenne de la fréquence cardiaque pour chaque catégorie d’intensité d’activité");
        heart.add("Evolution temporelle conjointe de la fréquence cardiaque et de l’activité ");
        heart.add("Evolution temporelle seule des intervalles R-R");
        heart.add("Moyenne de la taille des intervalles R-R durant les activités de repos");
        heart.add("Moyenne de la taille des intervalles R-R durant les activités faible à intense ");
        heart.add("Moyenne de la taille des intervalles R-R pour chaque catégorie d’intensité d’activité ");
        heart.add("Evolution temporelle conjointe de la taille des intervalles R-R et de l’activité");
        heart.add("Nombre d’anomalies décelées sur nombre de fenêtres par type d’arythmie ");

        List<String> fitness = new ArrayList<String>();
        fitness.add("Evolution temporelle du poids et tour de taille ");
        fitness.add("Evolution temporelle de l’Indice de Masse Corporelle (IMC) et Ratio tour de taille/taille (WtHR) ");

        listDataChild_activity.put(listDataHeader_activity.get(0),activity);
        listDataChild_activity.put(listDataHeader_activity.get(1),heart);
        listDataChild_activity.put(listDataHeader_activity.get(2),fitness);
    }
    
}
