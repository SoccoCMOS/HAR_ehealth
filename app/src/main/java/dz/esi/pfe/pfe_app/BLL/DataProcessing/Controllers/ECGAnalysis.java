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

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Structures.WindowData;
import dz.esi.pfe.pfe_app.DAL.Model.HeartRate;
import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;
import dz.esi.pfe.pfe_app.DAL.Model.RPeaks;
import dz.esi.pfe.pfe_app.DAL.S_DataAccess;

/**
 * Created by DUALCOMPUTER on 5/19/2017.
 */
public class ECGAnalysis {
    static long count;
    static long count_hr=0, count_rp=0;
    Context context;
    Double[] data;
    // Add here ecg features
    RequestQueue queue;
    String url="http://192.168.1.6:8000/ecg/";
    StringRequest request;
    Date begin,end;
    int wid;

    public ECGAnalysis(Double[] ecg, Context c, Date begin, Date end, int wid) {
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
                            String filtered_jar=json.getString("filtered");
                            String rpeaks_jar=json.getString("rpeaks");
                            String templates_ts_jar=json.getString("templates_ts");
                            String templates_jar=json.getString("templates");
                            String heart_rate_ts_jar=json.getString("heart_rate_ts");
                            String heart_rate_jar=json.getString("heart_rate");

                            Double[] ts= gsonPretty.fromJson(ts_jar, Double[].class);
                            Double[] filtered= gsonPretty.fromJson(filtered_jar, Double[].class);
                            Integer[] rpeaks= gsonPretty.fromJson(rpeaks_jar, Integer[].class);
                            Double[] templates_ts= gsonPretty.fromJson(templates_ts_jar, Double[].class);
                            Double[][] templates= gsonPretty.fromJson(templates_jar, Double[][].class);
                            Double[] heart_rate_ts= gsonPretty.fromJson(heart_rate_ts_jar, Double[].class);
                            Double[] heart_rate= gsonPretty.fromJson(heart_rate_jar, Double[].class);

                            // Write on DB
//                            Log.d("ts_size",""+ts.length);
//                            Log.d("filtered_size",""+filtered.length);
//                            Log.d("rpeaks_size",""+rpeaks.length);
//                            Log.d("templates_ts_size",""+templates_ts.length);
//                            Log.d("templates_size",""+templates.length);
//                            Log.d("heart_rate_ts_size",""+heart_rate_ts.length);
//                            Log.d("heart_rate_size",""+heart_rate.length);

                            HeartRate heartRate;
                            RPeaks rPeaks;
                            Measure_Data measure_data;
                            long time=0;
                            double hrate=0;

                            /// Writing the R peaks
                            ArrayList<RPeaks> rPeakses=new ArrayList<>();
                            Double[] rpks=new Double[rpeaks.length];
                            for (int i=0; i<rpeaks.length; i++){
                                time=begin.getTime()+Math.round(ts[rpeaks[i]]*1000);
                                rPeakses.add(new RPeaks(count_rp, filtered[rpeaks[i]], new Date(time), "socco"));
                                count_rp++;
                                rpks[i]=ts[rpeaks[i]];
                            }
                            S_DataAccess.startActionInsert(context,"rpeakss",rPeakses);

                            ArrayList<Measure_Data> measure_datas=new ArrayList<>();
                            Measure_Data md;
                            /// Writing the filtered signal
                            for (int i=0; i<filtered.length; i++){
                                time=begin.getTime()+Math.round(ts[i] * 1000);
                                md=new Measure_Data(count, "ECGL1", filtered[i], new Date(time), "socco");
                                measure_datas.add(md);
                                //Log.d("current", wid + "" + count);
                                count++;
                            }
                            WindowData.filtered_ecg.put(wid,measure_datas);
                            S_DataAccess.startActionInsert(context,"filteredecg",wid); //TODO: check SQL Unique constraint here

                            time=(begin.getTime()+end.getTime())/2;
                            hrate=0;
                            Log.d("time", "" + time);
                            for (int i=0; i<heart_rate.length; i++){
                                heartRate=new HeartRate(count_hr,heart_rate[i],new Date(begin.getTime()+Math.round(heart_rate_ts[i]*1000)),"socco");
                                S_DataAccess.startActionInsert(context, "heartrate", heartRate);
                                hrate+=heart_rate[i];
                                count_hr++;
                            }
                            Intent i = new Intent("ECG_RECEIVED");
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("hr",new HeartRate(wid,hrate/heart_rate.length,new Date(time),"socco"));
                            bundle.putSerializable("rpeaks",rpks);
                            i.putExtra("phy",bundle);
                            context.sendBroadcast(i);

                            //S_DecisionSupport.startActionCheckHR(context, new HeartRate(count_hr,hrate/heart_rate.length,new Date(time),"socco"));

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
                params.put("wid",String.valueOf(count));
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
