package com.example.t_note.Model;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public static vmInterfaceUsers listener;
    public static vmInterfaceNotes listener2;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterfaceUsers listener){
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public DatabaseAdapter(vmInterfaceNotes listener){
        this.listener2 = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }

    public interface vmInterfaceUsers{
        void setCollection(ArrayList<Users> users);
        void setToast(String s);
    }

    public interface vmInterfaceNotes{
        //void setCollection(ArrayList<Note> notes);
        void setCollectionVoice(ArrayList<VoiceNote> voiceNotes);
        void setCollectionImatge(ArrayList<ImageNote> imageNotes);
        void setCollectionText(ArrayList<TextNote> textNotes);
        void setCollectionDraw(ArrayList<DrawNote> drawNotes);
        void setToast(String s);
    }

    public void initFirebase(){
        user = mAuth.getCurrentUser();
        /*
        if (user == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                listener.setToast("Authentication successful.");
                                user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                listener.setToast("Authentication failed.");

                            }
                        }
                    });
        }
        else{
            listener.setToast("Authentication with current user.");

        }*/
    }

    public void getCollectionUsers(){
        Log.d(TAG,"updateUsers");
        DatabaseAdapter.db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Users> retrieved_ac = new ArrayList<Users>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_ac.add(new Users( document.getString("name"), document.getString("email"), document.getString("password")));
                            }
                            listener.setCollection(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void getCollectionNotes(String user){
        //Llegim totes les TextNotes de la base de dades
        Log.d(TAG,"updateTextNotes");
        DatabaseAdapter.db.collection("TextNotes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<TextNote> retrieved_ac = new ArrayList<TextNote>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(user)){
                                    retrieved_ac.add(new TextNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user"), document.getString("text")));
                                }
                            }
                            listener2.setCollectionText(retrieved_ac);

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

                            ArrayList<VoiceNote> retrieved_ac = new ArrayList<VoiceNote>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(user)){
                                    retrieved_ac.add(new VoiceNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user")));
                                }
                            }
                            listener2.setCollectionVoice(retrieved_ac);

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

                            ArrayList<ImageNote> retrieved_ac = new ArrayList<ImageNote>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(user)){
                                    retrieved_ac.add(new ImageNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user")));
                                }
                            }
                            listener2.setCollectionImatge(retrieved_ac);

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

                            ArrayList<DrawNote> retrieved_ac = new ArrayList<DrawNote>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (document.getString("user").equals(user)){
                                    retrieved_ac.add(new DrawNote( document.getString("tittle"), document.getDate("dataCreacio"), document.getString("user")));
                                }
                            }
                            listener2.setCollectionDraw(retrieved_ac);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
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
