package dz.esi.pfe.pfe_app.DAL.DataAccessObjects;

import android.content.Context;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.CSVFile;
import dz.esi.pfe.pfe_app.DAL.Model.Account;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.DAL.Model.Connexion;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.BloodGroup;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Position;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Type;
import dz.esi.pfe.pfe_app.DAL.Model.User;
import dz.esi.pfe.pfe_app.DAL.S_DataAccess;
import dz.esi.pfe.pfe_app.R;

public class Utilities {

    public static void initialize(Context context)
    {
        //// Creating the database tables
        S_DataAccess.startActionCreate(context);

        /// Available activities
        for (int i=0; i<=12; i++)
            S_DataAccess.startActionInsert(context,"activity",new Activity(i));
    }

    public static void sessionconfig(Context context)
    {
        /// User info: account, user and fitness measures
        S_DataAccess.startActionInsert(context,"account",new Account("socco","sara.simoussi@gmail.com"));
        S_DataAccess.startActionInsert(context,"user",new User("socco","Sara", Gender.Female,new Date(1994,4,19), BloodGroup.B));

        /// Available measures
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("WAccX","","", Position.WAIST));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("WAccY","","", Position.WAIST));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("WAccZ","","", Position.WAIST));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AAccX","","", Position.ARM));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AAccY","","", Position.ARM));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AAccZ","","", Position.ARM));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LAccX","","", Position.LEG));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LAccY","","", Position.LEG));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LAccZ","","", Position.LEG));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AGyrX","","", Position.ARM));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AGyrY","","", Position.ARM));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AGyrZ","","", Position.ARM));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LGyrX","","", Position.LEG));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LGyrY","","", Position.LEG));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LGyrZ","","", Position.LEG));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AMagX","","", Position.ARM));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AMagY","","", Position.ARM));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("AMagZ","","", Position.ARM));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LMagX","","", Position.LEG));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LMagY","","", Position.LEG));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("LMagZ","","", Position.LEG));

        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("ECGL1","","", Position.WAIST));
        S_DataAccess.startActionInsert(context,"measuretype",new Measure_Type("ECGL2","","", Position.WAIST));

        Date datetime=new Date(Calendar.getInstance().getTimeInMillis());
        /// Connexions
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(0, datetime,datetime,50,"socco","WAccX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(1, datetime,datetime,50,"socco","WAccY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(2, datetime,datetime,50,"socco","WAccZ"));

        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(3, datetime,datetime,50,"socco","AAccX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(4, datetime,datetime,50,"socco","AAccY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(5, datetime,datetime,50,"socco","AAccZ"));

        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(6, datetime,datetime,50,"socco","LAccX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(7, datetime,datetime,50,"socco","LAccY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(8, datetime,datetime,50,"socco","LAccZ"));


        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(9, datetime,datetime,50,"socco","AMagX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(10, datetime,datetime,50,"socco","AMagY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(11, datetime,datetime,50,"socco","AMagZ"));

        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(12, datetime,datetime,50,"socco","LMagX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(13, datetime,datetime,50,"socco","LMagY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(14, datetime,datetime,50,"socco","LMagZ"));


        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(15, datetime,datetime,50,"socco","AGyrX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(16, datetime,datetime,50,"socco","AGyrY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(17, datetime,datetime,50,"socco","AGyrZ"));

        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(18, datetime,datetime,50,"socco","LGyrX"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(19, datetime,datetime,50,"socco","LGyrY"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(20, datetime,datetime,50,"socco","LGyrZ"));


        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(21, datetime,datetime,50,"socco","ECGL1"));
        S_DataAccess.startActionInsert(context,"connexion",
                new Connexion(22, datetime,datetime,50,"socco","ECGL2"));
    }

    public static void fillData(Context context){
        InputStream inputStream1;
        inputStream1= context.getResources().openRawResource(R.raw.ecg2);
        CSVFile csvFile1 = new CSVFile(inputStream1);
        csvFile1.read();

        List<Double[]> ecg=csvFile1.ReadfromTo(0,3000);
        ArrayList<Measure_Data> measure_data=new ArrayList<>();
        Date datetime=new Date(Calendar.getInstance().getTimeInMillis());

        for(int i=0; i<ecg.size(); i++)
        {
            measure_data.add(new Measure_Data(i,"ECGL1",ecg.get(i)[0],datetime,"socco"));
            datetime.setTime(datetime.getTime()+1);
            S_DataAccess.startActionInsert(context,"measuredata",measure_data.get(i));
        }
    }

}
