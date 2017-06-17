package dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.Interpretation;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.RPeaks;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */
public class U_DecisionRules {

    public static int f_maxhr;
    public static int m_maxhr;

    public static ArrayList<Integer> resting_act;
    public static ArrayList<Integer> weak_act;
    public static ArrayList<Integer> aver_act;
    public static ArrayList<Integer> intens_act;

    public static int[] ages;
    public static String[] categories;
    public static int nbcat;

    public static double[] ref;
    public static String[] classes;
    public static int nbclass;
    public static int normal;

    public static double[] wh_ref;
    public static String[] wh_classes;
    public static int wh_nbclass;
    public static int wh_normal;

    public static String[] hr_cases;
    public static HashMap<Integer,int[]> thresholds;
    public static int nb_cases;
    public static int hr_normal;

    public static String[] ahr_cases;
    public static HashMap<Integer,int[]> a_thresholds;

    public static HashMap<Integer,int[]> recommendact;

    public static String rr_cases[];
    public static double rr_thresholds[];
    public static int rr_nb_cases, rr_normal;
    public static String arythmias[], rythmias[];
    public static int rreg_nb_cases, index_arythmias;

    public static void load_knowledge(){
        // Maximum Heart rate
        f_maxhr=226;
        m_maxhr=220;

        // Activities intensity
        resting_act=new ArrayList<Integer>(Arrays.asList(1,2,3));
        weak_act=new ArrayList<Integer>(Arrays.asList(6,7,8));
        aver_act=new ArrayList<Integer>(Arrays.asList(4,5,9));
        intens_act=new ArrayList<Integer>(Arrays.asList(10,11,12));

        // Age categories
        nbcat=4;
        ages=new int[4];
        ages[0]=0;
        ages[1]=4;
        ages[2]=16;
        ages[3]=65;

        categories=new String[4];
        categories[0]="Nourrisson";
        categories[1]="Enfant";
        categories[2]="Adulte";
        categories[3]="Vieux";

        // IMC
        nbclass=7;
        ref=new double[]{0,16,18.5,25,30,35,40};
        classes= new String[]{"Famine","Maigreur","Corpulence normale","Surpoids","Obésité modérée","Obésité sévère","Obsésité morbide"};
        normal=2;

        // WHR
        wh_nbclass=5;
        wh_ref=new double[]{0,0.4,0.61,0.67,0.78};
        wh_classes= new String[]{"Sous-poids","Normal","Surpoids","Obésité","Obésité sévère"};
        wh_normal=1;

        // RHR
        hr_cases=new String[]{"Bradycardie","Normal","Tachycardie"};
        nb_cases=3;
        thresholds=new HashMap<>();
        thresholds.put(0,new int[]{0,130,141});
        thresholds.put(1,new int[]{0,80,111});
        thresholds.put(2,new int[]{0,60,81});
        thresholds.put(3,new int[]{0,55,61});
        hr_normal=1;

        // AHR
        ahr_cases=new String[]{"Bas","Normal","Elevé"};
        a_thresholds=new HashMap<>();
        a_thresholds.put(1,new int[]{0,31,50});
        a_thresholds.put(2,new int[]{0,51,70});
        a_thresholds.put(3,new int[]{0,70,100});

        // Activity
        recommendact=new HashMap<>();
        recommendact.put(1,new int[]{360,180});
        recommendact.put(2,new int[]{300,150});
        recommendact.put(3,new int[]{150,75});

        // RR
        rr_cases=new String[]{"Court","Normalcourt","Normal","Prolongé"};
        rr_nb_cases=4;
        rr_normal=2;
        rr_thresholds=new double[]{0,0.42,0.6,1.2};

        // Arythmias
        rreg_nb_cases = 2;
        index_arythmias=1;
        arythmias=new String[]{"Bradyarythmie","Arythmie","Tachyarythmie"};
        rythmias=new String[]{"Sinus Bradycardie","Sinus Normal","Sinus Tachycardie"};
    }

    public static int getCategoryActivity(int codeact){
        switch(codeact){
            case 1:
            case 2:
            case 3: return 1;
            case 6:
            case 7:
            case 8: return 2;
            case 4:
            case 5:
            case 9: return 3;
            case 10:
            case 11:
            case 12: return 4;
        }

        return 1;
    }

                    /************* Interpreting Heart Rate *******************/
    public static Interpretation interprete_hr(Double heartrate, int codeAct, int age, Gender g){
        if(getIntensitéActivité(codeAct).equals("Repos") || codeAct==0)
            return interprete_rhr(heartrate,age);

        else return interprete_ahr(heartrate,codeAct,g,age);
    }

    public static Interpretation interprete_rhr(Double heartrate, int age){
        Interpretation interpretation=new Interpretation();
        int[] cases=thresholds.get(getIndexCategorieAge(age));
        int i;
        for(i=0; i<nb_cases-1;i++){
            if(heartrate<cases[i+1])
            {
                interpretation.setDetails(hr_cases[i]);
                if (i!=hr_normal)
                    interpretation.setNormal(false);
                return interpretation;
            }
        }
        interpretation.setDetails(hr_cases[i]);
        interpretation.setNormal(false);
        return interpretation;
    }

