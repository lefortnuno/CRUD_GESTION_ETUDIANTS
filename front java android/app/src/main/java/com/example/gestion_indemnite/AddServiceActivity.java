package com.example.gestion_indemnite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddServiceActivity extends AppCompatActivity {

    Button edit;
    EditText libelle, nb_jour, salaire_heure;
    TextView idService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        init();

    }

    private void init(){
        edit= findViewById(R.id.edit);
        idService= findViewById(R.id.idService);
        libelle= findViewById(R.id.libelle);
        nb_jour= findViewById(R.id.nb_jour);
        salaire_heure= findViewById(R.id.salaire);
        String t1=getIntent().getStringExtra("idS");
        String t2=getIntent().getStringExtra("libelle");
        String t3=getIntent().getStringExtra("salaire");
        String t4=getIntent().getStringExtra("jour");

        idService.setText(t1);
        libelle.setText(t2);

        salaire_heure.setText(t3);
        nb_jour.setText(t4);

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editData();
            }
        });

    }

    private void editData(){
        String index= idService.getText().toString();
        StringRequest request= new StringRequest(Request.Method.PUT, routes.URL_service+"/"+index, response->{
            //  Log.e("urllllllllll",routes.URL_service+"/"+index);
            try{
                JSONObject object= new JSONObject(response);
                Toast.makeText(AddServiceActivity.this, "Data editer avec succès", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(getApplicationContext(),ServiceActivity.class);
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
                map.put("salaire_heure", salaire_heure.getText().toString());
                map.put("id_service", idService.getText().toString());
                map.put("nb_jour", nb_jour.getText().toString());
                map.put("libelle", libelle.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}