package com.example.gestion_indemnite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import com.example.gestion_indemnite.personnel.personnelAdapter;
import com.example.gestion_indemnite.personnel.personnelModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndemniteActivity extends AppCompatActivity {

    indemniteAdapter indemniteAdapter;
    private ArrayList<indemniteModel> arrayList;
    private ListView refreshLayout;
    Button addEndamnite, home1;
    TextView net;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indemnite);
        init();

        home1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });
    }

    private void init(){
        refreshLayout= findViewById(R.id.listIndemnite);
        addEndamnite= findViewById(R.id.add);
        net= findViewById(R.id.net);
        home1= findViewById(R.id.home1);
       getIndemnite();
        Onclick();
        registerForContextMenu(refreshLayout);
        getTotal();
        addEndamnite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddIndemnite.class);
                startActivity(i);
            }
        });

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.petit_menu, menu);

        super.onCreateContextMenu(menu,v,menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        switch (id){
            case R.id.btnModifier: {
                indemniteModel aa= arrayList.get(position);
                int idea= aa.getId_endamnite();
                Log.e("eaea", String.valueOf(idea));
                String index =  String.valueOf(idea);

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_indemnite+"/"+index, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id_endamnite=  jsonObject1.getString("id_endamnite");
                                    String id_perso=  jsonObject1.getString("id_perso");
                                    String heure_travail=  jsonObject1.getString("heure_travail");


                                    Intent intent= new Intent(getApplicationContext(),AddIndemniteActivity.class);

                                    intent.putExtra("id_endamnite", id_endamnite);
                                    intent.putExtra("heure_travail", heure_travail);
                                    intent.putExtra("id_perso", id_perso);

                                    startActivity(intent);
                                    arrayAdapter.notifyDataSetChanged();
                                    refreshLayout.invalidateViews();

                                }
                            }

                        }catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });
                RequestQueue requestQueue= Volley.newRequestQueue(IndemniteActivity.this);
                requestQueue.add(stringRequest);


                break;
            }
            case R.id.btnSupprimer: {
                indemniteModel aa= arrayList.get(position);
                int idea= aa.getId_endamnite();
                Log.e("eaea", String.valueOf(idea));
                String index =  String.valueOf(idea);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Supprimer");
                builder.setMessage("Souhaitez-vous vraiment supprimer?");
                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //instruction pour NON
                        dialogInterface.dismiss();
                    }
                });

                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        deleteData( index );
                        Toast.makeText(IndemniteActivity.this, " Effacé avec succès", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                break;
            }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private  void  getIndemnite() {
        arrayList= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_indemnite+"/mClasse/", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("indemni",response);
                List<indemniteModel> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            indemniteModel mode= new indemniteModel();
                            mode.setId_endamnite(jsonObject1.getInt("mClasse"));
                            mode.setNom(jsonObject1.getString("mMin"));
                            mode.setService(jsonObject1.getString("mMax"));
                            mode.setHeure_travail(jsonObject1.getInt("admis"));
                            mode.setSalaire_heure(jsonObject1.getInt("redoublant"));
                            mode.setMontant(jsonObject1.getInt("nbEtud"));

                            arrayList.add(mode);
                        }
                        indemniteAdapter = new indemniteAdapter(getApplicationContext(),arrayList);
                        refreshLayout.setAdapter(indemniteAdapter);

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

    private void getTotal(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_indemnite+"/total", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
              //  Log.e("ressss", response);
                List<indemniteModel> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    net.setText(jsonObject.getString("montant"));


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

    private void  Onclick(){

        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                indemniteModel aa= arrayList.get(position);
                int id= aa.getId_endamnite();
             //   Log.e("eaea", String.valueOf(id));

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_indemnite+"/"+id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id_endamnite=  jsonObject1.getString("id_endamnite");
                                    String id_perso=  jsonObject1.getString("id_perso");
                                    String heure_travail=  jsonObject1.getString("heure_travail");


                                    Intent intent= new Intent(getApplicationContext(),AddIndemniteActivity.class);

                                    intent.putExtra("id_endamnite", id_endamnite);
                                    intent.putExtra("heure_travail", heure_travail);
                                    intent.putExtra("id_perso", id_perso);

                                    startActivity(intent);
                                    arrayAdapter.notifyDataSetChanged();
                                    refreshLayout.invalidateViews();

                                }
                            }

                        }catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });
                RequestQueue requestQueue= Volley.newRequestQueue(IndemniteActivity.this);
                requestQueue.add(stringRequest);

            }
        });

    }

    private void deleteData(String index){

        StringRequest request= new StringRequest(Request.Method.DELETE, routes.URL_indemnite+"/"+index, response->{

            try{
                JSONObject object= new JSONObject(response);

                Toast.makeText(IndemniteActivity.this, "Deleted Successully", Toast.LENGTH_LONG).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map= new HashMap<>();
                map.put("id_endamnite", index.trim());
                //  map.put("profession", profession.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


}