package com.example.gestion_indemnite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_indemnite.service.serviceAdapter;
import com.example.gestion_indemnite.service.serviceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity  {

    Button personnel, indemnite, service, payement;
    MenuItem deco;
   com.example.gestion_indemnite.service.serviceAdapter serviceAdapter;
    private ArrayList<serviceModel> arrayList;
    private ListView refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        deco = findViewById(R.id.deco);
        init();
        Onclick();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.deco: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation");
                builder.setMessage("Souhaitez-vous vraiment vous deconnecter?");
                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //instruction pour NON
                        dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                HomeActivity.this,
                                MainActivity.class
                        );
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                break;
            }
            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }
    public void init(){
        personnel=findViewById(R.id.personnel);
        indemnite=findViewById(R.id.indemnite);
        service=findViewById(R.id.service);
        payement=findViewById(R.id.payement);

    }
    public  void Onclick() {
        personnel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PersonnelActivity.class);
                startActivity(i);
            }
        });
        indemnite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), IndemniteActivity.class);
                startActivity(i);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ServiceActivity.class);
                startActivity(i);
            }
        });
        payement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PayementActivity.class);
                startActivity(i);
            }
        });
    }


}
