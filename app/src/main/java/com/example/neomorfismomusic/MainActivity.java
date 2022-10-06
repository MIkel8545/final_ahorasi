package com.example.neomorfismomusic;

import static com.example.neomorfismomusic.R.id.ArtistaUno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    String nombre;
    MediaPlayer mediaPlayer;
    AssetFileDescriptor fd;
    Button play;
    Button skip_prev;
    Button skip_next;
    ImageView AlbumImg;
    TextView Info;
    TextView NombreArtistaUno;
    TextView NombreArtistaDos;
    TextView NombreArtistaTres;
    ImageView ArtistaDos;
    ImageView ArtistaUno;
    ImageView ArtistaTres;
    List<song> ListaCanciones = new ArrayList<song>();
    FirebaseFirestore firebaseFirestore;

    TextView prueba2;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        skip_next = findViewById(R.id.skip_next);
        skip_prev = findViewById(R.id.skip_prev);
        AlbumImg = findViewById(R.id.AlbumImg);
        Info = findViewById(R.id.InfoSong);
        ArtistaUno = findViewById(R.id.ArtistaUno);
        NombreArtistaUno = findViewById(R.id.NombreArtistaUno);
        ArtistaDos = findViewById(R.id.ArtistaDos);
        NombreArtistaDos = findViewById( R.id.NombreArtistaDos);
        NombreArtistaTres = findViewById(R.id.NombreArtistaTres);

        ArtistaTres = findViewById(R.id.ArtistaTres);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);


       int picId = getResources().getIdentifier("rihanna", "drawable", getApplicationContext().getPackageName());
       /* song Umbrella = new song("Umbrella","Rihanna","desconocido","Pop",picId, "umbrella.mp3");
        ListaCanciones.add(Umbrella);
        startSound( ListaCanciones.get(0).getUrSong());

        AlbumImg.setBackgroundResource(ListaCanciones.get(0).getPortada());
        Info.setText(ListaCanciones.get(0).Datos());*/

        firebaseFirestore = FirebaseFirestore.getInstance();
        ObtenDatos();


    }

    private void ObtenDatos(){
        DocumentReference docRef = firebaseFirestore.collection("songs").document("Umbrella");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Resultado: ", "DocumentSnapshot data: " + document.getData());
                        StartMusic(document.getString("Songurl"));
                        //AlbumImg.setBackgroundResource(picId);
                        Glide.with(MainActivity.this).load(document.getString("ImgUrl")).into(AlbumImg);
                        Info.setText(document.getString("nombre") +"\n" + document.getString("artista"));

                    } else {
                        Log.d("Fallo", "No such document");
                    }
                } else {
                    Log.d("Fallo", "get failed with ", task.getException());
                }
            }
        });

        firebaseFirestore.collection("Artistas")
                .limit(3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Artistasss", document.getId() + " => " + document.getData());
                                if(document.getId().equals("Gorija")) {
                                    Glide.with(MainActivity.this).load(document.getString("ImgArtis")).into(ArtistaUno);
                                    NombreArtistaUno.setText(document.getString("Nombre"));
                                    //Log.d("Entrooo","Es igual");
                                }
                                if(document.getId().equals("Marron5")) {
                                    Glide.with(MainActivity.this).load(document.getString("ImgArtis")).into(ArtistaDos);
                                    NombreArtistaDos.setText(document.getString("Nombre"));
                                    //Log.d("Entrooo","Es igual");
                                }
                                if(document.getId().equals("Muse")) {
                                    Glide.with(MainActivity.this).load(document.getString("ImgArtis")).into(ArtistaTres);
                                    NombreArtistaTres.setText(document.getString("Nombre"));
                                    //Log.d("Entrooo","Es igual");
                                }
                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /*private void startSound(String filename) {
        AssetFileDescriptor afd = null;
        try {
            afd = getResources().getAssets().openFd(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new MediaPlayer();
        try {
            assert afd != null;
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void StartMusic(String Uri){
        String url = Uri ;// your URL here
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mediaPlayer.start();
    }

    public void Play(View view) {

        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            play.setBackgroundResource(R.drawable.ic_baseline_pause_24);

        }
        else {
            mediaPlayer.pause();
            play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }



    }
}