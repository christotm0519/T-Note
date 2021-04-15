package com.example.t_note;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.t_note.Model.DrawNote;
import com.example.t_note.Model.ImageNote;
import com.example.t_note.Model.Note;
import com.example.t_note.Model.TextNote;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Camera extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    // Requesting permission to use CAMERA
    private boolean permissionToUseCameraAccepted = false;
    private final String[] permissions = {Manifest.permission.CAMERA};
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean edit=false;

    Button cam;
    ImageView imatge;
    ImageButton guardar;
    private Bitmap foto;
    private EditText titol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION);
        imatge = findViewById(R.id.imageView);
        cam = findViewById(R.id.btnCamara);
        guardar = findViewById(R.id.boton_guardar_imatge);
        titol= findViewById(R.id.titol_imatge);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            String titolanterior = (String) bundle.get("titol");
            Bitmap imatgeanterior = (Bitmap) bundle.get("text");
            if (titolanterior != null) {
                edit = true;
                titol.setText(titolanterior);
                imatge.setImageBitmap(imatgeanterior);


            }
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotopantalla();

            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);
            }
        });


    }

    private void gotopantalla() {

        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent( this, PantallaActivity.class);
        if(bundle!=null){
            List<Note> listAdapterNote= (List<Note>) bundle.getSerializable("list");
            intent.putExtra("list", (Serializable) listAdapterNote);
            if(edit){
                intent.putExtra("position", (Integer) bundle.get("position"));
            }
        }
        ImageNote imageNote = new ImageNote(titol.getText().toString(), new Date());
        intent.putExtra("imatge",foto);
        intent.putExtra("NewNote",imageNote);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            permissionToUseCameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if (!permissionToUseCameraAccepted) finish();
    }


    private void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
       // }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            foto= (Bitmap) extras.get("data");
            imatge.setImageBitmap(foto);
        }
    }


}