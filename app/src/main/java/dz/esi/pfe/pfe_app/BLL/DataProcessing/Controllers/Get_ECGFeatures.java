package dz.esi.pfe.pfe_app.BLL.DataProcessing.Controllers;

/**
 * Created by Widad on 19-04-2017.
 */
import android.os.AsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities.HttpUtility;


public class Get_ECGFeatures extends AsyncTask<Double[],Void, Double []> {

    //private Context context;

   /* public Get_ECGfeaures(Context context) {
        this.context = context;
    }*/

    @Override
    protected Double[] doInBackground(Double[]... params) {
        StringBuilder result = new StringBuilder();
        String data;
        Map<String, Double[]> couple = new HashMap<String, Double[]>();

        String requestURL = "http://192.168.1.6:8000/ecg/";
        couple.put("param", params[0]);

        Double [] response =  new Double[]{};

        try {
            HttpUtility.sendPostRequest(requestURL, couple);
            response = HttpUtility.readSingleLineRespone();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        HttpUtility.disconnect();
        return  response;
    }


    @Override
    protected void onPostExecute(Double[] s) {
        // pd.dismiss();
        if(s.equals("1")) {
            // Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
        }
    }

}