package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class CanviContrasenyaActivity extends AppCompatActivity {

    TextInputEditText passwordA, passwordN;
    Button confirmar;
    private String user;
    TextView textError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvi_contrasenya);

        passwordA = this.findViewById(R.id.editC_Password);
        passwordN = this.findViewById(R.id.editC_PasswordC);
        textError = this.findViewById(R.id.textC_Error);
        confirmar = this.findViewById(R.id.botonC_Confirmar);


        //Obtenim informació passada
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            user = (String) bundle.get("User");
        }

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Canviar contrasenya
                changePassword();
            }
        });
    }

    public void changePassword(){
        EditText password = (EditText) passwordA;
        EditText newPassword = (EditText) passwordN;

    }

}