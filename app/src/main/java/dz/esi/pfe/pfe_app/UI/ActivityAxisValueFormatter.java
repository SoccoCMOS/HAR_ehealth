package dz.esi.pfe.pfe_app.UI;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by DUALCOMPUTER on 6/16/2017.
 */

public class ActivityAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axisBase) {
        switch(Math.round(value)){
            case 1:
                return "Repos";
            case 2:
                return "Faible";
            case 3:
                return "Modéré";
            case 4:
                return "Intense";
        }
        return "Repos";
    }
}