    public static Interpretation interprete_ahr(Double heartrate, int codeActivity, Gender g, int age){
        double pmaxhr=100*heartrate/(double)getMaxHR(g,age);
        Interpretation interpretation=new Interpretation();

        if(codeActivity!=0) {
            int[] cases = a_thresholds.get(getIndexIntensitéActivité(codeActivity));
            Log.d("interpahr", codeActivity + " " + getIndexIntensitéActivité(codeActivity));
            int i;
            for (i = 0; i < nb_cases - 1; i++) {
                if (pmaxhr < cases[i + 1]) {
                    interpretation.setDetails(ahr_cases[i]);
                    if (i != hr_normal) interpretation.setNormal(false);
                    return interpretation;
                }
            }
            interpretation.setDetails(ahr_cases[i]);
            interpretation.setNormal(false);
        }
        else interpretation.setDetails("Unlabeled");
        return interpretation;
    }

    public static Interpretation checkVariability(Double[] hr){
        Interpretation interpretation=new Interpretation();
        boolean constant=true;

        for(int i=0; i<hr.length-1; i++){
            constant=((hr[i+1]-hr[i])==0);
            if(!constant) return new Interpretation(false,"Irrégulier");
        }

        return new Interpretation(true,"Régulier");
    }

    public static Interpretation check_arythmia(int categ_rrreg,int categ_hr){
        Interpretation interpretation=new Interpretation();
        if(categ_rrreg==0)
        {
            interpretation.setDetails(rythmias[categ_hr]);
            if(categ_hr!=hr_normal) interpretation.setNormal(false);
        }
        else if(categ_rrreg==1)
        {
            interpretation.setDetails(arythmias[categ_hr]);
            interpretation.setNormal(false);
        }
        return interpretation;
    }

    public static Interpretation[] interprete_all_hr(Double hr, int codeAct, int age, Gender g, Double hrs[]){
        Interpretation[] interpretations=new Interpretation[4];
        /*** Interpreting heart rate vs age, gender, activity ***/
        interpretations[0]=interprete_hr(hr, codeAct, age, g);
        /*** Interpreting variability ***/
        interpretations[1]=checkVariability(hrs);

        /*** Detecting arythmias ***/
        if(interpretations[0].equals(hr_cases[0]) || interpretations[0].equals(ahr_cases[0])){
            if(interpretations[1].equals("Régulier")) {
                interpretations[2]=detect_arythmia(0,0);
            }
            else{
                interpretations[2]=detect_arythmia(1,0);
            }
        }
        else if(interpretations[0].equals(hr_cases[1]) || interpretations[0].equals(ahr_cases[1])){
            if(interpretations[1].equals("Régulier")) {
                interpretations[2]=detect_arythmia(0,1);
            }
            else{
                interpretations[2]=detect_arythmia(1,1);
            }
        }
        else {
            if(interpretations[1].equals("Régulier")) {
                interpretations[2]=detect_arythmia(0,2);
            }
            else{
                interpretations[2]=detect_arythmia(1,2);
            }
        }
        return interpretations;

    }

                    /************ Interpreting R-R Intervals ******************/

    /***************   Holistic interpretation *********************/
    public static Interpretation[] interprete_all_rr(Double hr, int codeAct, int age, Gender g, Double rpks[]){
        Interpretation[] interpretations=new Interpretation[4];
        /*** Interpreting heart rate vs age, gender, activity ***/
        interpretations[0]=interprete_hr(hr, codeAct, age, g);

        /*** Interpreting R-R intervals size ***/
        Double[] rr_intervals=process_rrintervals(rpks);
        boolean rr_sizes_normal=true;
        String details="";
        Interpretation inter;
        for(int i=0; i<rr_intervals.length; i++){
            inter=interprete_rr(rr_intervals[i]);
            rr_sizes_normal=rr_sizes_normal&inter.getNormal();
            details+=inter.getDetails()+" ";
        }
        interpretations[1]=new Interpretation(rr_sizes_normal,details);

        /*** Interpreting rythm variability ***/
        interpretations[2]=interprete_rreg(rr_intervals);

        /*** Detecting arythmias ***/
        if(interpretations[0].equals(hr_cases[0]) || interpretations[0].equals(ahr_cases[0])){
            if(interpretations[2].equals("Régulier")) {
                interpretations[3]=detect_arythmia(0,0);
            }
            else{
                interpretations[3]=detect_arythmia(1,0);
            }
        }
        else if(interpretations[0].equals(hr_cases[1]) || interpretations[0].equals(ahr_cases[1])){
            if(interpretations[2].equals("Régulier")) {
                interpretations[3]=detect_arythmia(0,1);
            }
            else{
                interpretations[3]=detect_arythmia(1,1);
            }
        }
        else {
            if(interpretations[2].equals("Régulier")) {
                interpretations[3]=detect_arythmia(0,2);
            }
            else{
                interpretations[3]=detect_arythmia(1,2);
            }
        }
        return interpretations;
    }

