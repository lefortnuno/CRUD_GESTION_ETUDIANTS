package com.example.gestion_indemnite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_indemnite.personnel.personnelModel;
import com.example.gestion_indemnite.service.serviceAdapter;
import com.example.gestion_indemnite.service.serviceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPersonnel extends AppCompatActivity {
    Spinner spinnerService;
    Button ajout, edit;
    EditText nomPerso, adressePerso;
    TextView serId;
    serviceAdapter serviceAdapter;
    private ArrayList<serviceModel> arrayListService;
    private ArrayList<String> arrayListSer;

    private ArrayList<personnelModel> arrayList;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_personnel);
        init();
    }

    private void init(){
        spinnerService= findViewById(R.id.spinnerService);
        ajout= findViewById(R.id.ajoutPerso);
        serId= findViewById(R.id.serId);
        nomPerso= findViewById(R.id.nomPerso);
        adressePerso= findViewById(R.id.adressePerso);
        edit= findViewById(R.id.editPerso);

        getService();
        ajout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                postData();
            }
        });

        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getIdService();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void postData(){
        StringRequest request= new StringRequest(Request.Method.POST, routes.URL_personnel, response->{
            try{
                JSONObject object= new JSONObject(response);
                Toast.makeText(AddPersonnel.this, "Service ajouter avec succès", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(),PersonnelActivity.class);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }, error ->{
            error.printStackTrace();
        }){
            //add parameter
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map= new HashMap<>();
                map.put("nom", nomPerso.getText().toString());
                map.put("adresse", adressePerso.getText().toString());
                map.put("service", serId.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private  void  getService() {
        arrayListService= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_service, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length() > 0) {

                        ArrayList<String> ara=new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ite=  String.valueOf(jsonObject1.getInt("id_service"));
                            String ites=  String.valueOf(jsonObject1.getString("libelle"));

                            ara.add(ites);
                        }

                        //   Log.e("item", String.valueOf(ara));
                        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ara);
                        spinnerService.setAdapter(adapter);

                    }
                } catch (Exception e) {
                    Log.e("erroe", String.valueOf(e));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Res:", String.valueOf(error));
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getIdService(){
        String lib= spinnerService.getSelectedItem().toString();
        arrayListService= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_service+"/"+lib, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("sele",lib);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length() > 0) {

                        ArrayList<String> ara=new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ite=  String.valueOf(jsonObject1.getInt("id_service"));
                            serId.setText(ite);
                        }

                    }
                } catch (Exception e) {
                    Log.e("erroe", String.valueOf(e));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Res:", String.valueOf(error));
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}