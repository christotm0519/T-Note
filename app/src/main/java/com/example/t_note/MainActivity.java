package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    private TextInputLayout layoutUsuari, layoutContrasenya;
    private TextInputEditText correo, contrasenya;
    private Button confirmar, forgot,registrar;

    private RecyclerView mRecyclerView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enregistrem els components
        layoutUsuari = this.findViewById(R.id.layoutM_Usuari);
        layoutContrasenya = this.findViewById(R.id.layoutM_Contrasenya);
        correo = this.findViewById(R.id.editM_Usuari);
        contrasenya = this.findViewById(R.id.editM_Contrasenya);
        confirmar = this.findViewById(R.id.botonM_Confirmar);
        forgot = this.findViewById(R.id.botonM_ForgotPassword);
        registrar = this.findViewById(R.id.botonM_Registrarse);
        mRecyclerView = this.findViewById(R.id.recycleM);

        //ViewModel
        mAuth = FirebaseAuth.getInstance();

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
                goToForgotPassword(mRecyclerView);
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

    public void goToForgotPassword(View anchorView){
        View popupView = getLayoutInflater().inflate(R.layout.activity_forgot_password, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 800, 600);

        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);
        // If you need the PopupWindow to dismiss when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        Button saveButton = popupView.findViewById(R.id.boton_EnviarEmail);
        EditText emailText = popupView.findViewById(R.id.editF_email);
        saveButton.setOnClickListener((v) -> {
            String email = emailText.getText().toString();

            if(email.isEmpty()){
                emailText.setError("Correo necessari!");
                emailText.requestFocus();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailText.setError("Introdueix un correo vàlid!");
                emailText.requestFocus();
                return;
            }
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Comprova el teu correo electrònic per canviar la contrasenya!", Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }else{
                        Toast.makeText(MainActivity.this, "No s'ha pogut enviar el correo!", Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }
                }
            });
        });
    }

    public void logIn(){
        String email = correo.getText().toString();
        String password = contrasenya.getText().toString();

        if(email.isEmpty()){
            correo.setError("Correo necessari!");
            correo.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            correo.setError("Introdueix un correo electrònic vàlid!");
            correo.requestFocus();
            return;
        }

        if(password.isEmpty()){
            contrasenya.setError("Contrasenya necessaria!");
            contrasenya.requestFocus();
            return;
        }

        if(password.length() < 6){
            contrasenya.setError("La contrasenya ha de contenir 6 caràcters com a mínim!");
            contrasenya.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Redirect to user profile
                    gotToPantallaActivity();
                }else{
                    Toast.makeText(MainActivity.this, "No s'ha pogut iniciar sessió! Revisa la informació!", Toast.LENGTH_LONG).show();
                    correo.setText("");
                    contrasenya.setText("");
                }
            }
        });
    }

    public void gotToPantallaActivity(){
        Intent intent = new Intent( this, PantallaActivity.class);
        startActivity(intent);
    }

}