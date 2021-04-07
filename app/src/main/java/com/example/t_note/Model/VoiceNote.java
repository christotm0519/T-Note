package com.example.t_note.Model;

import android.provider.ContactsContract;

public class VoiceNote extends Note{

    public VoiceNote(String tittle, ContactsContract.Contacts.Data dataCreació) {
        super(tittle, dataCreació);
    }
}
