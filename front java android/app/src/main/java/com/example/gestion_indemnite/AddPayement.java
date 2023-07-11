package com.example.gestion_indemnite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_indemnite.indemnite.indemniteAdapter;
import com.example.gestion_indemnite.indemnite.indemniteModel;
import com.example.gestion_indemnite.payement.payementAdapter;
import com.example.gestion_indemnite.payement.payementModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddPayement extends AppCompatActivity {
    payementAdapter payementAdapter;
    private ArrayList<payementModel> arrayListService;
    private ArrayList<String> arrayList;

    Spinner spinnerDesc;
    Button edit, suppr;
    TextView idPaye, enda;
    ArrayAdapter arrayAdapter;
    EditText notePC, nom, montPaye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payement);
        init();

    }

    private void init(){

        spinnerDesc= findViewById(R.id.spinnerDesc);
        String[]items= new String[]{"Non Payé","Payé"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerDesc.setAdapter(adapter);
        edit= findViewById(R.id.editPaye);
        suppr= findViewById(R.id.supprimerButton);
        idPaye= findViewById(R.id.idPaye);
        nom= findViewById(R.id.nomPer);
        montPaye= findViewById(R.id.montPaye);
        notePC = findViewById(R.id.notePC);
        enda= findViewById(R.id.enda);

        String t1=getIntent().getStringExtra("numEtud");
        String t2=getIntent().getStringExtra("nom");
        String t3=getIntent().getStringExtra("note_math");
        String t4=getIntent().getStringExtra("note_phys");
        String t5=getIntent().getStringExtra("moyenne");

        idPaye.setText(t1);
        nom.setText(t2);
        montPaye.setText(t3);
        notePC.setText(t4);
        enda.setText(t5);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                editData();
            }
        });

        suppr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                suppData();
            }
        });

    }

    private void editData(){
        String index= idPaye.getText().toString();
        StringRequest request= new StringRequest(Request.Method.PUT, routes.URL_payement+"/"+index, response->{

            System.out.println(response);
            try{
                JSONObject object= new JSONObject(response);
                Toast.makeText(AddPayement.this, "Data editer avec succès", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(),PayementActivity.class);
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
                map.put("nom", nom.getText().toString());
                map.put("note_math", montPaye.getText().toString());
                map.put("note_phys", notePC.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void suppData(){
        String index= idPaye.getText().toString();
        StringRequest request= new StringRequest(Request.Method.DELETE, routes.URL_payement+"/"+index, response->{
            // Log.e("urllllllllll",routes.URL_indemnite+"/"+index);
            try{
                JSONObject object= new JSONObject(response);
                Toast.makeText(AddPayement.this, "Suppression reussi avec succès", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(),PayementActivity.class);
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
                map.put("description", spinnerDesc.getSelectedItem().toString());
                map.put("endamnite", enda.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}