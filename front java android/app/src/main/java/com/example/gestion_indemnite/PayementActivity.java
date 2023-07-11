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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestion_indemnite.payement.payementAdapter;
import com.example.gestion_indemnite.payement.payementModel;
import com.example.gestion_indemnite.personnel.personnelAdapter;
import com.example.gestion_indemnite.personnel.personnelModel;
import com.example.gestion_indemnite.service.serviceAdapter;
import com.example.gestion_indemnite.service.serviceModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PayementActivity extends AppCompatActivity {
    payementAdapter payementAdapter;
    private ArrayList<payementModel> arrayList;
    private ListView refreshLayout;
    ArrayAdapter arrayAdapter;
    EditText search;
    Button home1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);
        init();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        home1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });
    }
    private void init(){
        refreshLayout= findViewById(R.id.payementList);
        search= findViewById(R.id.Spaye);
        home1= findViewById(R.id.home1);
        getPayement();
        Onclick();
        registerForContextMenu(refreshLayout);

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
                payementModel aa= arrayList.get(position);
                int idea= aa.getId_payement();
                Log.e("eaea", String.valueOf(idea));
                String index =  String.valueOf(idea);

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_payement+"/"+index, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String id_payement=  jsonObject1.getString("numEtud");
                                    String nom=  jsonObject1.getString("nom");
                                    String description=  jsonObject1.getString("note_math");
                                    String payement=  jsonObject1.getString("note_phys");
                                    String endamnite=  jsonObject1.getString("moyenne");


                                    Intent intent= new Intent(getApplicationContext(),AddPayement.class);


                                    intent.putExtra("id_payement", id_payement);
                                    intent.putExtra("nom", nom);
                                    intent.putExtra("description", description);
                                    intent.putExtra("payement", payement);
                                    intent.putExtra("endamnite", endamnite);

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
                RequestQueue requestQueue= Volley.newRequestQueue(PayementActivity.this);
                requestQueue.add(stringRequest);

                break;
            }
            case R.id.btnSupprimer: {

                Toast.makeText(PayementActivity.this, " On ne peut pas effacer!", Toast.LENGTH_LONG).show();
                break;
            }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }


    private  void  getPayement() {
        arrayList= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_payement, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                List<payementModel> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            payementModel mode= new payementModel();
                            mode.setId_payement(jsonObject1.getInt("numEtud"));
                            mode.setNom(jsonObject1.getString("nom"));
                            mode.setDescription(jsonObject1.getString("note_math"));
                            mode.setPayement(jsonObject1.getString("moyenne"));
                            mode.setDate(jsonObject1.getString("note_phys"));

                            arrayList.add(mode);
                        }
                        payementAdapter = new payementAdapter(getApplicationContext(),arrayList);
                        refreshLayout.setAdapter(payementAdapter);

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


                payementModel aa= arrayList.get(position);
                int id= aa.getId_payement();
                Log.e("eaea", String.valueOf(id));

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_payement+"/"+id, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String numEtud=  jsonObject1.getString("numEtud");
                                    String nom=  jsonObject1.getString("nom");
                                    String note_math=  jsonObject1.getString("note_math");
                                    String note_phys=  jsonObject1.getString("note_phys");
                                    String moyenne=  jsonObject1.getString("moyenne");


                                    Intent intent= new Intent(getApplicationContext(),AddPayement.class);

                                    intent.putExtra("numEtud", numEtud);
                                    intent.putExtra("nom", nom);
                                    intent.putExtra("note_math", note_math);
                                    intent.putExtra("note_phys", note_phys);
                                    intent.putExtra("moyenne", moyenne);

                                    startActivity(intent);
                                    arrayAdapter.notifyDataSetChanged();
                                    refreshLayout.invalidateViews();

                                }
                            }

                        }catch (Exception e){
                            Log.e("erroe", String.valueOf(e));
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });
                RequestQueue requestQueue= Volley.newRequestQueue(PayementActivity.this);
                requestQueue.add(stringRequest);

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
                    getPayement();
                }else{
                    search();
            }
            }
        });
    }


    private void search(){
        Editable nom= search.getText();
        arrayList= new ArrayList<>();

        StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_payement+"/recherche/"+nom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject= new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("data");

                    if(jsonArray.length()>0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            payementModel mode= new payementModel();
                            mode.setId_payement(jsonObject1.getInt("numEtud"));
                            mode.setNom(jsonObject1.getString("nom"));
                            mode.setDescription(jsonObject1.getString("note_math"));
                            mode.setPayement(jsonObject1.getString("note_phys"));
                            mode.setDate(jsonObject1.getString("moyenne"));

                            arrayList.add(mode);
                        }
                        payementAdapter = new payementAdapter(getApplicationContext(),arrayList);
                        refreshLayout.setAdapter(payementAdapter);

                    }

                }catch (Exception e){
                    Log.e("erroe", String.valueOf(e));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(PayementActivity.this);
        requestQueue.add(stringRequest);

    }


}