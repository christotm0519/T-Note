package com.example.t_note.Model;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PantallaViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceNotes {

    private final MutableLiveData<ArrayList<Note>> lNotes;
    private final MutableLiveData<ArrayList<TextNote>> lTextNotes;
    private final MutableLiveData<ArrayList<ImageNote>> lImageNotes;
    private final MutableLiveData<ArrayList<VoiceNote>> lVoiceNotes;
    private final MutableLiveData<ArrayList<DrawNote>> lDrawNotes;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModelInici";

    //Constructor
    public PantallaViewModel(){
        lNotes = new MutableLiveData<>();
        lNotes.setValue(new ArrayList<Note>());
        lTextNotes = new MutableLiveData<>();
        lTextNotes.setValue(new ArrayList<TextNote>());
        lImageNotes = new MutableLiveData<>();
        lImageNotes.setValue(new ArrayList<ImageNote>());
        lVoiceNotes = new MutableLiveData<>();
        lVoiceNotes.setValue(new ArrayList<VoiceNote>());
        lDrawNotes = new MutableLiveData<>();
        lDrawNotes.setValue(new ArrayList<DrawNote>());
        mToast = new MutableLiveData<>();
        DatabaseAdapter da= new DatabaseAdapter(this);
        //da.getCollectionNotes();
    }

    public MutableLiveData<ArrayList<Note>> getlNotes() {
        return lNotes;
    }

    public MutableLiveData<String> getmToast() {
        return mToast;
    }

    public MutableLiveData<ArrayList<TextNote>> getlTextNotes() { return lTextNotes; }

    public MutableLiveData<ArrayList<ImageNote>> getlImageNotes() { return lImageNotes; }

    public MutableLiveData<ArrayList<VoiceNote>> getlVoiceNotes() { return lVoiceNotes; }

    public MutableLiveData<ArrayList<DrawNote>> getlDrawNotes() { return lDrawNotes; }

    //@Override
    /*public void setCollection(ArrayList<Note> notes) {this.lNotes.setValue(notes);}*/

    @Override
    public void setCollectionVoice(ArrayList<VoiceNote> voiceNotes) { this.lVoiceNotes.setValue(voiceNotes); }

    @Override
    public void setCollectionImatge(ArrayList<ImageNote> imageNotes) { this.lImageNotes.setValue(imageNotes);}

    @Override
    public void setCollectionText(ArrayList<TextNote> textNotes) { this.lTextNotes.setValue(textNotes);}

    @Override
    public void setCollectionDraw(ArrayList<DrawNote> drawNotes) { this.lDrawNotes.setValue(drawNotes);}

    @Override
    public void setToast(String s) { this.mToast.setValue(s); }
}
