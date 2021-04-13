package com.example.t_note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_note.Model.Note;
import com.example.t_note.Model.TextNote;

import java.io.Serializable;
import java.util.List;

public class ListAdapterNote extends RecyclerView.Adapter<ListAdapterNote.ViewHolder> implements View.OnLongClickListener, View.OnClickListener {
    private List<TextNote> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private String text,titol;
    private int position;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public ListAdapterNote(List<TextNote> llista, Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.context=context;
        this.data=llista;
    }
    @Override
    public int getItemCount(){
        return data.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View view= layoutInflater.inflate(R.layout.card_text,null);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ListAdapterNote.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }



    public void setData(List<TextNote> llista){
        data=llista;
    }

    @Override
    public boolean onLongClick(View v) {
        if(onLongClickListener!=null){
            onLongClickListener.onLongClick(v);

        }
    return true;
    }

    @Override
    public void onClick(View v) {

        if(onClickListener!=null){
            onClickListener.onClick(v);

        }


    }
    public void remove(int pos){
        data.remove(pos);
        notifyItemRemoved(pos);
    }
    public TextNote getItem(int pos){
        return(data.get(pos));
    }

    public void add(TextNote element) {
        data.add(element);
        notifyDataSetChanged();
    }

    public List<TextNote> getdata() {
        return data;
    }
    public void replace(int pos,TextNote note){
        data.set(pos,note);
        notifyItemChanged(pos);
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
