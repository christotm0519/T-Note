package com.example.t_note.Model;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String tittle;
    private Date dataCreació;

    public Note(String tittle, Date dataCreació) {
        this.tittle = tittle;
        this.dataCreació = dataCreació;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Date getDataCreació() {
        return dataCreació;
    }

    public void setDataCreació(Date dataCreació) {
        this.dataCreació = dataCreació;
    }
}
