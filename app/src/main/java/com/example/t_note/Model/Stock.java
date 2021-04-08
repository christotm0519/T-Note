package com.example.t_note.Model;

import java.util.ArrayList;

public class Stock {
    private ArrayList<Note> magatzem;

    public Stock(){
        this.magatzem = new ArrayList<>();
    }
    public Stock(ArrayList<Note> magatzem) {
        this.magatzem = magatzem;
    }
}
