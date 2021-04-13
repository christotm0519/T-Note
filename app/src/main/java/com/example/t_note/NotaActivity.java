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
    private boolean edit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        guardar=findViewById(R.id.boton_guardar_nota);
        titol = findViewById(R.id.titol_nota);
        text = findViewById(R.id.text_nota);
        foto= findViewById(R.id.cam);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String titolanterior = (String) bundle.get("titol");
            String textanterior = (String)bundle.get("text");
            if(titolanterior!=null){
                edit=true;
                titol.setText(titolanterior);
                text.setText(textanterior);


            }

        }


        guardar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                gotoPantallaActivity();

            }
        });
    
    foto.setOnClickListener(new View.OnClickListener() {
        //Iniciar pantalla main
        @Override
        public void onClick(View v) {
            gotoCameractivity();

        }
    });
}

    private void gotoCameractivity() {
        Intent intent = new Intent(this,Camera.class);
        startActivity(intent);
    }

    private void gotoPantallaActivity() {
        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent( this, PantallaActivity.class);
        intent.putExtra("edit",edit);
        if(bundle!=null){
            List<TextNote> listAdapterNote= (List<TextNote>) bundle.getSerializable("Adapter");
            intent.putExtra("Adapter", (Serializable) listAdapterNote);
            if(edit){
                intent.putExtra("position", (Integer) bundle.get("position"));
            }
        }
        intent.putExtra("NewNote",new TextNote(titol.getText().toString(), new Date(), text.getText().toString()));
        startActivity(intent);
    }
}