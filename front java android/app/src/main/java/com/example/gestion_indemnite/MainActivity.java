package com.example.gestion_indemnite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email, password;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        login= findViewById(R.id.login);

        email=  findViewById(R.id.email);
        password=  findViewById(R.id.Password);
        error=  findViewById(R.id.error);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                error.setVisibility(View.GONE);
                String txtEmail = email.getText().toString();
                String txtpass= password.getText().toString();

                String text= "root";
                String pwd= "root";

                if(text.equals(txtEmail) && pwd.equals(txtpass)){
                    email.setText("");
                    password.setText("");
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getApplicationContext(),HomeActivity.class);
                    i.putExtra("email", txtEmail);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Non Correct", Toast.LENGTH_LONG).show();
                    error.setText("Email ou PWD incorrect");
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}