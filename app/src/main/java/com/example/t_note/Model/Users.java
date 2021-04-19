package com.example.t_note.Model;

import android.util.Log;

public class Users {
    private String name;
    private String email;
    private String password;
    private final DatabaseAdapter adapter = DatabaseAdapter.databaseAdapter;

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveUser(){
        Log.d("saveUser", "saveUser-> saveUserToBase");
        adapter.saveUserToBase(this.name,this.email,this.password);
    }

    public void changePasswordUser(String password){
        setPassword(password);
        adapter.changePassword(name,email,password);
    }
}
