package com.example.gestion_indemnite.indemnite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.gestion_indemnite.R;

import java.util.ArrayList;

public class indemniteAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<indemniteModel> listData;

    public indemniteAdapter(Context aContext, ArrayList<indemniteModel> listData) {
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
        indemniteAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_indemnite, null);
            holder = new indemniteAdapter.ViewHolder();
            holder.endamniteView = (TextView) convertView.findViewById(R.id.id_endamnite);
            holder.nomView = (TextView) convertView.findViewById(R.id.nomE);
            holder.serviceView = (TextView) convertView.findViewById(R.id.serviceE);
            holder.heureView = (TextView) convertView.findViewById(R.id.heureE);
            holder.salaireView = (TextView) convertView.findViewById(R.id.salaireE);
            holder.montantView = (TextView) convertView.findViewById(R.id.montant);

            convertView.setTag(holder);
        } else {
            holder = (indemniteAdapter.ViewHolder) convertView.getTag();
        }

        indemniteModel modele = this.listData.get(position);
        holder.endamniteView.setText(String.valueOf( modele.getId_endamnite()));
        holder.nomView.setText( modele.getNom());
        holder.heureView.setText(String.valueOf(modele.getHeure_travail()));
        holder.serviceView.setText(String.valueOf( modele.getService()));
        holder.salaireView.setText( String.valueOf(modele.getSalaire_heure()));
        holder.montantView.setText( String.valueOf(modele.getMontant()));

        return convertView;
    }
    static class ViewHolder {
        TextView endamniteView;
        TextView nomView;
        TextView heureView;
        TextView serviceView;
        TextView salaireView;
        TextView montantView;
    }
}
