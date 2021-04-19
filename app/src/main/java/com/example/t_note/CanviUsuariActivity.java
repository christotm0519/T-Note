package com.example.t_note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.t_note.Model.ChangeViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class CanviUsuariActivity extends AppCompatActivity {

    TextInputEditText userN;
    Button confirmar;
    private String user;
    TextView textError;

    private ChangeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvi_usuari);

        userN = this.findViewById(R.id.editC_NameC);
        textError = this.findViewById(R.id.textC_ErrorN);
        confirmar = this.findViewById(R.id.botonC_ConfirmarN);

        //ViewModel
        viewModel = new ViewModelProvider(this).get(ChangeViewModel.class);

        //Obtenim informació passada
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            user = (String) bundle.get("User");
        }

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Canviar usuari
                changeUsuari();
            }
        });
    }

    public void changeUsuari(){
        EditText newName = (EditText) userN;
    }
}