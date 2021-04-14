package com.example.t_note.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NotesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Note>> lNotes;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModelNote";

    //Constructor
    public NotesViewModel(){
        lNotes = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        //DatabaseAdapter da= new DatabaseAdapter(this);
        //da.getCollection();
    }

    public MutableLiveData<ArrayList<Note>> getlNotes() {
        return lNotes;
    }

    public Note getNote(int idx){
        return lNotes.getValue().get(idx);
    }

    public void addNote(){

    }


    public MutableLiveData<String> getmToast() {
        return mToast;
    }
}
