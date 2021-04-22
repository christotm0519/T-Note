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

import com.example.t_note.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CanviContrasenyaActivity extends AppCompatActivity {

    TextInputEditText passwordA, passwordN;
    Button confirmar, forgot;
    private String userName;
    TextView textError;
    private RecyclerView cRecyclerView;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String passwordActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvi_contrasenya);

        passwordA = this.findViewById(R.id.editC_Password);
        passwordN = this.findViewById(R.id.editC_PasswordC);
        textError = this.findViewById(R.id.textC_Error);
        confirmar = this.findViewById(R.id.botonC_Confirmar);
        cRecyclerView = this.findViewById(R.id.recycleC);

        forgot = this.findViewById(R.id.botonM_ForgotPassword2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users u = snapshot.getValue(Users.class);

                if (u != null) {
                    passwordActual = u.getPassword();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Obtenim informació passada
        //Bundle bundle = getIntent().getExtras();
        //if(bundle!=null) {
        //   user = (String) bundle.get("User");
        //}

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

    public void goToForgotPassword(View anchorView) {
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


    public void gotToPantallaActivity() {
        Intent intent = new Intent(this, PantallaActivity.class);
        startActivity(intent);
    }

    public void changePassword() {
        String password = passwordA.getText().toString();
        String newPassword = passwordN.getText().toString();

        if (password.isEmpty()) {
            passwordA.setError("Contrasenya actual necessaria!");
            passwordA.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordN.setError("La contrasenya ha de contenir 6 caràcters com a mínim!");
            passwordN.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            passwordN.setError("Contrasenya nova necessaria!");
            passwordN.requestFocus();
            return;
        }
        if (newPassword.length() < 6) {
            passwordN.setError("La contrasenya ha de contenir 6 caràcters com a mínim!");
            passwordN.requestFocus();
            return;
        }



        if (password.equals(passwordActual)) {
            //Realitzar canvi
            user.updatePassword(newPassword);
            Toast.makeText(CanviContrasenyaActivity.this, "La contrasenya ha sigut actualitzada!", Toast.LENGTH_LONG).show();
            finish();
            /*
            AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),passwordActual);
            user.reauthenticate(authCredential)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            user.updatePassword(newPassword)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(CanviContrasenyaActivity.this, "La contrasenya ha sigut actualitzada!", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CanviContrasenyaActivity.this, "No s'ha pogut actualitzar la contrasenya!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CanviContrasenyaActivity.this, "No s'ha pogut actualitzar la contrasenya!", Toast.LENGTH_LONG).show();
                        }
                    });
        }else{
            Toast.makeText(CanviContrasenyaActivity.this, "La contrasenya actual introduïda és incorrecte!", Toast.LENGTH_LONG).show();
        }*/
        }

    }
}