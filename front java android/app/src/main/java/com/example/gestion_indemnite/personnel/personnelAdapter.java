package com.example.gestion_indemnite.personnel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gestion_indemnite.R;
import com.example.gestion_indemnite.service.serviceAdapter;
import com.example.gestion_indemnite.service.serviceModel;

import java.util.ArrayList;

public class personnelAdapter  extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<personnelModel> listData;

    public personnelAdapter(Context aContext, ArrayList<personnelModel> listData) {
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
        personnelAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_personnel, null);
            holder = new personnelAdapter.ViewHolder();
            holder.persoIdView = (TextView) convertView.findViewById(R.id.id_perso);
            holder.nomView = (TextView) convertView.findViewById(R.id.nom);
            holder.adresseView = (TextView) convertView.findViewById(R.id.adresse);
            holder.serviceView = (TextView) convertView.findViewById(R.id.serviceP);
//            holder.nbRedoublView = (TextView) convertView.findViewById(R.id.nbRedoubl);

            convertView.setTag(holder);
        } else {
            holder = (personnelAdapter.ViewHolder) convertView.getTag();
        }

        personnelModel modele = this.listData.get(position);
        holder.persoIdView.setText(String.valueOf( modele.getId_perso()));
        holder.nomView.setText( modele.getNom());
        holder.adresseView.setText(modele.getAdresse());
        holder.serviceView.setText( modele.getService());
//        holder.nbRedoublView.setText( modele.getService());

        return convertView;
    }
    static class ViewHolder {
        TextView persoIdView;
        TextView nomView;
        TextView adresseView;
        TextView serviceView;
//        TextView nbRedoublView;
    }
}
