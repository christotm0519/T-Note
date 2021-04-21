package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {


    private Button canviarpass, tancarSessio,tema;
    private TextView userName;
    private String user;
    private ImageButton tornar;

    //private FirebaseAuth user;
    //private DatabaseReference reference;
    //private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        canviarpass= this.findViewById(R.id.canviarpass);
        userName = this.findViewById(R.id.nomUsuariL);
        tema = this.findViewById(R.id.tema);
        tancarSessio = this.findViewById(R.id.boton_TancarSessio);
        tornar= this.findViewById(R.id.saveButton);

        //user = FirebaseAuth.getInstance().getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference("Users");
        //userId = user.getUid();


        //Obtenim informació passada
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            user = (String) bundle.get("User");
            userName.setText(user);
        }

        tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPantallaActivity();
            }
        })  ;

        tancarSessio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tancar sessió
                goToCloseLogin();
            }
        });

        canviarpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Canviar contrassenya
                goToChangePassword();
            }
        });
        tema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Canviar contrassenya
                PopupMenu popup = new PopupMenu(Settings.this, tema);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("predeterminat")){
                            setTheme(R.style.AppTheme);

                        }
                        else if(item.getTitle().equals("nit")){
                            setTheme(R.style.Theme_MaterialComponents_DayNight);

                        }
                        else{

                        }
                    return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method
            }

    private void gotoPantallaActivity() {
        Intent intent = new Intent(this,PantallaActivity.class);
        //intent.putExtra -> en cas de voler enviar dades
        startActivity(intent);
    }


    public void goToCloseLogin(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void goToChangePassword(){
        Intent intent = new Intent(this, CanviContrasenyaActivity.class);
        intent.putExtra("User",user);
        startActivity(intent);
    }

}