    public static Interpretation interprete_rr(Double rr){
        Interpretation interpretation=new Interpretation();
        int i;
        for(i=0; i<rr_nb_cases-1;i++){
            if(rr<rr_thresholds[i+1])
            {
                interpretation.setDetails(rr_cases[i]);
                if (i!=rr_normal) interpretation.setNormal(false);
                return interpretation;
            }
        }
        interpretation.setDetails(rr_cases[i]);
        interpretation.setNormal(false);
        return interpretation;
    }

    public static Double[] process_rrintervals(Double peaks[]){
        Double rr_intervals[]=new Double[peaks.length-1];

        for(int i=0; i<peaks.length-1; i++){
            rr_intervals[i]=peaks[i+1]-peaks[i];
        }
        return rr_intervals;
    }

    public static Interpretation interprete_rreg(Double rr_intervals[]){
        Interpretation interpretation=new Interpretation();
        boolean constant=true;

        for(int i=0; i<rr_intervals.length-1; i++){
            constant=((rr_intervals[i+1]-rr_intervals[i])==0);
            if(!constant) return new Interpretation(false,"Irrégulier");
        }

        return new Interpretation(true,"Régulier");
    }

    public static Interpretation interprete_rrreg(Double peaks[]){
        Double rr_intervals[]=process_rrintervals(peaks);
        Interpretation interpretation=new Interpretation();
        boolean constant=true;

        for(int i=0; i<rr_intervals.length-1; i++){
            constant=((rr_intervals[i+1]-rr_intervals[i])==0);
            if(!constant) return new Interpretation(false,"Irrégulier");
        }

        return new Interpretation(true,"Régulier");
    }

    public static Interpretation detect_arythmia(int categ_rrreg,int categ_hr){
        Interpretation interpretation=new Interpretation();
        if(categ_rrreg==0)
        {
            interpretation.setDetails(rythmias[categ_hr]);
            if(categ_hr!=hr_normal) interpretation.setNormal(false);
        }
        else if(categ_rrreg==1)
        {
            interpretation.setDetails(arythmias[categ_hr]);
            interpretation.setNormal(false);
        }
        return interpretation;
    }

                    /************* Interpreting Fitness Data *******************/
    public static Interpretation interprete_imc(Double weight, Double height){
        Interpretation interpretation=new Interpretation();
        double imc=weight*weight/height;
        int i;
        for(i=0; i<ref.length-1;i++){
            if(imc<ref[i+1])
            {
                interpretation.setDetails(classes[i]);
                if (i!=normal) interpretation.setNormal(false);
                return interpretation;
            }
        }
        interpretation.setDetails(classes[i]);
        interpretation.setNormal(false);
        return interpretation;
    }

    public static Interpretation interprete_whr(Double height, Double waist){
        Interpretation interpretation=new Interpretation();
        double whr=waist/height;
        int i;
        for(i=0; i<wh_ref.length-1;i++){
            if(whr<wh_ref[i+1])
            {
                interpretation.setDetails(wh_classes[i]);
                if (i!=wh_normal) interpretation.setNormal(false);
                return interpretation;
            }
        }
        interpretation.setDetails(wh_classes[i]);
        interpretation.setNormal(false);
        return interpretation;
    }

                    /************* Utilities *******************/
    public static String getCategorieAge(int age){
        int i;
        for(i=0; i<ages.length-1;i++){
            if(age<ages[i+1]) return categories[i];
        }
        return categories[i];
    }

    public static int getIndexCategorieAge(int age){
        int i;
        for(i=0; i<ages.length-1;i++){
            if(age<ages[i+1]) return i;
        }
        return i;
    }

    public static int getMaxHR(Gender g, int age){
        if(g==Gender.Male) return m_maxhr-age;
        return f_maxhr-age;
    }

    public static String getIntensitéActivité(int code){
        if(intens_act.contains(code))return "Intense";
        if(resting_act.contains(code)) return "Repos";
        if(weak_act.contains(code)) return "Faible";
        if(aver_act.contains(code)) return "Modéré";
        return "Unlabeled";
    }

    public static int getIndexIntensitéActivité(int code){
        if(intens_act.contains(code))return 3;
        if(resting_act.contains(code)) return 0;
        if(weak_act.contains(code)) return 1;
        if(aver_act.contains(code)) return 2;
        return -1;
    }

                        /************* Exercising Recommandations *******************/
    public static int[] getDuréeActivité(int codeAct, int age){
        int[] recommendations={0,0};
        int age_categorie=getIndexCategorieAge(age);
        if(age_categorie!=0){
            recommendations[0] =recommendact.get(age_categorie)[0];
            recommendations[1] =recommendact.get(age_categorie)[1];
        }
        return recommendations;
    }

}
