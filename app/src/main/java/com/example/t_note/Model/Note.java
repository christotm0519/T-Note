package com.example.t_note.Model;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String tittle;
    private Date dataCreacio;

    public Note(String tittle, Date dataCreacio) {
        this.tittle = tittle;
        this.dataCreacio = dataCreacio;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }
}
