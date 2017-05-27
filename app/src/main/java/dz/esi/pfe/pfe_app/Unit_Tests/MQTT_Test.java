package dz.esi.pfe.pfe_app.Unit_Tests;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import dz.esi.pfe.pfe_app.BLL.Communication.C_Communication;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.Utilities;

/**
 * Created by DUALCOMPUTER on 5/22/2017.
 */
public class MQTT_Test {

    public  static Context context;

    public static void MQTT_test() {
        U_DecisionRules.load_knowledge();  // intialise knowledge ta3 CDS

        try {
            Utilities.initialize(context);
            Utilities.sessionconfig(context); // hna tu peux changer user info
        }
        catch(SQLiteException e) {
            e.printStackTrace();
        }

        Log.d("debug_mqtt_test","after initialization");
        WindowData.context=context;
        WindowData.intialize();
        C_Communication.context=context;
        C_Communication c = new C_Communication();
        Log.d("debug_mqtt_test","before starting action sub");
        c.startActionSub(context);
    }

    public static  void GetMessages()
    {
    }
}
