package com.example.gestion_indemnite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddService extends AppCompatActivity {

    Button ajout;
    EditText libelle, nb_jour, salaire_heure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service);
        init();
    }
    private void init(){
        libelle= findViewById(R.id.libelle);
        nb_jour= findViewById(R.id.nb_jour);
        salaire_heure= findViewById(R.id.salaire);
        ajout= findViewById(R.id.ajout);

        ajout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                postData();
            }
        });
    }

    private void postData(){
        StringRequest request= new StringRequest(Request.Method.POST, routes.URL_indemnite+"/", response->{
            try{
                JSONObject object= new JSONObject(response);
                Toast.makeText(AddService.this, "Service ajouter avec succès", Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map= new HashMap<>();
                map.put("note_phys", salaire_heure.getText().toString());
                map.put("note_math", nb_jour.getText().toString());
                map.put("nom", libelle.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}