package com.example.t_note.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChangeViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceUsers{

    private final MutableLiveData<ArrayList<Users>> lUsuaris;
    private final MutableLiveData<String> mToast;
    public static final String TAG = "ViewModelInici";

    //Constructor
    public ChangeViewModel(){
        lUsuaris = new MutableLiveData<>();
        lUsuaris.setValue(new ArrayList<Users>());
        mToast = new MutableLiveData<>();
        DatabaseAdapter da= new DatabaseAdapter(this);
        da.getCollectionUsers();
    }

    public MutableLiveData<ArrayList<Users>> getlUsuaris() { return lUsuaris; }

    public MutableLiveData<String> getmToast() { return mToast; }


    public Users getUser(String name){
        if(lUsuaris.getValue().isEmpty()){
            return null;
        }
        ArrayList<Users> usuaris = lUsuaris.getValue();
        for (int i=0; i < usuaris.size(); i++){
            if  (usuaris.get(i).getName().equals(name)){
                return usuaris.get(i);
            }
        }
        return null;
    }

    public boolean canChangePassword(String userName, String password){
        Users u = getUser(userName);
        if (u != null){
            if(u.getPassword().equals(password)) {
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public void changePassword(String userName,String newPassword){
        Users u = getUser(userName);
        u.changePasswordUser(newPassword);
    }

    @Override
    public void setCollection(ArrayList<Users> users) { this.lUsuaris.setValue(users); }

    @Override
    public void setToast(String s) { this.mToast.setValue(s); }
}
