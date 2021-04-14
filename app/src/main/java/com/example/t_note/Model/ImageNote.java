package com.example.t_note.Model;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

public class ImageNote extends Note implements Serializable {
    private Bitmap imatge;


    public ImageNote(String tittle,/* ContactsContract.Contacts.Data dataCreació*/ Date dataCreació, Bitmap imatge) {
        super(tittle, dataCreació);
        this.imatge=imatge;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }
}
