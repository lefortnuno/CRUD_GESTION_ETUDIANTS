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
import com.example.gestion_indemnite.indemnite.indemniteAdapter;
import com.example.gestion_indemnite.indemnite.indemniteModel;
import com.example.gestion_indemnite.personnel.personnelModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddIndemniteActivity extends AppCompatActivity {
    indemniteAdapter indemniteAdapter;
    private ArrayList<indemniteModel> arrayListService;
    private ArrayList<String> arrayList;

    Spinner spinnerPerso;
    Button edit;
    EditText heure;
    TextView idPersoE, idEnd;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_indemnite);
        init();
    }
    private void init(){
        edit= findViewById(R.id.editE);
        spinnerPerso= findViewById(R.id.spinnerPerso);
        idPersoE= findViewById(R.id.idPersoE);
        heure= findViewById(R.id.hTravail);
        idEnd= findViewById(R.id.idEnd);
        getPersonnel();

        String t1=getIntent().getStringExtra("id_endamnite");
        String t2=getIntent().getStringExtra("heure_travail");
        String t3=getIntent().getStringExtra("id_perso");

        idEnd.setText(t1);
        idPersoE.setText(t3);
        heure.setText(t2);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editData();
            }
        });

        spinnerPerso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getIdPerso();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private  void  getPersonnel() {
        arrayListService= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_personnel, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length() > 0) {

                        ArrayList<String> ara=new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ites=  String.valueOf(jsonObject1.getString("nom"));

                            ara.add(ites);
                        }

                        //   Log.e("item", String.valueOf(ara));
                        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ara);
                        spinnerPerso.setAdapter(adapter);

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

    private void getIdPerso(){
        String lib= spinnerPerso.getSelectedItem().toString();
        arrayListService= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_personnel+"/"+lib, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
             //   Log.e("sele",lib);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length() > 0) {

                        ArrayList<String> ara=new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String ite=  String.valueOf(jsonObject1.getInt("id_perso"));
                            idPersoE.setText(ite);
                            Log.e("idPersoE", String.valueOf(idPersoE));
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

    private void editData(){
        String index= idEnd.getText().toString();
        StringRequest request= new StringRequest(Request.Method.PUT, routes.URL_indemnite+"/"+index, response->{
            Log.e("urllllllllll",routes.URL_indemnite+"/"+index);
            try{
                JSONObject object= new JSONObject(response);
                Toast.makeText(AddIndemniteActivity.this, "Data editer avec succès", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(),IndemniteActivity.class);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }, error ->{
            error.printStackTrace();
        }){
            //add parameter
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> map= new HashMap<>();
                map.put("personnel", idPersoE.getText().toString());
                map.put("heure_travail", heure.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}