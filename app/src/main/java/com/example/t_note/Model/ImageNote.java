package com.example.t_note.Model;

import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class ImageNote extends Note {
    private byte[] imatge;
    //private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public ImageNote(String tittle,Date dataCreacio) {
        super(tittle, dataCreacio);;
    }

    public ImageNote(String tittle,Date dataCreacio, String user) {
        super(tittle, dataCreacio, user);;
    }

    public ImageNote(String tittle, Date dataCreacio, byte[] imatge) {
        super(tittle, dataCreacio);
        this.imatge=imatge;
    }

    public ImageNote(String tittle, Date dataCreacio, String user, byte[] imatge) {
        super(tittle, dataCreacio,user);
        this.imatge=imatge;
    }

    public byte[] getImatge() {
        return imatge;
    }

    public void setImatge(byte[] imatge) {
        this.imatge = imatge;
    }

   /* @Override
    public void saveNote() {
        Log.d("saveNote", "saveNote-> saveImageNoteToBase");
        adapter.saveImageNoteToBase(getTittle(),getDataCreacio(),getUser());
    }*/
}
