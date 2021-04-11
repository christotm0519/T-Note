package com.example.t_note.Model;

import android.provider.ContactsContract;

public class TextNote extends Note{
    private String text;


    public TextNote(String tittle,/* ContactsContract.Contacts.Data dataCreació*/ int dataCreació,String text) {
        super(tittle, dataCreació);
        this.text=text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
