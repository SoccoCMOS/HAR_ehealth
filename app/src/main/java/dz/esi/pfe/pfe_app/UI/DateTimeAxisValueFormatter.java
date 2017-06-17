package dz.esi.pfe.pfe_app.UI;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by DUALCOMPUTER on 6/16/2017.
 */

public class DateTimeAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int hours=(int)value/60;
        int min = Math.round(value-hours*60);

        return hours+":"+min;
    }
}
