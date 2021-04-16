package com.example.t_note.Model;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

public class ImageNote extends Note{
    private Bitmap imatge;

    public ImageNote(String tittle,/* ContactsContract.Contacts.Data dataCreació*/ Date dataCreacio) {
        super(tittle, dataCreacio);;
    }
    public ImageNote(String tittle,/* ContactsContract.Contacts.Data dataCreació*/ Date dataCreacio, Bitmap imatge) {
        super(tittle, dataCreacio);
        this.imatge=imatge;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }
}
