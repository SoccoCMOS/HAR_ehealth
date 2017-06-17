package dz.esi.pfe.pfe_app.BLL.DataProcessing;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.Interpretation;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_DecisionRules;
import dz.esi.pfe.pfe_app.DAL.DataAccessObjects.SQLite.DatabaseHelper;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;
import dz.esi.pfe.pfe_app.DAL.Model.Enum.Gender;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.RPeaks;
import dz.esi.pfe.pfe_app.DAL.S_DataAccess;
import dz.esi.pfe.pfe_app.R;

/**
 * Created by DUALCOMPUTER on 5/20/2017.
 */
public class S_DecisionSupport extends IntentService {

    private static final int MY_PERMISSIONS_REQUEST_SMS = 2 ;
    public S_DecisionSupport() {
        super("S_DecisionSupport");
    }

    private static Long count_arythmias=new Long(0);

    private static String ACTION_CHECK_HEART_RATE="checkhr";
    private static String ACTION_CALLBACK="callback";
    public static int anomalies=0;

    public static void startActionCheckHR(Context context,HeartRate hr){
        Intent intent = new Intent(context, S_DecisionSupport.class);
        intent.setAction(ACTION_CHECK_HEART_RATE);
        // pass parameters here
        Bundle bundle=new Bundle();
        bundle.putSerializable("hr",hr);
        intent.putExtra("hr", bundle);
        Log.d("debug", "gonna call the service");
        context.startService(intent);
    }

    public static void startActionCallback(Context context,HeartRate hr, Double[] rpeaks,int codeact){
        Intent intent = new Intent(context, S_DecisionSupport.class);
        intent.setAction(ACTION_CALLBACK);
        // pass parameters here
        Bundle bundle=new Bundle();
        bundle.putSerializable("hr",hr);
        bundle.putSerializable("rpeaks",rpeaks);
        intent.putExtra("phy", bundle);
        intent.putExtra("codeact",codeact);
        Log.d("debug", "gonna call the service");
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d("debug", "inside the service not null");
            final String action = intent.getAction();
            if (ACTION_CHECK_HEART_RATE.equals(action)) {
                Log.d("debug","inside check hr handle");
                // Récupérer les paramètres depuis l'intent
                HeartRate hr=(HeartRate)intent.getExtras().getBundle("hr").get("hr");
                Log.d("instantqueried",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(hr.getTimestamp()));

                S_DataAccess.startActionActivity(this,hr);
            }
            else if (ACTION_CALLBACK.equals(action)){
                HeartRate hr=(HeartRate)intent.getExtras().getBundle("phy").get("hr");
                Double[] rpks=(Double[]) intent.getExtras().getBundle("phy").get("rpeaks");
                int codeact=intent.getIntExtra("codeact", 0);

                Interpretation interpretation[] = U_DecisionRules.interprete_all_rr(hr.getValue(), codeact, 23, Gender.Female, rpks);
//                /**********  Heart-Rate vs activity, age, gender interpretation  **********/
                if(!interpretation[0].getNormal()) {
                    //  Call notification service of the phone */
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.logo_appbar)
                                    .setContentTitle("Window " + hr.getId())
                                    .setContentText("Fréquence cardiaque: " + Math.round(hr.getValue()) + ", Activité: " + new Activity(codeact).getActivityLabel());

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(anomalies, mBuilder.build());
                    anomalies++;
                }
//
//                *//**********  R Peaks standalone interpretation  **********//*
                if(!interpretation[1].getNormal()) {
                    //  Call notification service of the phone *//*
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.logo_appbar)
                                    .setContentTitle("Window "+hr.getId())
                                    .setContentText("Intervalles R-R: " + interpretation[1].getDetails());

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(anomalies, mBuilder.build());
                    anomalies++;
                }

                /**********   Rythm variability ***************/
                if(!interpretation[2].getNormal()) {
                    //  Call notification service of the phone */
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.logo_appbar)
                                    .setContentTitle("Window "+hr.getId())
                                    .setContentText("Rythme : " + interpretation[2].getDetails());

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(anomalies, mBuilder.build());
                    anomalies++;
                }

                                        /**********  Arrythmia detection  **********/

                if(!interpretation[3].getNormal()) {
                    //  Call notification service of the phone */
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.drawable.logo_appbar)
                                    .setContentTitle("Alerte")
                                    .setContentText("Diagnostic probable : " + interpretation[3].getDetails());

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(anomalies, mBuilder.build());
                    anomalies++;
                    WindowData.nbanomalies++;
/*                    if (count_arythmias++ >= 3) {
                        // SEND SMS
                        if (ActivityCompat.checkSelfPermission(, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            //Toast.makeText(getActivity(), "No permission to call", Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(,
                                    new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SMS);
                        } else {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage("0561267262", null, "Your friend needs a cardio checkup ASAP.", null, null);
                        }
                        // REINITIALIZE
                        count_arythmias = Long.valueOf(0);
                    }*/
                }
            }
        }
        else Log.d("debug", "inside the service null intent");
    }

}
