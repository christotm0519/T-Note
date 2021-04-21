package com.example.t_note.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Date;

public class PantallaViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceNotes, Parcelable {

    private int mData;

    private final MutableLiveData<ArrayList<Note>> lNotes;

    private String userName = "YATUSABE";

    public static final String TAG = "ViewModelInici";

    //Constructor
    public PantallaViewModel(){
        lNotes = new MutableLiveData<>();
        lNotes.setValue(new ArrayList<Note>());
        //DatabaseAdapter da= new DatabaseAdapter(this);
        //da.getCollectionNotes();
    }


    public MutableLiveData<ArrayList<Note>> getlNotes() {
        return lNotes;
    }


    public void addTextNote(String tittle, Date dataCreacio, String user, String text){
        TextNote newNote = new TextNote(tittle,dataCreacio,user,text);
        lNotes.getValue().add(newNote);
        //Inform observer
        lNotes.setValue(lNotes.getValue());
        //Guardar a la base de dades
        newNote.saveNote();
    }

    public void setUser(String user){
        userName = user;
        DatabaseAdapter da= new DatabaseAdapter(this);
        da.getCollectionNotes(userName);
    }

    public String getUserName(){
        return userName;
    }
    @Override
    public void setCollection(ArrayList<Note> notes) {this.lNotes.setValue(notes);}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
    }

    protected PantallaViewModel(Parcel in, MutableLiveData<ArrayList<Note>> lNotes) {
        mData = in.readInt();
        this.lNotes = lNotes;
    }

    public static final Creator<PantallaViewModel> CREATOR = new Creator<PantallaViewModel>() {
        private  MutableLiveData<ArrayList<Note>> lNotes;

        @Override
        public PantallaViewModel createFromParcel(Parcel in) {
            return new PantallaViewModel(in, lNotes);
        }

        @Override
        public PantallaViewModel[] newArray(int size) {
            return new PantallaViewModel[size];
        }
    };
}
