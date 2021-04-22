package com.example.t_note.Model;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.t_note.PantallaActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class DatabaseAdapter {

    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private String userID;
    private DatabaseReference reference;
    private String userName;

    public static vmInterfaceNotes listener;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterfaceNotes listener){
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public interface vmInterfaceNotes{
        void setCollection(ArrayList<Note> notes);
        //void setCollectionVoice(ArrayList<VoiceNote> voiceNotes);
        //void setCollectionImatge(ArrayList<ImageNote> imageNotes);
        //void setCollectionText(ArrayList<TextNote> textNotes);
        //void setCollectionDraw(ArrayList<DrawNote> drawNotes);
        //void setToast(String s);
    }

    public void initFirebase(){
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users u = snapshot.getValue(Users.class);
                if(u != null){
                    userName = u.getName();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,"No s'ha pogut carregar l'usuari");
            }
        });
    }

    public void getCollectionNotes(String nameUser){
        //Llegim totes les TextNotes de la base de dades
        /*
        ArrayList<Note>retrieved_ac = new ArrayList<Note>() ;

        Log.d(TAG,"updateTextNotes");
        DatabaseAdapter.db.collection("TextNotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(nameUser)){
                                    retrieved_ac.add(new TextNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user"), document.getString("text")));
                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Llegim totes les VoiceNotes de la base de dades
        Log.d(TAG,"updateVoiceNotes");
        DatabaseAdapter.db.collection("VoiceNotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(nameUser)){
                                    retrieved_ac.add(new VoiceNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user")));
                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Llegim totes les ImageNotes de la base de dades
        Log.d(TAG,"updateImageNotes");
        DatabaseAdapter.db.collection("ImageNotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(nameUser)){
                                    retrieved_ac.add(new ImageNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user")));
                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Llegim totes les DrawNotes de la base de dades
        Log.d(TAG,"updateDrawNotes");
        DatabaseAdapter.db.collection("DrawNotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(nameUser)){
                                    retrieved_ac.add(new DrawNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user")));
                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        listener.setCollection(retrieved_ac);
        */
    }

    public void saveUserToBase (String name, String email, String password) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("name", name);
        note.put("email", email);
        note.put("password", password);

        Log.d(TAG, "saveUser");
        // Add a new document with a generated ID
        db.collection("Users")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void changePassword (String name, String email, String password) {
        //Cercar ususari
        DatabaseAdapter.db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("name").equals(name)){
                                    String path = document.getId();
                                    db.collection("Users").document(path)
                                            .update("password",password);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void saveTextNoteToBase (String tittle, Date dataCreacio, String user, String text) {

        Map<String, Object> note = new HashMap<>();
        note.put("tittle", tittle);
        note.put("dataCreacio", dataCreacio);
        note.put("user", user);
        note.put("text", text);

        Log.d(TAG, "saveTextNote");
        // Add a new document with a generated ID
        db.collection("TextNotes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void saveImageNoteToBase (String tittle, Date dataCreacio, String user) {

        Map<String, Object> note = new HashMap<>();
        note.put("tittle", tittle);
        note.put("dataCreacio", dataCreacio);
        note.put("user", user);

        Log.d(TAG, "saveImageNote");
        // Add a new document with a generated ID
        db.collection("ImageNotes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void saveVoiceNoteToBase (String tittle, Date dataCreacio, String user) {

        Map<String, Object> note = new HashMap<>();
        note.put("tittle", tittle);
        note.put("dataCreacio", dataCreacio);
        note.put("user", user);

        Log.d(TAG, "saveVoiceNote");
        // Add a new document with a generated ID
        db.collection("VoiceNotes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void saveDrawNoteToBase (String tittle, Date dataCreacio, String user) {

        Map<String, Object> note = new HashMap<>();
        note.put("tittle", tittle);
        note.put("dataCreacio", dataCreacio);
        note.put("user", user);

        Log.d(TAG, "saveDrawNote");
        // Add a new document with a generated ID
        db.collection("DrawNotes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
