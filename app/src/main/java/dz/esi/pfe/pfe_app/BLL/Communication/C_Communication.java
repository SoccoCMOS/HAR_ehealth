package dz.esi.pfe.pfe_app.BLL.Communication;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import dz.esi.pfe.pfe_app.DAL.Model.Measure_Data;

/**
 * Created by DUALCOMPUTER on 3/28/2017.
 */
public class C_Communication {
    private static C_Communication ourInstance = new C_Communication();
    private List<Measure_Data> buffer;
    private int bufferSize;
    // TODO: Add an attribute of client connexion to the broker

    public static C_Communication getInstance() {
        return ourInstance;
    }

    private C_Communication() {
        buffer=new ArrayList<Measure_Data>();
        bufferSize=0;
    }

    public List<Measure_Data> getBuffer() {
        return buffer;
    }

    public void addElement(Measure_Data md)
    {
        buffer.add(md);
    }

    public void removeElement(int index)
    {
        buffer.remove(index);
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void startStreaming()
    {

    }

    public void stopStreaming()
    {

    }


}
