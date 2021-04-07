package com.example.t_note.Model;

import android.provider.ContactsContract;

public class TextNote extends Note{

    public TextNote(String tittle, ContactsContract.Contacts.Data dataCreació) {
        super(tittle, dataCreació);
    }
}
