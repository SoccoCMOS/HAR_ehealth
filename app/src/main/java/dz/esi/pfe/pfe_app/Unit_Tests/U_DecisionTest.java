package dz.esi.pfe.pfe_app.Unit_Tests;

import android.content.Context;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_DecisionSupport;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;

/**
 * Created by DUALCOMPUTER on 5/21/2017.
 */
public class U_DecisionTest {
    public static Context context;
    public static void test_checkHR(){
        U_DecisionRules.load_knowledge();
        Log.d("instantconstructed", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date(new java.util.Date().getTime() + 500)));
        S_DecisionSupport.startActionCheckHR(context, new HeartRate(0, 60, new Date(new java.util.Date().getTime() + 500), "socco"));
        S_DecisionSupport.startActionCheckHR(context,new HeartRate(0,120,new Date(new java.util.Date().getTime()+4100),"socco"));
    }

    public static void test_DecisionRules()
    {
        U_DecisionRules.load_knowledge();
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getCategorieAge(2)));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getCategorieAge(15)));
        Log.d("DecisionRules", String.valueOf( U_DecisionRules.getCategorieAge(35)));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getCategorieAge(70)));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getIndexCategorieAge(40)));

        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getIntensitéActivité(5)));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getIndexIntensitéActivité(5)));

        //Log.d("DecisionRules", String.valueOf(U_DecisionRules.getMaxHR(Gender.Male)));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.getDuréeActivité(11, 23)));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.interprete_imc(70.0, 168.0).getDetails()));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.interprete_whr(70.0, 42.0).getDetails()));

        Log.d("DecisionRules", String.valueOf(U_DecisionRules.interprete_hr(100.0, 1, 23, Gender.Female).getDetails()));
        Log.d("DecisionRules", String.valueOf(U_DecisionRules.interprete_hr(50.0, 3, 13, Gender.Male).getDetails()));
    }

    public static void test_RR_Rules()
    {
        U_DecisionRules.load_knowledge();
        //Log.d("DecisionRules", U_DecisionRules.interprete_rrreg(new double[]{308, 1008, 1708, 2408}).getDetails());
        //Log.d("DecisionRules", U_DecisionRules.interprete_rrreg(new double[]{308, 1008, 1206, 1653}).getDetails());
        //Log.d("DecisionRules",U_DecisionRules.interprete_rrreg(new double[]{308, 588, 788, 900}).getDetails());

        Log.d("DecisionRules", U_DecisionRules.detect_arythmia(0, 0).getDetails() + " " + U_DecisionRules.detect_arythmia(0, 0).getNormal());
        Log.d("DecisionRules", U_DecisionRules.detect_arythmia(0, 1).getDetails() + " " + U_DecisionRules.detect_arythmia(0, 1).getNormal());
        Log.d("DecisionRules", U_DecisionRules.detect_arythmia(0, 2).getDetails() + " " + U_DecisionRules.detect_arythmia(0,2).getNormal());
        Log.d("DecisionRules", U_DecisionRules.detect_arythmia(1, 0).getDetails() + " " + U_DecisionRules.detect_arythmia(1,0).getNormal());
        Log.d("DecisionRules", U_DecisionRules.detect_arythmia(1, 1).getDetails() + " " + U_DecisionRules.detect_arythmia(1,1).getNormal());
        Log.d("DecisionRules", U_DecisionRules.detect_arythmia(1, 2).getDetails() + " " + U_DecisionRules.detect_arythmia(1,2).getNormal());


        Log.d("DecisionRules", U_DecisionRules.interprete_rr(0.2).getDetails() + " " + U_DecisionRules.interprete_rr(0.2).getNormal());
        Log.d("DecisionRules",U_DecisionRules.interprete_rr(0.5).getDetails() + " " + U_DecisionRules.interprete_rr(0.5).getNormal());
        //Log.d("DecisionRules",U_DecisionRules.interprete_rr(1).getDetails() + " " + U_DecisionRules.interprete_rr(1).getNormal());
        //Log.d("DecisionRules",U_DecisionRules.interprete_rr(2).getDetails() + " " + U_DecisionRules.interprete_rr(2).getNormal());


    }
}
