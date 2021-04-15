package com.example.t_note.Model;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

public class VoiceNote extends Note implements Serializable {

    public VoiceNote(String tittle, Date dataCreació) {
        super(tittle, dataCreació);
    }
}
