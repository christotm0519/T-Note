package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.t_note.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity {

    private TextInputLayout layoutUsuari, layoutContrasenya, layoutCorreo;
    private TextInputEditText usuari, correo, contrasenya;
    private Button confirmar;
    private ImageButton tornar;

    private FirebaseAuth mAuth;

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
        tornar = this.findViewById(R.id.boton_tornarM);
        confirmar = this.findViewById(R.id.botonR_Confirmar);

        mAuth = FirebaseAuth.getInstance();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprovació de les dades
                canRegistrar();
            }
        });

        tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void canRegistrar(){
        String userName = usuari.getText().toString();
        String email = correo.getText().toString();
        String password = contrasenya.getText().toString();

        if(userName.isEmpty()){
            usuari.setError("Nom d'usuari necessari!");
            usuari.requestFocus();
            return;
        }
        if(email.isEmpty()){
            correo.setError("Correo necessari!");
            correo.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correo.setError("Introdueix un correo vàlid!");
            correo.requestFocus();
            return;
        }

        if(password.isEmpty()){
            contrasenya.setError("Contrasenya necessari!");
            contrasenya.requestFocus();
            return;
        }

        if(password.length() < 6){
            contrasenya.setError("La contrasenya ha de contenir 6 caràcters com a mínim!");
            contrasenya.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users u = new Users(userName,email,password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistrarActivity.this, "S'ha afegit correctament l'usuari!", Toast.LENGTH_LONG).show();
                                        //Redirect to Login layout
                                        goToMain();
                                    }else{
                                        Toast.makeText(RegistrarActivity.this, "No s'ha pogut registrar l'usuari! Torna a provar!", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegistrarActivity.this, "Np s'ha pogut registrar!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goToMain(){
        Intent intent = new Intent( this, MainActivity.class);
        startActivity(intent);
    }
}