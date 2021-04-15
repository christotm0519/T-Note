package com.example.t_note.Model;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

public class DrawNote extends Note implements Serializable {

    public DrawNote(String tittle, Date dataCreació) {
        super(tittle, dataCreació);
    }
}
