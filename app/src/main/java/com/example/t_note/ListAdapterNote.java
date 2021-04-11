package com.example.t_note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_note.Model.Note;
import com.example.t_note.Model.TextNote;

import java.util.List;

public class ListAdapterNote extends RecyclerView.Adapter<ListAdapterNote.ViewHolder> {
    private List<TextNote> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListAdapterNote(List<TextNote> llista, Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.context=context;
        this.data=llista;
    }
    @Override
    public int getItemCount(){
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view= layoutInflater.inflate(R.layout.card_text,null);
        return new ListAdapterNote.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }



    public void setData(List<TextNote> llista){
        data=llista;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView titol,text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titol=itemView.findViewById(R.id.titol);
            text=itemView.findViewById(R.id.text);
        }
        void bindData(final TextNote nota){
            titol.setText(nota.getTittle());
            text.setText(nota.getText());
        }
    }
}
