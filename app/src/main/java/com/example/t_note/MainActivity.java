package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText usuari, contrasenya;
    Button confirmar, forgot,registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enregistrem els components
        usuari = this.findViewById(R.id.editM_Usuari);
        contrasenya = this.findViewById(R.id.editM_Contrasenya);
        confirmar = this.findViewById(R.id.botonM_Confirmar);
        forgot = this.findViewById(R.id.botonM_ForgotPassword);
        registrar = this.findViewById(R.id.botonM_Registrarse);


        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPantallaActivity();
            }
        });
        /*
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */
    }

    public void gotToRegistrar(){
        Intent intent = new Intent(this,RegistrarActivity.class);
        startActivity(intent);
    }

    public void goToForgotPassword(){
        //Intent intent = new Intent(this,.class);
        //startActivity(intent);
    }

    public void goToLogin(){
        //Intent intent = new Intent(this,.class);
        //startActivity(intent);
    }
    public void gotoPantallaActivity(){
        Intent intent = new Intent( this, PantallaActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        if(R.id.botonM_Confirmar == v.getId()){
            //Iniciar pantalla main

        }if(R.id.botonM_ForgotPassword == v.getId()){
            //Iniciar pantalla de perdua de contrasenya

        }if(R.id.botonM_Registrarse == v.getId()){
            //Iniciar pantalla de registrar
            gotToRegistrar();
        }
    }
}