package com.example.t_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.t_note.Model.ImageNote;
import com.example.t_note.Model.Note;
import com.example.t_note.Model.VoiceNote;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GrabadoraActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    ImageButton saveButton = null;
    private EditText titol;

    private boolean edit=false;
    private Bitmap audio;

    ImageButton recordButton = null;
    private MediaRecorder recorder = null;

    ImageButton playButton = null;
    private MediaPlayer   player = null;

    Chronometer crono;

    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {

        recorder.stop();
        recorder.release();
        recorder = null;

    }

    class RecordButton extends androidx.appcompat.widget.AppCompatButton {
        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
        }
    }

    class PlayButton extends androidx.appcompat.widget.AppCompatButton {
        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_grabadora);
        crono = (Chronometer) findViewById(R.id.cronometre);

        fileName = getExternalCacheDir().getAbsolutePath();
        fileName = fileName+ File.separator + "/audiorecordtest.3gp";
        titol = (EditText) findViewById(R.id.titolAudio);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        //saveButton = (ImageButton) findViewById(R.id.saveButton);

        recordButton = (ImageButton) findViewById(R.id.btn_recorder);

        playButton = (ImageButton) findViewById(R.id.stopBoton);
        playButton.setVisibility(View.INVISIBLE);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recorder == null){
                    playButton.setVisibility(View.INVISIBLE);
                    crono.start();
                    startRecording();
                    recordButton.setImageResource(R.drawable.rec);
                } else if(recorder!=null){
                    stopRecording();
                    crono.stop();
                    crono.setBase(SystemClock.elapsedRealtime());
                    playButton.setVisibility(View.VISIBLE);
                    recordButton.setImageResource(R.drawable.mic);
                }
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player == null){
                    startPlaying();
                    crono.start();
                    playButton.setImageResource(R.drawable.pause);
                } else if(player!=null){
                    stopPlaying();
                    crono.stop();
                    crono.setBase(SystemClock.elapsedRealtime());
                    playButton.setImageResource(R.drawable.play);
                }
            }
        });

       /* saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotopantalla();
            }
        });*/
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    /*private void gotopantalla() {

        Bundle bundle = getIntent().getExtras();
        Intent intent = new Intent( this, PantallaActivity.class);
        intent.putExtra("edit",edit);
        if(bundle!=null){
            List<Note> listAdapterNote= (List<Note>) bundle.getSerializable("list");
            intent.putExtra("list", (Serializable) listAdapterNote);
            if(edit){
                intent.putExtra("position", (Integer) bundle.get("position"));
            }
        }
        //TextNote imageNote = new TextNote("m",new Date(),"a");
        byte[] byteArray = null;
        if(audio!=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            audio.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }

        VoiceNote imageNote = new VoiceNote(titol.getText().toString(), new Date(),byteArray);
        intent.putExtra("NewNote",imageNote);
        startActivity(intent);
    }*/
}