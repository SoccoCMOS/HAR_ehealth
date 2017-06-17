package dz.esi.pfe.pfe_app.BLL.Communication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_DecisionSupport;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.Interpretation;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.RPeaks;
import dz.esi.pfe.pfe_app.DAL.Model.WindowActivity;

public  class ResultsReceiver extends BroadcastReceiver {

    private final String ACTION_RECEIVE_ACTIVITY="ACTIVITY_RECEIVED";
    private final String ACTION_RECEIVE_ECG="ECG_RECEIVED";
    //public static HashMap<Long,Integer> progression=new HashMap<>();
    public static int progression=-1;
    public static HeartRate hrt;
    public static WindowActivity wac;
    public static Double[] rpks;
    public static HashMap<Long,Integer> prog=new HashMap<>();

    public ResultsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle;
        if (intent.getAction().equals(ACTION_RECEIVE_ACTIVITY)){
            bundle=intent.getExtras().getBundle("wa");
            //Toast.makeText(context, "wahooactivity "+((WindowActivity)bundle.getSerializable("wa")).getCodeActivity(), Toast.LENGTH_SHORT).show();
            actionActivity((WindowActivity)bundle.get("wa"),context);
        }
        else if (intent.getAction().equals(ACTION_RECEIVE_ECG)){
            bundle=intent.getExtras().getBundle("phy");
            //Toast.makeText(context,"wahooecg "+((HeartRate) bundle.getSerializable("hr")).getValue(),Toast.LENGTH_SHORT).show();
            actionECG((HeartRate)bundle.get("hr"),(Double[]) bundle.get("rpeaks"),context);
        }
    }

    private void actionActivity(WindowActivity wa, Context context){
        Log.d("broadcastwa",(wa==null)+"");

        if(prog.get(wa.getIdWindow())==null){
            wac=wa;
            prog.put(wa.getIdWindow(),0);
        }
        else if(prog.get(wa.getIdWindow())==1){
            S_DecisionSupport.startActionCallback(context,hrt,rpks,Integer.valueOf(wa.getCodeActivity()));
            if(Integer.valueOf(wa.getCodeActivity())<=3){
                WindowData.lasthr=hrt.getValue();
            }
            else{
                WindowData.lastahr=hrt.getValue();
            }
            prog.remove(wa.getIdWindow());
        }
        Log.d("broadcastwac",(wac==null)+"");
    }
    private void actionECG(HeartRate hr, Double[] rpeaks, Context context){

        Log.d("broadcasthr",(hr==null)+"");
        if(prog.get(hr.getId())==null){
            hrt=hr;
            rpks=rpeaks;
            prog.put(hr.getId(),1);
        }
        else if(prog.get(hr.getId())==0){
            S_DecisionSupport.startActionCallback(context,hr,rpeaks,Integer.valueOf(wac.getCodeActivity()));
            if(Integer.valueOf(wac.getCodeActivity())<=3){
                WindowData.lasthr=hr.getValue();
            }
            else{
                WindowData.lastahr=hr.getValue();
            }
            prog.remove(hr.getId());
        }

        Log.d("broadcasthrt",(hrt==null)+"");
    }
}