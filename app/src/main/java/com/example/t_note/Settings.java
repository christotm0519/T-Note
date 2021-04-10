package com.example.t_note;

import androidx.annotation.MenuRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.Menu;
import android.view.ContextMenu;
import 	android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ListPopupWindow;
public class Settings extends AppCompatActivity {


    private Button canviarpass, tancarSessio,tema;
    private ImageButton tornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        canviarpass= findViewById(R.id.canviarpass);
        findViewById(R.id.fotoperfil);
        findViewById(R.id.nomUsuari);
        tema =findViewById(R.id.tema);
        tancarSessio = this.findViewById(R.id.boton_TancarSessio);
        tornar= findViewById(R.id.boton_Settings);

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
                        System.out.println(item.getItemId());
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
        Intent intent = new Intent(this,MainActivity.class);
        //intent.putExtra -> en cas de voler enviar dades
        startActivity(intent);
    }

    public void goToChangePassword(){
        //Intent intent = new Intent(this,.class);
        //startActivity(intent);
    }

}
