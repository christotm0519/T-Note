package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

public class GrabadoraActivity extends AppCompatActivity {
    final static int RQS_RECORDING = 1;
    private boolean permissionToUseMicroAccepted = false;
    private final String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_MICRO_PERMISSION = 200;
    Uri savedUri;

    Chronometer crono;
    ImageButton start,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabadora);

        crono = (Chronometer) findViewById(R.id.cronometre);

        start = (ImageButton) findViewById(R.id.botonMicro);
        stop = (ImageButton) findViewById(R.id.stopBoton);
        stop.setVisibility(View.INVISIBLE);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crono.start();
                stop.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
                //dispatchTakePictureIntent(v);
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

    /*private void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaRecorder.);
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, REQUEST_MICRO_PERMISSION);
        // }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQS_RECORDING) {
            savedUri = data.getData();
            Toast.makeText(GrabadoraActivity.this,
                    "Saved: " + savedUri.getPath(), Toast.LENGTH_LONG).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_MICRO_PERMISSION) {
            permissionToUseMicroAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToUseMicroAccepted) finish();
    }*/

    private void gotopantalla() {
        Intent intent = new Intent( this, NotaActivity.class);
        startActivity(intent);
    }

}