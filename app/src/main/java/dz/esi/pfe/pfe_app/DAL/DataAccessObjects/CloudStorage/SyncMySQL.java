package dz.esi.pfe.pfe_app.DAL.DataAccessObjects.CloudStorage;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.Model.Connexion;
import dz.esi.pfe.pfe_app.UI.Activities.MainActivity;

/**
 * Created by DUALCOMPUTER on 4/2/2017.
 */
public class SyncMySQL {

    DatabaseHelper controller;

    public SyncMySQL(DatabaseHelper databaseHelper) {
        this.controller = databaseHelper;
    }

    public void syncActivitiesMySQLDB() {
        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<HashMap<String, String>> activityList = controller.getAllActivities();
        if (activityList.size() != 0) {
            //System.out.println("array list has " + activityList.size() + " items");
            params.put("activitiesJSON", controller.composeJSONfromActivities());
            // EXECUTE PHP Script
            client.post("http://192.168.1.4/sqlitemysqlsync/activity/insertactivity.php", params, new AsyncHttpResponseHandler());
        }
    }

    public void syncConnexionsMySQLDB(){
        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<Connexion> connexionList = controller.getAllConnexions();
        if (connexionList.size() != 0) {
            System.out.println("array list has " + connexionList.size() + " items");
            params.put("connexionsJSON", controller.composeJSONfromConnexions());
            // EXECUTE PHP Script
            client.post("http://192.168.1.4/sqlitemysqlsync/connexion/insertconnexion.php", params, new AsyncHttpResponseHandler());
        }
        else
            System.out.println("List is empty");

    }

}
