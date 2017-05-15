package dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities;


        import android.content.Context;
        import java.util.Arrays;
        import java.util.List;
        import java.io.InputStream;
        import java.util.ArrayList;

        import dz.esi.pfe.pfe_app.R;

public class U_FeatureExtraction {

    List<double[]> window;
    double[] mean,sd,rms,mad;
    double featureVector[][];
    int nm=24; //number of measures
    int ws=3000; //size of window
    Context context;

    public U_FeatureExtraction(Context context, List<double[]> window, int ws, int nm) {
        this.context = context;
        this.window=window;
        this.ws = ws;
        this.nm = nm;
        featureVector=new double[nm*4][1];
    }


    public double[][] applyFE() {
        mean=mean(window, nm);
        mad=mad(window, nm);
        rms=rms(window, nm);
        sd=sd(window, nm);
        return featureVector;
    }


    public double[] mean(List<double[]> window, int size_line) // 0
    {
        double[] result = new double[size_line];
        Arrays.fill(result, 0);
        for (int i = 0; i < size_line; i++) {
            for (int j = 0; j < window.size(); j++) {
                result[i] = result[i] + window.get(j)[i];
            }
            result[i] = result[i] / window.size();
            featureVector[i][0]=result[i];
        }
        return result;
    }

    public double[] mad(List<double[]> window, int size_line) // 1
    {
        double[] result = new double[size_line];
        Arrays.fill(result, 0);

        double[] result1 = new double[size_line];
        Arrays.fill(result1, 0);
        for (int i = 0; i < size_line; i++) {
            for (int j = 0; j < window.size(); j++) {
                result1[i] = result1[i] + Math.abs((window.get(j)[i] - mean[i]));
            }
            result[i] = Math.sqrt(result1[i] / (window.size() - 1));
            featureVector[nm+i][0]=result[i];
        }

        return result;
    }

    public double[] rms(List<double[]> window, int size_line)// 3
    {
        double[] result = new double[size_line];
        Arrays.fill(result, 0);

        for (int i = 0; i < size_line; i++) {
            for (int j = 0; j < window.size(); j++) {
                result[i] = result[i] + Math.pow(window.get(j)[i], 2);
            }
            result[i] = Math.sqrt(result[i] / window.size());
            featureVector[nm*2+i][0]=result[i];
        }
        return result;
    }

    public double[] sd(List<double[]> window, int size_line)// 2
    {
        double[] result = new double[size_line];
        Arrays.fill(result, 0);
        double[] result1 = new double[size_line];
        Arrays.fill(result1, 0);
        for (int i = 0; i < size_line; i++) {
            for (int j = 0; j < window.size(); j++) {
                result1[i] = result1[i] + Math.pow((window.get(j)[i] - mean[i]), 2);
            }
            result[i] = Math.sqrt(result1[i] / (window.size() - 1));
            featureVector[nm*3+i][0]=result[i];
        }
        return result;
    }
}

