package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.t_note.Model.CustomNoteAdapter;
import com.example.t_note.Model.DrawNote;
import com.example.t_note.Model.ImageNote;
import com.example.t_note.Model.Note;
import com.example.t_note.Model.PantallaViewModel;
import com.example.t_note.Model.TextNote;
import com.example.t_note.Model.Users;
import com.example.t_note.Model.VoiceNote;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaActivity extends AppCompatActivity implements CustomNoteAdapter.playerInterface{

    private ImageButton eliminar, compartir, copiar,configuracio;
    private ExtendedFloatingActionButton crearNota;
    private Button tot, mensual, anual, seleccioTot, seleccioMensual, seleccioAnual;
    private RecyclerView rview;
    private ListAdapterNote listAdapterNote;
    private int position;
    private Context parentContext;
    private AppCompatActivity mActivity;

    private String userName;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private PantallaViewModel viewModel;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    // Requesting permission to use CAMERA
    private boolean permissionToUseCameraAccepted = false;
    private final String[] permissions = {Manifest.permission.CAMERA};
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    Bitmap foto;
    ImageView imatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla);

        parentContext = this.getBaseContext();
        mActivity = this;

        //ImatgeButtons
        eliminar = this.findViewById(R.id.boton_Eliminar);
        compartir = this.findViewById(R.id.boton_Compartir);
        copiar = this.findViewById(R.id.boton_Copia);
        configuracio = this.findViewById(R.id.boton_Configuracio);
        crearNota = this.findViewById(R.id.boton_CrearNota);
        rview= findViewById(R.id.recycle);

        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(this));

        //Button
        tot = this.findViewById(R.id.boton_Tot);
        mensual = this.findViewById(R.id.boton_Mensual);
        anual = this.findViewById(R.id.boton_Anual);
        seleccioTot = this.findViewById(R.id.boton_SeleccioTot);
        seleccioTot.setClickable(false);
        seleccioTot.setVisibility(View.INVISIBLE);
        seleccioMensual = this.findViewById(R.id.boton_SeleccioMensual);
        seleccioMensual.setClickable(false);
        seleccioMensual.setVisibility(View.INVISIBLE);
        seleccioAnual = this.findViewById(R.id.boton_SeleccioAnual);
        seleccioAnual.setClickable(false);
        seleccioAnual.setVisibility(View.INVISIBLE);

        setunvisible();
        changeToTot();

        //ViewModel
        viewModel = new ViewModelProvider(this).get(PantallaViewModel.class);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users u = snapshot.getValue(Users.class);
                if(u != null){
                    userName = u.getName();

                    viewModel.setUserName(userName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PantallaActivity.this, "Ha passat alg??n problema!", Toast.LENGTH_LONG).show();
            }
        });

        setLiveDataObservers();

        //recyclerview();


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
        startActivity(intent);
    }

    public void goToNewNote(){
        View popupView = getLayoutInflater().inflate(R.layout.activity_nota, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 1100, 1000);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(rview, Gravity.CENTER, 0, 0);
        ImageButton guardar,dibuixar,foto,grabar;
        EditText titol,text;
        dibuixar=popupView.findViewById(R.id.icon6);
        grabar=popupView.findViewById(R.id.icon7);
        guardar=popupView.findViewById(R.id.boton_guardar_nota);
        titol = popupView.findViewById(R.id.titol_nota);
        text = popupView.findViewById(R.id.text_nota);
        foto= popupView.findViewById(R.id.cam);
        guardar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
               viewModel.addTextNote(titol.getText().toString(), new Date(),userName, text.getText().toString());
               popupWindow.dismiss();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                camPopUp();

            }
        });
        grabar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                GrabadoraPopUp();

            }
        });
        dibuixar.setOnClickListener(new View.OnClickListener() {
            //Iniciar pantalla main
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                DibuixarPopUp();

            }
        });
    }

    private void camPopUp() {

        View popupView = getLayoutInflater().inflate(R.layout.activity_camera, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 900, 1000);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(rview, Gravity.CENTER, 0, 0);
        Button cam;
        ImageButton guardarCam;

        EditText titol;
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CAMERA_PERMISSION);
        imatge = popupView.findViewById(R.id.imageView);
        cam = popupView.findViewById(R.id.btnCamara);
        guardarCam =popupView.findViewById(R.id.boton_guardar_imatge);
        titol= popupView.findViewById(R.id.titol_imatge);


        guardarCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                //viewModel.addTextNote();

            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);
            }
        });

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

    private void DibuixarPopUp() {
        View popupView = getLayoutInflater().inflate(R.layout.activity_dibuixar, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 900, 1000);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(rview, Gravity.CENTER, 0, 0);
    }

    private void GrabadoraPopUp() {
        View popupView = getLayoutInflater().inflate(R.layout.activity_grabadora, null);
        PopupWindow popupWindow = new PopupWindow(popupView, 900, 1000);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(rview, Gravity.CENTER, 0, 0);
    }


    public void changeToTot(){
        //Canviar finestra RecyclerView per mostrar tot en ordre
        seleccioTot.setVisibility(View.VISIBLE);
        seleccioMensual.setVisibility(View.INVISIBLE);
        seleccioAnual.setVisibility(View.INVISIBLE);
        //recyclerview();
    }

    public void changetoMensual(){
        //Canviar finestra RecyclerView per mostrar segons el ordre mensual
        seleccioTot.setVisibility(View.INVISIBLE);
        seleccioMensual.setVisibility(View.VISIBLE);
        seleccioAnual.setVisibility(View.INVISIBLE);
        //recyclerview();
    }

    public void changetoAnual(){
        //Canviar finestra RecyclerView per mostrar tot en ordre anual
        seleccioTot.setVisibility(View.INVISIBLE);
        seleccioMensual.setVisibility(View.INVISIBLE);
        seleccioAnual.setVisibility(View.VISIBLE);
        //recyclerview();
    }

    /*
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
    }*/

    public void setLiveDataObservers() {
        //Subscribe the activity to the observable

        final Observer<ArrayList<Note>> observer = new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> ac) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA NOTTTTTTTTAAAAAAAAAAAAAATTTTTTTTTTTT CANVIIIII");
                CustomNoteAdapter newAdapter = new CustomNoteAdapter(parentContext, ac, (CustomNoteAdapter.playerInterface) mActivity);
                rview.swapAdapter(newAdapter, false);
                newAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getlNotes().observe(this, observer);
    }

    @Override
    public void Prueba() {
        System.out.println("HEllo");
    }
}