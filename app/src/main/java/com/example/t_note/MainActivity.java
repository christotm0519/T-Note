package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity{

    TextInputLayout layoutUsuari, layoutContrasenya;
    TextInputEditText usuari, contrasenya;
    Button confirmar, forgot,registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enregistrem els components
        layoutUsuari = this.findViewById(R.id.layoutM_Usuari);
        layoutContrasenya = this.findViewById(R.id.layoutM_Contrasenya);
        usuari = this.findViewById(R.id.editM_Usuari);
        contrasenya = this.findViewById(R.id.editM_Contrasenya);
        confirmar = this.findViewById(R.id.botonM_Confirmar);
        forgot = this.findViewById(R.id.botonM_ForgotPassword);
        registrar = this.findViewById(R.id.botonM_Registrarse);


        confirmar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                gotoPantallaActivity();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla de perdua de contrasenya
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(MainActivity.this)

                        .setTitle("Has oblidat la contrasenya?")

                        .setPositiveButton("Enviar correu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity", "Correu enviat");
                                //AFEGIR ELIMINAR NOTA
                            }
                        })
                        .show();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla de registrar
            @Override
            public void onClick(View v) {
                gotToRegistrar();
            }
        });
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
}