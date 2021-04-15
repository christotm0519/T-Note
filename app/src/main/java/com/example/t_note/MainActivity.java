package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t_note.Model.IniciarRegistrarViewModel;
import com.example.t_note.Model.TextNote;
import com.example.t_note.Model.Users;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.net.Authenticator;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    TextInputLayout layoutUsuari, layoutContrasenya;
    TextInputEditText usuari, contrasenya;
    Button confirmar, forgot,registrar;

    private IniciarRegistrarViewModel viewModel;


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

        viewModel = new ViewModelProvider(this).get(IniciarRegistrarViewModel.class);

        confirmar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                logIn();
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
        //intent.putExtra("viewModel", (Serializable) viewModel);
        startActivity(intent);
    }

    public void goToForgotPassword(){
        //Intent intent = new Intent(this,.class);
        //startActivity(intent);
    }

    public void logIn(){
        //Cal actualitzar la base de dades

        EditText name = (EditText) usuari;
        EditText password = (EditText) contrasenya;
        if(viewModel.canLog(name.getText().toString(),password.getText().toString())){
            gotToPantallaActivity();
        }

        //Netejem els camps
        usuari.setText("");
        contrasenya.setText("");
    }

    public void gotToPantallaActivity(){
        Intent intent = new Intent( this, PantallaActivity.class);
        startActivity(intent);

    }
}