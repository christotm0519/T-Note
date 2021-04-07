package com.example.t_note.Model;

import android.provider.ContactsContract;

public class Note {
    private String tittle;
    private ContactsContract.Contacts.Data dataCreació;

    public Note(String tittle, ContactsContract.Contacts.Data dataCreació) {
        this.tittle = tittle;
        this.dataCreació = dataCreació;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public ContactsContract.Contacts.Data getDataCreació() {
        return dataCreació;
    }

    public void setDataCreació(ContactsContract.Contacts.Data dataCreació) {
        this.dataCreació = dataCreació;
    }
}
