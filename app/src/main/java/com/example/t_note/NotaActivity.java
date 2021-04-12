package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.t_note.Model.TextNote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotaActivity extends AppCompatActivity {
    private ImageButton guardar,dibuixar,foto,grabar;
    private EditText titol,text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        guardar=findViewById(R.id.boton_guardar_nota);
        titol = findViewById(R.id.titol_nota);
        text = findViewById(R.id.text_nota);


        guardar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                gotoPantallaActivity();

            }
        });
    }

    private void gotoPantallaActivity() {
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent( this, PantallaActivity.class);
        if (bundle != null){
            ArrayList<TextNote>  llista = (ArrayList<TextNote>) bundle.getSerializable("nota");
            llista.add(new TextNote(titol.getText().toString(), new Date(), text.getText().toString()));
            intent.putExtra("nota",  llista);
        }





        startActivity(intent);
    }
}