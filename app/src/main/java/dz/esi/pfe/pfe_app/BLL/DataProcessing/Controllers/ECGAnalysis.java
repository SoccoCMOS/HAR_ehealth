package dz.esi.pfe.pfe_app.BLL.DataProcessing.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.S_DecisionSupport;
import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.RPeaks;
import dz.esi.pfe.pfe_app.DAL.S_DataAccess;

/**
 * Created by DUALCOMPUTER on 5/19/2017.
 */
public class ECGAnalysis {
    static long count_rp=0;
    Context context;
    Double[] data;
    // Add here ecg features
    RequestQueue queue;
    String url="http://192.168.43.79:8000/ecg/";
    StringRequest request;
    Date begin,end;
    Long wid;

    public ECGAnalysis(Double[] ecg, Context c, Date begin, Date end, Long wid) {
        data = ecg;
        queue = Volley.newRequestQueue(c);
        context=c;
        this.begin=begin;
        this.end=end;
        this.wid=wid;
    }

    public void getECGFeatures(){
        request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response ecglead ", response);
                        // Write it on DB
                        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
                        JSONObject json;
                        try {
                            json=new JSONObject(response);
                            String ts_jar=json.getString("ts");
                            String rpeaks_jar=json.getString("rpeaks");
                            String heart_rate_jar=json.getString("heart_rate");

                            Double[] ts= gsonPretty.fromJson(ts_jar, Double[].class);
                            Integer[][] rpeaks= gsonPretty.fromJson(rpeaks_jar, Integer[][].class);
                            Integer heart_rate= gsonPretty.fromJson(heart_rate_jar, Integer.class);

                            long time=0;

                            /// Writing the R peaks
                            ArrayList<RPeaks> rPeakses=new ArrayList<>();

                            for (int i=0; i<heart_rate; i++){
                                time=begin.getTime()+Math.round(ts[i]);
                                rPeakses.add(new RPeaks(count_rp, data[rpeaks[0][i]], new Date(time), "socco"));
                                count_rp++;
                            }
                            S_DataAccess.startActionInsert(context,"rpeakss",rPeakses);
                            time=(begin.getTime()+end.getTime())/2;
                            HeartRate heartRate= new HeartRate(wid, heart_rate, new Date(time),"socco");
                            S_DataAccess.startActionInsert(context, "heartrate",heartRate);

                            Intent i = new Intent("ECG_RECEIVED");
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("hr",heartRate);
                            bundle.putSerializable("rpeaks",ts);
                            i.putExtra("phy", bundle);
                            context.sendBroadcast(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("wid",String.valueOf(wid));
                params.put("param", Arrays.toString(data));
                Log.d("Params ECG Lead:", String.valueOf(params));
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(request);
    }
}
