package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.t_note.Model.IniciarRegistrarViewModel;
import com.example.t_note.Model.Users;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrarActivity extends AppCompatActivity {

    TextInputLayout layoutUsuari, layoutContrasenya, layoutCorreo;
    TextInputEditText usuari, correo, contrasenya;
    Button confirmar;
    TextView textError;

    private IniciarRegistrarViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Enregistrem els components
        layoutUsuari = this.findViewById(R.id.layoutR_Usuari);
        layoutContrasenya = this.findViewById(R.id.layoutR_Contrasenya);
        layoutCorreo = this.findViewById(R.id.layoutR_Correo);
        usuari = this.findViewById(R.id.editR_Usuari);
        correo = this.findViewById(R.id.editR_Correo);
        contrasenya = this.findViewById(R.id.editR_Contrasenya);
        confirmar = this.findViewById(R.id.botonR_Confirmar);

        viewModel = new ViewModelProvider(this).get(IniciarRegistrarViewModel.class);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprovació de les dades
                canRegistrar();
            }
        });
    }

    public void canRegistrar(){
        EditText name = (EditText) usuari;
        EditText email = (EditText) correo;
        EditText password = (EditText) contrasenya;

        if(name.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")){
            option(2);
        }
        else if(viewModel.registrar(name.getText().toString(),email.getText().toString(),password.getText().toString())){
            //-->Tot correcte
            finish();
        }else{
            if(viewModel.findNameUser(name.getText().toString())){
                option(1);
            }if(viewModel.findEmailUser(email.getText().toString())){
                option(3);
            }
        }
    }

    public void option(int index){
        switch (index){
            case 1: //Usuari ja existent!
                textError.setText("Usuari ja existent!");
                usuari.setText("");
                break;
            case 2: //Dades buides
                textError.setText("Falten dades!");
                usuari.setText("");
                correo.setText("");
                contrasenya.setText("");
                break;
            case 3: //Correo electrònic ja vinculat a altre compte!
                textError.setText("Correo electrònic ja vinculat a altre compte!");
                correo.setText("");
                break;
            default:
                break;
        }
    }
}