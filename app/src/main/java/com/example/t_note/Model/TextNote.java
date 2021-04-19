package com.example.t_note.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class TextNote extends Note implements saveDades{
    private String text;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public TextNote(String tittle, Date dataCreació, String text) {
        super(tittle, dataCreació);
        this.text=text;
    }

    public TextNote(String tittle, Date dataCreació, String user, String text) {
        super(tittle, dataCreació, user);
        this.text=text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void saveNote() {
        Log.d("saveNote", "saveNote-> saveTextNoteToBase");
        adapter.saveTextNoteToBase(getTittle(),getDataCreacio(),getUser(),text);
    }
}
