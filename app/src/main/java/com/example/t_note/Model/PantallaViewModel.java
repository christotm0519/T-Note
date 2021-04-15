package com.example.t_note.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PantallaViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceNotes {

    private final MutableLiveData<ArrayList<Note>> lNotes;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModelInici";

    //Constructor
    public PantallaViewModel(){
        lNotes = new MutableLiveData<>();
        lNotes.setValue(new ArrayList<Note>());
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

    @Override
    public void setCollection(ArrayList<Note> users) {

    }

    @Override
    public void setToast(String s) {

    }
}
