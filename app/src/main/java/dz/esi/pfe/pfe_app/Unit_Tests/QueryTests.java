package dz.esi.pfe.pfe_app.Unit_Tests;

import android.content.Context;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import dz.esi.pfe.pfe_app.DAL.Model.WindowActivity;
import dz.esi.pfe.pfe_app.DAL.S_DataAccess;

/**
 * Created by DUALCOMPUTER on 5/21/2017.
 */
public class QueryTests {

    public static Context context;

    public static void testActivityWindowInsert()
    {
        //Create window activities
        WindowActivity windowActivity1=new WindowActivity(0,new Date(new java.util.Date().getTime()),new Date(new java.util.Date().getTime()+3000),"7","socco");
        WindowActivity windowActivity2=new WindowActivity(1,new Date(new java.util.Date().getTime()+3001),new Date(new java.util.Date().getTime()+6000),"8","socco");

        //Insert window activities
        S_DataAccess.startActionInsert(context,"windowactivity",windowActivity1);
        S_DataAccess.startActionInsert(context,"windowactivity",windowActivity2);
    }

    public static void testGetActivityByInstant()
    {
        // Execute query
        //S_DataAccess.startActionActivity(context,new Date(new java.util.Date().getTime()+500));
        //S_DataAccess.startActionActivity(context,new Date(new java.util.Date().getTime()+4000));
    }
}
