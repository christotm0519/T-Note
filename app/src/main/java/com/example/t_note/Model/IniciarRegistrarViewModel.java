package com.example.t_note.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public class IniciarRegistrarViewModel extends ViewModel{

    private final MutableLiveData<ArrayList<Users>> lUsuaris;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModelInici";

    //Constructor
    public IniciarRegistrarViewModel(){
        lUsuaris = new MutableLiveData<>();
        mToast = new MutableLiveData<>();
        //DatabaseAdapter da= new DatabaseAdapter(this);
        //da.getCollection();
    }

    public MutableLiveData<ArrayList<Users>> getlUsuaris() {
        return lUsuaris;
    }

    public Users getUser(int idx){
        return lUsuaris.getValue().get(idx);
    }

    public void addUser(){

    }


    public MutableLiveData<String> getmToast() {
        return mToast;
    }
}
