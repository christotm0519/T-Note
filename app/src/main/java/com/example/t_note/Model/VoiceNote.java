package com.example.t_note.Model;

import android.provider.ContactsContract;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class VoiceNote extends Note implements Serializable, saveDades {

    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public VoiceNote(String tittle, Date dataCreació) {
        super(tittle, dataCreació);
    }

    public VoiceNote(String tittle, Date dataCreació, String user) { super(tittle, dataCreació, user); }

    @Override
    public void saveNote() {
        Log.d("saveNote", "saveNote-> saveVoiceNoteToBase");
        adapter.saveVoiceNoteToBase(getTittle(),getDataCreacio(),getUser());
    }

}
