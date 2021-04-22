package com.example.t_note.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_note.R;

import java.util.ArrayList;

public class CustomNoteAdapter extends RecyclerView.Adapter<CustomNoteAdapter.ViewHolder> {

    private final ArrayList<Note> localDataSet;
    private final Context parentContext;
    private final playerInterface listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_text, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        holder.getTextView().setText(
                localDataSet.get(position).getTittle());
    }

    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        }
        return 0;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titol,text;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            titol = view.findViewById(R.id.titol);
            text = view.findViewById(R.id.text);
        }

        public TextView getTextView(){
            return titol;
        }

        public TextView getText(){
            return text;
        }
    }

    public interface playerInterface{
        void Prueba();
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomNoteAdapter(Context current, ArrayList<Note> dataSet, playerInterface listener) {
        parentContext = current;
        localDataSet = dataSet;
        this.listener = listener;
    }


}
