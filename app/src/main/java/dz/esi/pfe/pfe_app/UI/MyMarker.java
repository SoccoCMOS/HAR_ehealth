package dz.esi.pfe.pfe_app.UI;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import dz.esi.pfe.pfe_app.R;

/**
 * Created by DUALCOMPUTER on 6/15/2017.
 */

public class MyMarker extends MarkerView {
    private TextView tvContent;

    public MyMarker (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("(" + e.getX()+","+e.getY()+")"); // set the entry-value as the display text
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2),-getHeight());
    }

}
