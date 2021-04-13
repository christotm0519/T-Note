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
    private ListAdapterNote listAdapterNote;
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
                                ListAdapterNote adapterNote= (ListAdapterNote) rview.getAdapter();
                                adapterNote.remove(position);
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
                TextNote element = listAdapterNote.getItem(position);
                if(element!=null){
                    listAdapterNote.add(element);
                }
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
        intent.putExtra("Adapter", (Serializable) listAdapterNote.getdata());
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

        listAdapterNote = new ListAdapterNote(new ArrayList<TextNote>(),this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            List<TextNote> adapterNote= (List<TextNote>) bundle.get("Adapter");
            if(adapterNote!=null){
                TextNote NewNote = (TextNote) bundle.getSerializable("NewNote");
                listAdapterNote.setData(adapterNote);
                if((boolean) bundle.get("edit")){
                    position= (int) bundle.get("position");
                    listAdapterNote.replace(position,NewNote);

                }
                else {
                    listAdapterNote.add(NewNote);
                }
            }




        }
        PantallaActivity m = this;
        listAdapterNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rview.getChildAdapterPosition(v);
                Intent intent = new Intent(m,NotaActivity.class);
                TextNote element = listAdapterNote.getItem(position);
                intent.putExtra("titol",element.getTittle());
                intent.putExtra("text",element.getText());
                intent.putExtra("position",position);
                intent.putExtra("Adapter", (Serializable) listAdapterNote.getdata());
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