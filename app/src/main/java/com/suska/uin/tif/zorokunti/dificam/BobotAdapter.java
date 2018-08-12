package com.suska.uin.tif.zorokunti.dificam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angsakumisan on 28/07/18.
 */

public class BobotAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public BobotAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(bobot object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        BobotHolder bobotHolder;

        if (row==null){

            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);

            bobotHolder = new BobotHolder();

            bobotHolder.v0 = (TextView)row.findViewById(R.id.v0);
            bobotHolder.v1 = (TextView)row.findViewById(R.id.v1);
            bobotHolder.v2 = (TextView)row.findViewById(R.id.v2);

            row.setTag(bobotHolder);


        }
        else {
            bobotHolder = (BobotHolder) row.getTag();

        }

        bobot bbt = (bobot)this.getItem(position);
        bobotHolder.v0.setText(bbt.getV0());
        bobotHolder.v1.setText(bbt.getV1());
        bobotHolder.v2.setText(bbt.getV2());

        return row;
    }

    static class BobotHolder
    {
        TextView v0,v1,v2;
    }

}
