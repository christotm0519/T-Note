package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PantallaActivity extends AppCompatActivity {

    private ImageButton eliminar, compartir, copiar,configuracio, crearNota;
    private Button tot, mensual, anual;

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

        //Button
        tot = this.findViewById(R.id.boton_Tot);
        mensual = this.findViewById(R.id.boton_Mensual);
        anual = this.findViewById(R.id.boton_Anual);


        //Eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });

        //Configuracio
        configuracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Crear nota
        crearNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Tot
        tot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Mensual
        mensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Anual
        anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}