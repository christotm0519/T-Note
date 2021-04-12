package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.t_note.Model.TextNote;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PantallaActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton eliminar, compartir, copiar,configuracio, crearNota;
    private Button tot, mensual, anual;
    private RecyclerView rview;
    private List<TextNote> notestext;
    TextNote nota;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla);

        //ImatgeButtons
        eliminar = this.findViewById(R.id.boton_Eliminar);
        compartir = this.findViewById(R.id.boton_Compartir);
        copiar = this.findViewById(R.id.boton_Copia);
        configuracio = this.findViewById(R.id.boton_Configuracio);
        crearNota = this.findViewById(R.id.boton_CrearNota);
        rview= findViewById(R.id.recycle);

        //Button
        tot = this.findViewById(R.id.boton_Tot);
        mensual = this.findViewById(R.id.boton_Mensual);
        anual = this.findViewById(R.id.boton_Anual);

       setunvisible();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
        notestext = (List<TextNote>) bundle.getSerializable("nota");
        }
        else{
            notestext=new ArrayList<>();
        }
        recyclerview();



        //Eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(PantallaActivity.this)
                        .setMessage("Vols eliminar la nota?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(PantallaActivity.this,"Nota eliminada", Toast.LENGTH_SHORT).show();

                                if(notestext.get(position)!=null){
                                    notestext.remove(position);
                                    recyclerview();
                                }
                                setunvisible();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity", "Aborting mission...");
                            }
                        })
                        .show();
            }
        });

        //Compartir
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Copiar
        copiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextNote element = notestext.get(position);
                if(element!=null){
                    notestext.add(element);
                }
                recyclerview();
                setunvisible();


            }
        });

        //Configuracio
        configuracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSettings();
            }
        });

        //Crear nota
        crearNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    goToNewNote();

            }
        });

        //Tot
        tot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToTot();
            }
        });

        //Mensual
        mensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changetoMensual();
            }
        });

        //Anual
        anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changetoAnual();
            }
        });
    }

    private void setunvisible() {
        eliminar.setVisibility(View.INVISIBLE);
        compartir.setVisibility(View.INVISIBLE);
        copiar.setVisibility(View.INVISIBLE);
    }

    private void setvisible() {
        eliminar.setVisibility(View.VISIBLE);
        compartir.setVisibility(View.VISIBLE);
        copiar.setVisibility(View.VISIBLE);
    }

    public void goToSettings(){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
    }

    public void goToNewNote(){
        Intent intent = new Intent(this,NotaActivity.class);
        intent.putExtra("nota", (Serializable) notestext);
        startActivity(intent);
    }

    public void changeToTot(){
        //Canviar finestra RecyclerView per mostrar tot en ordre
    }

    public void changetoMensual(){
        //Canviar finestra RecyclerView per mostrar segons el ordre mensual
    }

    public void changetoAnual(){
        //Canviar finestra RecyclerView per mostrar tot en ordre anual
    }

    @Override
    public void onClick(View v) {
        if(R.id.boton_CrearNota == v.getId()){
            //Anar a la pantalla de creació d'una nota de text
            goToNewNote();
        }
    }

    void recyclerview(){
        ListAdapterNote listAdapterNote = new ListAdapterNote(notestext,this);
        PantallaActivity m = this;
        listAdapterNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rview.getChildAdapterPosition(v);
                Intent intent = new Intent(m,NotaActivity.class);
                TextNote element = notestext.get(position);
                intent.putExtra("titol",element.getTittle());
                intent.putExtra("text",element.getText());
                intent.putExtra("nota", (Serializable) notestext);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });
        listAdapterNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RecyclerView.ViewHolder card = rview.getChildViewHolder(v);
                position = rview.getChildAdapterPosition(v);
                setvisible();

                return true;
            }
        });
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(listAdapterNote);
    }


}