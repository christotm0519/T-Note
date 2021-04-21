package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.t_note.Model.DrawNote;
import com.example.t_note.Model.ImageNote;
import com.example.t_note.Model.Note;
import com.example.t_note.Model.PantallaViewModel;
import com.example.t_note.Model.TextNote;
import com.example.t_note.Model.VoiceNote;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PantallaActivity extends AppCompatActivity{

    private ImageButton eliminar, compartir, copiar,configuracio;
    private ExtendedFloatingActionButton crearNota;
    private Button tot, mensual, anual, seleccioTot, seleccioMensual, seleccioAnual;
    private RecyclerView rview;
    private ListAdapterNote listAdapterNote;
    private int position;
    private String user;
    private PantallaViewModel viewModel;

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
        seleccioTot = this.findViewById(R.id.boton_SeleccioTot);
        seleccioTot.setActivated(false);
        seleccioTot.setClickable(false);
        seleccioTot.setVisibility(View.INVISIBLE);
        seleccioMensual = this.findViewById(R.id.boton_SeleccioMensual);
        seleccioMensual.setActivated(false);
        seleccioMensual.setClickable(false);
        seleccioMensual.setVisibility(View.INVISIBLE);
        seleccioAnual = this.findViewById(R.id.boton_SeleccioAnual);
        seleccioAnual.setActivated(false);
        seleccioAnual.setClickable(false);
        seleccioAnual.setVisibility(View.INVISIBLE);
        setunvisible();
        changeToTot();
        recyclerview();


        //ViewModel
        viewModel = new ViewModelProvider(this).get(PantallaViewModel.class);
        setLiveDataObservers();

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
                Note element = listAdapterNote.getItem(position);
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
            public void onClick(View v) { changeToTot();
            }
        });

        //Mensual
        mensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changetoMensual();
            }
        });

        //Anual
        anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changetoAnual();
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
        intent.putExtra("User",this.user);
        startActivity(intent);
    }

    public void goToNewNote(){
        //Intent intent = new Intent(this,Camera.class);//prova
        Intent intent = new Intent(this,NotaActivity.class);
        intent.putExtra("list", (Serializable) listAdapterNote.getdata());
        intent.putExtra("viewModel", (Parcelable) viewModel);
        System.out.println(viewModel);
        startActivity(intent);
    }


    public void changeToTot(){
        //Canviar finestra RecyclerView per mostrar tot en ordre
        seleccioTot.setVisibility(View.VISIBLE);
        seleccioMensual.setVisibility(View.INVISIBLE);
        seleccioAnual.setVisibility(View.INVISIBLE);
        recyclerview();
    }

    public void changetoMensual(){
        //Canviar finestra RecyclerView per mostrar segons el ordre mensual
        seleccioTot.setVisibility(View.INVISIBLE);
        seleccioMensual.setVisibility(View.VISIBLE);
        seleccioAnual.setVisibility(View.INVISIBLE);
        recyclerview();
    }

    public void changetoAnual(){
        //Canviar finestra RecyclerView per mostrar tot en ordre anual
        seleccioTot.setVisibility(View.INVISIBLE);
        seleccioMensual.setVisibility(View.INVISIBLE);
        seleccioAnual.setVisibility(View.VISIBLE);
        recyclerview();
    }

    void recyclerview(){
        listAdapterNote = new ListAdapterNote(new ArrayList<Note>(),this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            List<Note> list= (List<Note>) bundle.get("list");
            Note note = (Note) bundle.getSerializable("NewNote");
            if(list!=null){
                listAdapterNote.setData(list);
            }
            if(note!=null) {


                    if (bundle.get("edit")== null || !(boolean)bundle.get("edit")){
                        listAdapterNote.add(note);
                    }
                    else{
                        position = (int) bundle.get("position");
                        listAdapterNote.replace(position, note);
                    }

                }

        }
        PantallaActivity m = this;
        listAdapterNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rview.getChildAdapterPosition(v);

                Note element =  listAdapterNote.getItem(position);
                if(element instanceof TextNote){
                    Intent intent = new Intent(m,NotaActivity.class);
                    intent.putExtra("titol",element.getTittle());
                    intent.putExtra("text", ((TextNote) element).getText());
                    intent.putExtra("position",position);
                    intent.putExtra("list", (Serializable) listAdapterNote.getdata());
                    startActivity(intent);
                }
                else if(element instanceof ImageNote){
                    Intent intent = new Intent(m,Camera.class);
                    intent.putExtra("titol",element.getTittle());
                    byte[] byteArray = ((ImageNote) element).getImatge();
                    Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    intent.putExtra("Imatge", bmp);
                    intent.putExtra("position",position);
                    intent.putExtra("list", (Serializable) listAdapterNote.getdata());
                    startActivity(intent);
                }


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

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable

        final Observer<ArrayList<Note>> observer = new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> ac) {
                //CustomAdapter newAdapter = new CustomAdapter(parentContext, ac, (CustomAdapter.playerInterface) mActivity);
                //mRecyclerView.swapAdapter(newAdapter, false);
                //newAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getlNotes().observe(this, observer);
    }

}