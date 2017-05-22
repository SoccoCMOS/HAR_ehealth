package dz.esi.pfe.pfe_app.BLL.DataProcessing.Utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */
public class U_Matrix {
    
    public static ArrayList<Double> multiply(Double[][] a, Double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        ArrayList<Double> c = new ArrayList<>();
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++) {
                Double temp=0.0;
                for (int k = 0; k < n1; k++) {
                    temp+=a[i][k] * b[k][j];
                }
                c.add(temp);
            }

        return c;
    }

    public static Double[][] transpose(Double[][] a) {
        int m = a.length;
        int n = a[0].length;
        Double[][] b = new Double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }

    public static Double[][] norm(Double[][] a){
        Double norm=0.0;
        for (int i=0; i<a.length; i++)
        {
            norm+=a[i][0]*a[i][0];
        }
        norm=Math.sqrt(norm); //Norme euclidéenne du vecteur colonne 0
        for (int i=0; i<a.length; i++)
        {
            if (norm!=0) a[i][0]=a[i][0]/norm;
        }
        return a;
    }

}
