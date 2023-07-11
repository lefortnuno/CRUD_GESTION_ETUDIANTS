package com.example.gestion_indemnite.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gestion_indemnite.R;

import java.util.ArrayList;

public class serviceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<serviceModel> listData;

    public serviceAdapter(Context aContext, ArrayList<serviceModel> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_service, null);
            holder = new ViewHolder();
            holder.serviceIdView = (TextView) convertView.findViewById(R.id.idS);
            holder.libelleView = (TextView) convertView.findViewById(R.id.libelleS);
            holder.salaireView = (TextView) convertView.findViewById(R.id.salaireS);
            holder.jourView = (TextView) convertView.findViewById(R.id.jourS);
            holder.nbRedoublView = (TextView) convertView.findViewById(R.id.nbRedoubl);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        serviceModel modele = this.listData.get(position);
        holder.serviceIdView.setText(String.valueOf( modele.getId_service()));
        holder.libelleView.setText( modele.getLibelle());
        holder.salaireView.setText( String.valueOf(modele.getSalaire_heure()));
        holder.jourView.setText( String.valueOf(modele.getNb_jour()));
        holder.nbRedoublView.setText( String.valueOf(modele.getNb_Redoubl()));

        return convertView;
    }
    static class ViewHolder {
        TextView serviceIdView;
        TextView libelleView;
        TextView salaireView;
        TextView jourView;
        TextView nbRedoublView;
    }
}
