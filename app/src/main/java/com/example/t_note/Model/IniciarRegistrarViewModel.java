package com.example.t_note.Model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class IniciarRegistrarViewModel extends ViewModel implements DatabaseAdapter.vmInterfaceUsers{

    private final MutableLiveData<ArrayList<Users>> lUsuaris;
    private final MutableLiveData<String> mToast;

    public static final String TAG = "ViewModelInici";

    //Constructor
    public IniciarRegistrarViewModel(){
        lUsuaris = new MutableLiveData<>();
        lUsuaris.setValue(new ArrayList<Users>());
        mToast = new MutableLiveData<>();
        DatabaseAdapter da= new DatabaseAdapter(this);
        da.getCollectionUsers();
    }

    public MutableLiveData<ArrayList<Users>> getlUsuaris() {
        return lUsuaris;
    }

    public Users getUser(int idx){
        return lUsuaris.getValue().get(idx);
    }
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
    public boolean findNameUser(String name){
        if(lUsuaris.getValue().isEmpty()){
            return false;
        }
        ArrayList<Users> usuaris = lUsuaris.getValue();
        for (int i=0; i < usuaris.size(); i++){
            if  (usuaris.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public boolean findEmailUser(String email){
        if(lUsuaris.getValue().isEmpty()){
            return false;
        }
        ArrayList<Users> usuaris = lUsuaris.getValue();
        for (int i=0; i < usuaris.size(); i++){
            if  (usuaris.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean canLog(String name, String password){
        Users u = getUser(name);
        if(u != null){
            if(u.getPassword().equals(password)){
                return true;
            }else{
                return false;
            }
        };
        return false;
    }

    public boolean canRegistrar(String name, String email){
        if(lUsuaris.getValue().isEmpty()){
            return true;
        }
        if(!findNameUser(name)){
            if(!findEmailUser(email)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    public boolean registrar(String name, String email, String password){
        if(canRegistrar(name,email)){
            addUser(name,email,password);
            return true;
        }
        return false;
    }
    public void addUser(String name, String email, String password){
        Users u = new Users(name,email,password);
        lUsuaris.getValue().add(u);
        //Inform observer
        lUsuaris.setValue(lUsuaris.getValue());
        u.saveUser();
    }

    public MutableLiveData<String> getmToast() {
        return mToast;
    }

    @Override
    public void setCollection(ArrayList<Users> users) {
        this.lUsuaris.setValue(users);
    }

    @Override
    public void setToast(String s) {
        this.mToast.setValue(s);
    }
}
