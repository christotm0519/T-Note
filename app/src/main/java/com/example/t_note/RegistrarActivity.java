package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrarActivity extends AppCompatActivity {

    TextInputLayout layoutUsuari, layoutContrasenya, layoutCorreo;
    TextInputEditText usuari, correo, contrasenya;
    Button confirmar;

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

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprovació de les dades
                canRegistrar();
            }
        });
    }

    public void canRegistrar(){
        //Comprovar l'usuari -> no es troba vinculat a un altre compte
        //Comprovar l'e-mail -> no es troba vinculat a un altre compte
        //Comprovar la contrassenya -> no buida

        //-->Tot correcte
        Intent intent = new Intent(this,MainActivity.class);
        //intent.putExtra -> en cas de voler enviar dades
        startActivity(intent);
    }
}