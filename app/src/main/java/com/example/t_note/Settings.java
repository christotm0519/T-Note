package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {


    private Button canviarpass;
    private Spinner tema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        canviarpass= findViewById(R.id.canviarpass);
        findViewById(R.id.fotoperfil);
        findViewById(R.id.nomUsuari);
        tema= findViewById(R.id.tema);


        canviarpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        tema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                   // setTheme(R.style.);
                }
                else if(position==1){

                }
                else if(position==2){

                }
            }
        });
    }
}