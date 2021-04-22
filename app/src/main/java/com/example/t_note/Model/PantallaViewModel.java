package com.example.t_note.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PantallaViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceNotes, Parcelable, Serializable {

    private int mData;

    private final MutableLiveData<ArrayList<Note>> lNotes;

    public static final String TAG = "ViewModelInici";

    private String userName;

    //Constructor
    public PantallaViewModel(){
        lNotes = new MutableLiveData<>();
        lNotes.setValue(new ArrayList<Note>());
    }

    public void setUserName(String name){
        this.userName = name;
        DatabaseAdapter da= new DatabaseAdapter(this);
        da.getCollectionNotes(userName);
    }

    public String getUserName(){
        return this.userName;
    }

    public boolean getNull(){
        if(lNotes == null){
            return true;
        }
        return false;
    }
    public MutableLiveData<ArrayList<Note>> getlNotes() {
        return lNotes;
    }

    public void addTextNote(String tittle, Date dataCreacio, String user, String text){

        System.out.println("HERERERERERERERERERERERERERERERERERERERERERERRERERERERERERERERERER");
        if(lNotes == null){
            System.out.println("EL LNOTES ESTA NULL");
        }

        TextNote newNote = new TextNote(tittle,dataCreacio,user,text);
        lNotes.getValue().add(newNote);
        //Inform observer
        System.out.println("ANEEEEEEEEEEEEEEEEEEMMMMMMMMMM AAAA NOTIFIIIIIIIIIIIIIICAAAAAAAAAAAAAAAAARRRRRRRRRRRR");
        lNotes.setValue(lNotes.getValue());
        //Guardar a la base de dades
        //newNote.saveNote();


    }

    @Override
    public void setCollection(ArrayList<Note> notes) {this.lNotes.setValue(notes);}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeTypedList(lNotes);
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
            return new PantallaViewModel(in, this.lNotes);
        }

        @Override
        public PantallaViewModel[] newArray(int size) {
            return new PantallaViewModel[size];
        }
    };

}
