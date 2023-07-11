package com.example.gestion_indemnite.payement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.gestion_indemnite.R;

import java.util.ArrayList;

public class payementAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<payementModel> listData;

    public payementAdapter(Context aContext, ArrayList<payementModel> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        payementAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_payement, null);
            holder = new payementAdapter.ViewHolder();
            holder.payeIdView = (TextView) convertView.findViewById(R.id.idPayement);
            holder.nomView = (TextView) convertView.findViewById(R.id.nomP);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.desc);
            holder.payementView = (TextView) convertView.findViewById(R.id.montP);
            holder.dateView = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (payementAdapter.ViewHolder) convertView.getTag();
        }

        payementModel modele = this.listData.get(position);
        holder.payeIdView.setText(String.valueOf( modele.getId_payement()));
        holder.nomView.setText( modele.getNom());
        holder.descriptionView.setText(modele.getDescription());
        holder.payementView.setText(String.valueOf( modele.getPayement()));
        holder.dateView.setText( modele.getDate());

        return convertView;
    }
    static class ViewHolder {
        TextView payeIdView;
        TextView nomView;
        TextView descriptionView;
        TextView payementView;
        TextView dateView;
    }
}
