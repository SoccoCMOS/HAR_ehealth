package dz.esi.pfe.pfe_app.UI;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by DUALCOMPUTER on 6/5/2017.
 */

public class TimeAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axisBase) {
        Long time=Long.valueOf(Math.round(value));
        Date date=new Date(time);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss.SS");
        return simpleDateFormat.format(date);
    }
}
