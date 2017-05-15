package dz.esi.pfe.pfe_app.BLL.DataProcessing;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.U_Matrix;
import dz.esi.pfe.pfe_app.DAL.Model.Activity;

/**
 * Created by Sara on 5/12/2017.
 * This class handles the patterns service provider call for activity recognition
 */
public class ActivityRecognition {
    int count=0;
    double[][] featureVector;
    RequestQueue queue;
    String url="http://192.168.1.6:8000/activity/";
    String urlget="http://192.168.1.6:8000/activity/?param=no+param&tr=NP&p=1&wid="+count;
    Activity activity;
    double np[][];
    ArrayList<Double> npb;
    double b[][];
    int c[];
    String ja;
    Map<String, String> params;
    long[] latency=new long[2];

    public ActivityRecognition(double[][] featureVector, Context c) {
        this.featureVector = featureVector;
        queue = Volley.newRequestQueue(c);
    }

    public double[][] getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(double[][] featureVector) {
        this.featureVector = featureVector;
    }

    public Activity getActivityByFeatures()
    {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urlget,null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ja=response.getString("content");
                            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
                            np =    gsonPretty.fromJson(ja, double[][].class);
                            Log.d("np first and last",""+np[0][0]+"  "+np[125][83]);
                            b= U_Matrix.norm(featureVector);
                            Log.d("b first and last",""+b[0][0]+"  "+b[83][0]);
                            npb = U_Matrix.multiply(np, b);
                            Log.d("npb first and last",""+npb.get(0)+"  "+npb.get(125));
                            Log.d("npb dimensions : " , String.valueOf(npb.size()));

                            StringRequest jsr2 = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            try {
                                                JSONObject json=new JSONObject(response);
                                                String jar=json.getString("content");
                                                Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
                                                int[] classes= gsonPretty.fromJson(jar, int[].class);
                                                activity = new Activity(classes[0]);
                                                Log.d("activity",activity.getActivityLabel());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("Response", response);
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
                                    params.put("wid", String.valueOf(count));
                                    params.put("tr", "C");
                                    params.put("p",String.valueOf(1));
                                    params.put("param", String.valueOf(npb));
                                    Log.d("Params : ",String.valueOf(params));
                                    return params;
                                }

                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    latency[1]=response.networkTimeMs;
                                    Log.d("latency post",""+latency[1]);
                                    return super.parseNetworkResponse(response);
                                }
                            };
                            queue.add(jsr2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activity =new Activity(0);
                    }
                })
                {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                latency[0]=response.networkTimeMs;
                Log.d("latency get",""+latency[0]);
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsObjRequest);
        return activity;
    }
}
