package dz.esi.pfe.pfe_app.BLL.DataProcessing;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Controllers.C_Processing;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Controllers.ECGAnalysis;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;

public class S_Processing extends IntentService {

    private static final String ACTION_HAR = "fehar" ;
    private static final String ACTION_PHYSIO = "fephy";
    private static final long wid=0;

    public S_Processing() {
        super("S_Processing");
    }

    public static void startActionFeHar(Context context, long begin, long end, long wid) {
        Intent intent = new Intent(context, S_Processing.class);
        intent.setAction(ACTION_HAR);
        intent.putExtra("begin", begin);
        intent.putExtra("end",end);
        intent.putExtra("wid", wid);
        Log.d("onstartfehar", "called start action fe har");
        // pass parameters here
        context.startService(intent);
    }

    public static void startActionFePhy(Context context, long begin, long end, Long wid) {
        Intent intent = new Intent(context, S_Processing.class);
        intent.setAction(ACTION_PHYSIO);
        intent.putExtra("begin", begin);
        intent.putExtra("end", end);
        intent.putExtra("wid", wid);
        Log.d("onstartfephy", "called start action fe physio");
        // pass parameters here
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("sv","started service"+intent.getAction());
        Date begin=new Date(intent.getLongExtra("begin",0));
        Date end=new Date(intent.getLongExtra("end",0));
        Long wid=intent.getLongExtra("wid",0);

        if(intent.getAction().equals(ACTION_HAR)) {

            List<Double[]> wd = WindowData.hhardata.get(wid);
            Log.d("ZMAR","" + (WindowData.hhardata==null) + "  " + (WindowData.hhardata.get(wid)==null));
            // Set cp window

            C_Processing cp = new C_Processing(this,wid);
            cp.setWindow(wd);
            // WindowData.hardata.clear();
            // Launch processWindow from cp
            Log.d("Zmar2",""+ (wd==null));
            cp.processWindow(wd.size(), wd.get(0).length,begin,end);

        }
        else if (intent.getAction().equals(ACTION_PHYSIO)){
            int sz=WindowData.hphydata.get(wid).get(0).length;
            Double[] wd = new Double[sz];
            for(int i=0; i<sz; i++)
                wd[i]= WindowData.hphydata.get(wid).get(0)[i];
            ECGAnalysis ea=new ECGAnalysis(wd,this,begin,end,wid);
            ea.getECGFeatures();
        }
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("sv", "on start service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("sv", "on destroy service");
    }
}
