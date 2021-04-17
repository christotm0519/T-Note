package com.example.t_note.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChangeViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceUsers{

    private final MutableLiveData<ArrayList<Users>> lUsuaris;
    private final MutableLiveData<String> mToast;
    private String userName;
    public static final String TAG = "ViewModelInici";

    //Constructor
    public ChangeViewModel(String user){
        lUsuaris = new MutableLiveData<>();
        lUsuaris.setValue(new ArrayList<Users>());
        mToast = new MutableLiveData<>();
        userName = user;
        DatabaseAdapter da= new DatabaseAdapter(this);
        da.getCollectionUsers();
    }

    public MutableLiveData<ArrayList<Users>> getlUsuaris() { return lUsuaris; }

    public MutableLiveData<String> getmToast() { return mToast; }

    public String getUserName() { return userName; }

    @Override
    public void setCollection(ArrayList<Users> users) { this.lUsuaris.setValue(users); }

    @Override
    public void setToast(String s) { this.mToast.setValue(s); }
}
