package dz.esi.pfe.pfe_app.BLL.Communication;

import android.app.IntentService;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.ArrayList;

import java.util.List;


import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_Processing;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;


public class C_Communication extends IntentService{




    private List<Measure_Data[]> buffer;

    private int bufferSize;
    private static final String ACTION_sub= "sub" ;
    private static final String ACTION_com= "com" ;
    private subClientService subclient;
    private static int size =0;

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        C_Communication.size = size;
    }

    public static Context context;

    private String topics[] = new String[]{"M/MV/C/Acc-x", "M/MV/C/Acc-y", "M/MV/C/Acc-z",
            "M/PHY/H/ECG-1", "M/PHY/H/ECG-2",
            "M/MV/A/Acc-x", "M/MV/A/Acc-y", "M/MV/A/Acc-z", "M/MV/A/Gyr-x", "M/MV/A/Gyr-y", "M/MV/A/Gyr-z",
            "M/MV/A/Mag-x", "M/MV/A/Mag-y", "M/MV/A/Mag-z", "M/MV/W/Acc-x", "M/MV/W/Acc-y", "M/MV/W/Acc-z",
            "M/MV/W/Gyr-x", "M/MV/W/Gyr-y", "M/MV/W/Gyr-z", "M/MV/W/Mag-x", "M/MV/W/Mag-y", "M/MV/W/Mag-z"
    };



    public  C_Communication( ){
        super("C_Communication");
        buffer=new ArrayList<Measure_Data[]>();
        bufferSize=0;
        Log.d("debug_c_commt","instantiated");
    }

    public  void startActionSub(Context context) {
        Intent intent = new Intent(context, C_Communication.class);
        Log.d("debug_c_comm","starting action sub");
        intent.setAction(ACTION_sub);
        context.startService(intent);
    }
    public static void startActionComWindow(Context context, Long wid ) {

        Intent intent = new Intent(context, C_Communication.class);
        intent.setAction(ACTION_com);
        intent.putExtra("wid",wid);
        context.startService(intent);
    }


    @Override
    protected  void onHandleIntent(Intent intent) {

        if (intent != null) {
            Log.d("debug", "inside the service c_comm");
            final String action = intent.getAction();
            if (ACTION_sub.equals(action)) {
                Log.d("debug_c_comm","handling action sub");
                for(int i=0;i<topics.length;i++) {
                    subclient   = new subClientService(this.context, "tcp://192.168.43.79:1883", "120");
                    subclient.subscribe(topics[i],i);}

            }
            if (ACTION_com.equals(action)) {

                System.out.println( " call FE ...");
                Long begin=new java.util.Date().getTime();
                Long finish=begin+3000;
                Long wid=intent.getLongExtra("wid",0);
                S_Processing.startActionFeHar(context,begin,finish,wid);
                Log.d("beginhar", "" + begin);
                Log.d("finishhar",""+finish);
                S_Processing.startActionFePhy(context, begin, finish,wid);
            }

        }
        else Log.d("debug_c_comm","haw l'intent rah null");
    }



    public void removeElement(int index)
    {
        buffer.remove(index);
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void startStreaming()
    {

    }

    public void stopStreaming()
    {

    }



}

