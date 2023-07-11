package com.example.gestion_indemnite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.LogPrinter;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


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
import java.util.List;
import java.util.Map;

public class ServiceActivity extends AppCompatActivity {

    serviceAdapter serviceAdapter;
    private ArrayList<serviceModel> arrayList;
    private ListView refreshLayout;
    Button addService, search, home1;
    EditText bar;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        init();
//        getService();
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
       refreshLayout= findViewById(R.id.serviceList);
       addService= findViewById(R.id.add_service);
       bar= findViewById(R.id.bar);
       home1= findViewById(R.id.home1);

       Onclick();
       registerForContextMenu(refreshLayout);

       addService.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(), AddService.class);
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
                serviceModel aa= arrayList.get(position);
                int idea= aa.getId_service();
                Log.e("eaea", String.valueOf(idea));
                String index =  String.valueOf(idea);

                StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_service+"/"+index, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("data");

                            if(jsonArray.length()>0){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String idS=  jsonObject1.getString("id_service");
                                    String libelle=  jsonObject1.getString("libelle");
                                    String salaire=  jsonObject1.getString("salaire_heure");
                                    String jour=  jsonObject1.getString("nb_jour");


                                    Toast.makeText(ServiceActivity.this, libelle, Toast.LENGTH_SHORT).show();

                                    Intent intent= new Intent(getApplicationContext(),AddServiceActivity.class);

                                    intent.putExtra("idS", idS);
                                    intent.putExtra("libelle", libelle);
                                    Log.e("urls", libelle);
                                    intent.putExtra("salaire", salaire);
                                    intent.putExtra("jour", jour);

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
                RequestQueue requestQueue= Volley.newRequestQueue(ServiceActivity.this);
                requestQueue.add(stringRequest);


                break;
            }
            case R.id.btnSupprimer: {
                serviceModel aa= arrayList.get(position);
                int idea= aa.getId_service();
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
                        Toast.makeText(ServiceActivity.this, " Effacé avec succès", Toast.LENGTH_LONG).show();
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

//    private  void  getService() {
//        arrayList= new ArrayList<>();
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_service+ "/mClasse/", new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//
//                List<serviceModel> list = new ArrayList<>();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
//                    if (jsonArray.length() > 0) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                             String id = jsonObject1.getString("mClasse");
//                            serviceModel mode= new serviceModel();
//                            mode.setId_service(jsonObject1.getInt("mClasse"));
//                            mode.setLibelle(jsonObject1.getString("mMin"));
//                            mode.setSalaire_heure(jsonObject1.getInt("mMax"));
//                            mode.setNb_jour(jsonObject1.getInt("admis"));
//                            mode.setNb_Redoubl(jsonObject1.getInt("redoublant"));
//
//                            arrayList.add(mode);
//                        }
//                        serviceAdapter = new serviceAdapter(getApplicationContext(),arrayList);
//                        refreshLayout.setAdapter(serviceAdapter);
//
//                    }
//
//
//                } catch (Exception e) {
//                    Log.e("erroe", String.valueOf(e));
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.e("Res:", String.valueOf(error));
//            }
//
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

private void  Onclick(){

        bar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);

                Editable nom= bar.getText();
                arrayList= new ArrayList<>();
                if(nom.length()<0){
//                    getService();
                }else{
                    search();}
            }
        });



    refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            serviceModel aa= arrayList.get(position);
            int id= aa.getId_service();

              StringRequest stringRequest= new StringRequest(Request.Method.GET, routes.URL_service+"/"+id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject= new JSONObject(response);
                        JSONArray jsonArray= jsonObject.getJSONArray("data");

                        if(jsonArray.length()>0){
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String idS=  jsonObject1.getString("id_service");
                                String libelle=  jsonObject1.getString("libelle");
                                String salaire=  jsonObject1.getString("salaire_heure");
                                String jour=  jsonObject1.getString("nb_jour");


                                Toast.makeText(ServiceActivity.this, libelle, Toast.LENGTH_SHORT).show();

                                Intent intent= new Intent(getApplicationContext(),AddServiceActivity.class);

                                intent.putExtra("idS", idS);
                                intent.putExtra("libelle", libelle);
                                Log.e("urls", libelle);
                                intent.putExtra("salaire", salaire);
                                intent.putExtra("jour", jour);

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
            RequestQueue requestQueue= Volley.newRequestQueue(ServiceActivity.this);
            requestQueue.add(stringRequest);

        }
    });

}

private void search(){
        Editable nom= bar.getText();
    arrayList= new ArrayList<>();

       StringRequest stringRequest = new StringRequest(Request.Method.GET, routes.URL_service+"/mClasse/"+nom, new Response.Listener<String>() {


           @Override
           public void onResponse(String response) {

               List<serviceModel> list = new ArrayList<>();
               try {
                   JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                   if (jsonArray.length() > 0) {
                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                           String id = jsonObject1.getString("mClasse");
                           serviceModel mode= new serviceModel();
                           mode.setId_service(jsonObject1.getInt("mClasse"));
                           mode.setLibelle(jsonObject1.getString("mMin"));
                           mode.setSalaire_heure(jsonObject1.getInt("mMax"));
                           mode.setNb_jour(jsonObject1.getInt("admis"));
                           mode.setNb_Redoubl(jsonObject1.getInt("redoublant"));

                           arrayList.add(mode);
                       }
                       serviceAdapter = new serviceAdapter(getApplicationContext(),arrayList);
                       refreshLayout.setAdapter(serviceAdapter);

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

    private void deleteData(String index){
        StringRequest request= new StringRequest(Request.Method.DELETE, routes.URL_service+"/"+index, response->{

            try{
                JSONObject object= new JSONObject(response);

                Toast.makeText(ServiceActivity.this, "Deleted Successully", Toast.LENGTH_LONG).show();
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
                map.put("id_service", index.trim());
                //  map.put("profession", profession.getText().toString());
                return map;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}







































































