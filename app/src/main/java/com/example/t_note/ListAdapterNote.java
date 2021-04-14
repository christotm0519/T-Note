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

import com.example.t_note.Model.ImageNote;
import com.example.t_note.Model.Note;
import com.example.t_note.Model.TextNote;

import java.io.Serializable;
import java.util.List;

public class ListAdapterNote extends RecyclerView.Adapter implements View.OnLongClickListener, View.OnClickListener {
    private List<Note> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private String text,titol;
    private int position;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public ListAdapterNote(List<Note> llista, Context context){
        this.layoutInflater = LayoutInflater.from(context);
        this.context=context;
        this.data=llista;
    }

    @Override
    public int getItemViewType(int position) {
        Note element= data.get(position);
        if(element instanceof TextNote){
            return 0;

        }
        else if(element instanceof ImageNote){
            return 1;
        }
        else{
            return 2;
        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){

        if(ViewType==0){
            View view= layoutInflater.inflate(R.layout.card_text,null);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return new ListAdapterNote.ViewHolder0(view);
        }
        else if(ViewType==1){
            View view= layoutInflater.inflate(R.layout.card_imatge,null);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            return new ListAdapterNote.ViewHolder1(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Note element= data.get(position);
        if(element instanceof TextNote) {
            ViewHolder0 holder0 = (ViewHolder0) holder;
            holder0.bindData((TextNote) data.get(position));

        }
        else if(element instanceof ImageNote){
            ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.bindData((ImageNote) data.get(position));

        }




    }


        public void setData(List<Note> llista){
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
        return (TextNote) data.get(pos);
    }

    public void add(TextNote element) {
        data.add(element);
        notifyDataSetChanged();
    }

    public List<Note> getdata() {
        return data;
    }
    public void replace(int pos,TextNote note){
        data.set(pos,note);
        notifyItemChanged(pos);
    }


    public class ViewHolder0 extends RecyclerView.ViewHolder
    {
        TextView titol,text;

        public ViewHolder0(@NonNull View itemView) {
            super(itemView);
            titol=itemView.findViewById(R.id.titol);
            text=itemView.findViewById(R.id.text);
        }
        void bindData(final TextNote nota){
            titol.setText(nota.getTittle());
            text.setText(nota.getText());
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder
    {
        TextView titol,text;
        ImageNote imageNote;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            titol=itemView.findViewById(R.id.titol);
            text=itemView.findViewById(R.id.text);
        }
        void bindData(final ImageNote nota){
            titol.setText(nota.getTittle());
            imageNote.setImatge(nota.getImatge());
        }
    }
}
