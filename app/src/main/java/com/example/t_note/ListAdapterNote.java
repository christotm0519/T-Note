package com.example.t_note;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_note.Model.ImageNote;
import com.example.t_note.Model.Note;
import com.example.t_note.Model.TextNote;

import java.sql.SQLOutput;
import java.util.ArrayList;
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
    public Note getItem(int pos){
        return  data.get(pos);
    }

    public void add(Note element) {
        data.add(element);
        notifyDataSetChanged();
    }

    public List<Note> getdata() {
        return data;
    }
    public void replace(int pos,Note note){
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
        TextView titol;
        ImageView imatge;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            titol=itemView.findViewById(R.id.titol_imatge);
            imatge=itemView.findViewById(R.id.foto_card);
        }
        void bindData(final ImageNote nota){
            titol.setText(nota.getTittle());
            byte[] byteArray = nota.getImatge();
            if(byteArray!=null){
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imatge.setImageBitmap(bmp);
            }


        }
    }

    /*
    private final ArrayList<Note> localDataSet;
    private final Context parentContext;
    private final playerInterface listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final LinearLayout audioLayout;
        private final ImageButton playButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.textView);
            audioLayout = view.findViewById(R.id.audio_layout);
            playButton = view.findViewById(R.id.play_button);
        }

        public TextView getTextView() {
            return textView;
        }

        public LinearLayout getLayout() {
            return audioLayout;
        }

        public ImageButton getPlayButton() {return playButton;}
    }

    public ListAdapterNote(Context current, ArrayList<Note> dataSet, playerInterface listener) {
        parentContext = current;
        localDataSet = dataSet;
        this.listener = listener;

    }

    public interface playerInterface{
        void startPlaying(int fileName);
    }
    
     */
}
