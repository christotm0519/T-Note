package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class GrabadoraActivity extends AppCompatActivity {

    private boolean permissionToUseMicroAccepted = false;
    private final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    Chronometer crono;
    private boolean running = false;
    ImageButton start,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabadora);

        crono = (Chronometer) findViewById(R.id.cronometre);

        start = (ImageButton) findViewById(R.id.botonMicro);
        stop = (ImageButton) findViewById(R.id.stopBoton);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crono.start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crono.stop();
                gotopantalla();
            }

        });



    }

    private void gotopantalla() {
        Intent intent = new Intent( this, PantallaActivity.class);
        startActivity(intent);
    }

}