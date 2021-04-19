package com.example.t_note.Model;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class ImageNote extends Note implements saveDades{
    private Bitmap imatge;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public ImageNote(String tittle,Date dataCreacio) {
        super(tittle, dataCreacio);;
    }

    public ImageNote(String tittle,Date dataCreacio, String user) {
        super(tittle, dataCreacio, user);;
    }

    public ImageNote(String tittle, Date dataCreacio, Bitmap imatge) {
        super(tittle, dataCreacio);
        this.imatge=imatge;
    }

    public ImageNote(String tittle, Date dataCreacio, String user, Bitmap imatge) {
        super(tittle, dataCreacio,user);
        this.imatge=imatge;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }

    @Override
    public void saveNote() {
        Log.d("saveNote", "saveNote-> saveImageNoteToBase");
        adapter.saveImageNoteToBase(getTittle(),getDataCreacio(),getUser());
    }
}
