package com.example.gestion_indemnite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_indemnite.personnel.personnelAdapter;
import com.example.gestion_indemnite.personnel.personnelModel;
import com.example.gestion_indemnite.service.serviceAdapter;
import com.example.gestion_indemnite.service.serviceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonnelActivity extends AppCompatActivity {

    personnelAdapter personnelAdapter;
    private ArrayList<personnelModel> arrayList;

    private ListView refreshLayout;
    Button addPersonnel,home1;
    EditText search;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel);
        init();
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
                personnelModel aa= arrayList.get(position);
                int idea= aa.getId_perso();
                Log.e("eaea", String.valueOf(idea));
                String index =  String.valueOf(idea);

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_personnel+"/"+index, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id_service=  jsonObject1.getString("id_service");
                                    String service=  jsonObject1.getString("service");
                                    String id_perso=  jsonObject1.getString("id_perso");
                                    String nom=  jsonObject1.getString("nom");
                                    String adresse=  jsonObject1.getString("adresse");

                                    Toast.makeText(PersonnelActivity.this, nom, Toast.LENGTH_SHORT).show();

                                    Intent intent= new Intent(getApplicationContext(),AddPersonnelActivity.class);

                                    intent.putExtra("id_service", id_service);
                                    intent.putExtra("service", service);
                                    intent.putExtra("id_perso", id_perso);
                                    intent.putExtra("nom", nom);
                                    intent.putExtra("adresse", adresse);

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
                RequestQueue requestQueue= Volley.newRequestQueue(PersonnelActivity.this);
                requestQueue.add(stringRequest);

                break;
            }
            case R.id.btnSupprimer: {
                personnelModel aa= arrayList.get(position);
                int idea= aa.getId_perso();
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
                        Toast.makeText(PersonnelActivity.this, " Effacé avec succès", Toast.LENGTH_LONG).show();
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


    private void init(){
        refreshLayout= findViewById(R.id.personnelList);
        addPersonnel= findViewById(R.id.add_personnel);
        search= findViewById(R.id.searchP);
        home1= findViewById(R.id.home1);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getPersonnel();
        Onclick();
        registerForContextMenu(refreshLayout);
        addPersonnel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddPersonnel.class);
                startActivity(i);
            }
        });

        home1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);

                Editable nom= search.getText();
                arrayList= new ArrayList<>();
                if(nom.length()<0){
                    getPersonnel();
                }else{
                    search();}
            }
        });


    }

    private  void  getPersonnel() {
        arrayList= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_personnel+"/mClasse/", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("ffff", response);
                List<personnelModel> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            personnelModel mode= new personnelModel();
                            mode.setId_perso(jsonObject1.getInt("mClasse"));
                            mode.setNom(jsonObject1.getString("mMin"));
                            mode.setAdresse(jsonObject1.getString("mMax"));
                            mode.setService(jsonObject1.getString("admis"));
                            mode.setService(jsonObject1.getString("redoublant"));

                            arrayList.add(mode);
                        }
                  personnelAdapter = new personnelAdapter(getApplicationContext(),arrayList);
                        refreshLayout.setAdapter(personnelAdapter);

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

    private void  Onclick(){

        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                personnelModel aa= arrayList.get(position);
                int id= aa.getId_perso();
                Log.e("eaea", String.valueOf(id));

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_personnel+"/"+id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id_service=  jsonObject1.getString("id_service");
                                    String service=  jsonObject1.getString("service");
                                    String id_perso=  jsonObject1.getString("id_perso");
                                    String nom=  jsonObject1.getString("nom");
                                    String adresse=  jsonObject1.getString("adresse");

                                    Toast.makeText(PersonnelActivity.this, nom, Toast.LENGTH_SHORT).show();

                                    Intent intent= new Intent(getApplicationContext(),AddPersonnelActivity.class);

                                    intent.putExtra("id_service", id_service);
                                    intent.putExtra("service", service);
                                    intent.putExtra("id_perso", id_perso);
                                    intent.putExtra("nom", nom);
                                    intent.putExtra("adresse", adresse);

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
                RequestQueue requestQueue= Volley.newRequestQueue(PersonnelActivity.this);
                requestQueue.add(stringRequest);

            }
        });

    }

    private void search(){
        Editable nom= search.getText();
        arrayList= new ArrayList<>();
        if(nom.length()<0){
            getPersonnel();
        }else{
            arrayList= new ArrayList<>();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_personnel+"/"+nom, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    List<personnelModel> list = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                personnelModel mode= new personnelModel();
                                mode.setId_perso(jsonObject1.getInt("id_perso"));
                                mode.setNom(jsonObject1.getString("nom"));
                                mode.setAdresse(jsonObject1.getString("adresse"));
                                mode.setService(jsonObject1.getString("service"));

                                arrayList.add(mode);
                            }
                            personnelAdapter = new personnelAdapter(getApplicationContext(),arrayList);
                            refreshLayout.setAdapter(personnelAdapter);

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

    private void deleteData(String index){

        StringRequest request= new StringRequest(Request.Method.DELETE, routes.URL_personnel+"/"+index, response->{

            try{
                JSONObject object= new JSONObject(response);

                Toast.makeText(PersonnelActivity.this, "Deleted Successully", Toast.LENGTH_LONG).show();
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
                map.put("id_perso", index );
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }




}