package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CanviContrasenyaActivity extends AppCompatActivity {

    TextInputEditText passwordA, passwordN;
    Button confirmar,forgot;
    private String user;
    TextView textError;
    private RecyclerView cRecyclerView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvi_contrasenya);

        passwordA = this.findViewById(R.id.editC_Password);
        passwordN = this.findViewById(R.id.editC_PasswordC);
        textError = this.findViewById(R.id.textC_Error);
        confirmar = this.findViewById(R.id.botonC_Confirmar);
        cRecyclerView = this.findViewById(R.id.recycleC);

        forgot = this.findViewById(R.id.botonM_ForgotPassword);

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
        forgot.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla de perdua de contrasenya
            @Override
            public void onClick(View v) {
                goToForgotPassword(cRecyclerView);
            }
        });

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
        EditText passwordAntiguaText = popupView.findViewById(R.id.editC_Password);
        saveButton.setOnClickListener((v) -> {
            String passwordAntigua = passwordA.getText().toString();

            if(passwordAntigua.isEmpty()){
                passwordAntiguaText.setError("Contrasenya necessaria!");
                passwordAntiguaText.requestFocus();
                return;
            }

            if(passwordAntigua.length() < 6){
                passwordAntiguaText.setError("Introdueix una contrasenya vàlida!");
                passwordAntiguaText.requestFocus();
                return;
            }
            mAuth.sendPasswordResetEmail(passwordAntigua).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CanviContrasenyaActivity.this, "Comprova el teu correo electrònic per canviar la contrasenya!", Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }else{
                        Toast.makeText(CanviContrasenyaActivity.this, "No s'ha pogut enviar el correo!", Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }
                }
            });
        });
    }

    /*public void logIn(){
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
    }*/

    public void gotToPantallaActivity(){
        Intent intent = new Intent( this, PantallaActivity.class);
        startActivity(intent);
    }

    public void changePassword(){
        EditText password = (EditText) passwordA;
        EditText newPassword = (EditText) passwordN;
    }

}