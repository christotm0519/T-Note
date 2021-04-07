package com.example.t_note.Model;

import android.provider.ContactsContract;

public class DrawNote extends Note{

    public DrawNote(String tittle, ContactsContract.Contacts.Data dataCreació) {
        super(tittle, dataCreació);
    }
}
