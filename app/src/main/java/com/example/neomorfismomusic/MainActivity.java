package com.example.neomorfismomusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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
    TextView Saludo;
    RecyclerView recyclerView;
    RecyclerView recyclerViewGen;
    ArrayList<artistas> ListArtis;
    ArrayList<String> Generos;
    //List<song> ListaCanciones = new ArrayList<song>();
    FirebaseFirestore firebaseFirestore;

    TextView prueba2;
    String [] img = new String[3];
    String [] text = new String[3];
    int cont = 0;
    String Gen;

    AdaptadorArtist AdaptadorArtist;
    AdapterGenero AdapterGenero;
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
        NombreArtistaDos = findViewById(R.id.NombreArtistaDos);
        NombreArtistaTres = findViewById(R.id.NombreArtistaTres);
        Saludo = findViewById(R.id.firstBlock);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerViewGen = findViewById(R.id.recyclerviewGen);

        ArtistaTres = findViewById(R.id.ArtistaTres);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        firebaseFirestore = FirebaseFirestore.getInstance();
        ObtenerSaludo();
        ListArtis = new ArrayList<>();
        Generos = new ArrayList<>();
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
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Artistasss", document.getId() + " => " + document.getData());
                                Gen= document.getString("Genero");
                                artistas atist = new artistas(document.getString("ImgArtis"), document.getString("Nombre"),
                                        Gen);

                                ListArtis.add(cont, atist);
                                if(!Generos.contains(Gen)){
                                 Generos.add(cont, document.getString("Genero"));
                                }
                                cont ++;

                                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());

                                AdaptadorArtist = new AdaptadorArtist(MainActivity.this, ListArtis);
                                recyclerView.setAdapter(AdaptadorArtist);

                            }

                            /// GENEROOOOOOOSSSSSSSS
                            LinearLayoutManager layoutManager2= new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            recyclerViewGen.setLayoutManager(layoutManager2);
                            recyclerViewGen.setItemAnimator(new DefaultItemAnimator());
                            AdapterGenero = new AdapterGenero(MainActivity.this, Generos);
                            recyclerViewGen.setAdapter(AdapterGenero);

                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void ObtenerSaludo(){
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        Log.d("HORAAAAAAA", String.valueOf(hora));
        if(hora < 12){
            Saludo.setText("¡Buenos Días!");
        }
        if(hora > 12 && hora < 19)
            Saludo.setText("¡Buenas tardes!");
        if(hora >19){
            Saludo.setText("¡Buenas noches!");
        }
        
    }

    private void StartMusic(String Uri){
        String url = Uri ;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Play(View view) {

        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            Log.d("Duracion", String.valueOf(mediaPlayer.getDuration()));
        }
        else {
            mediaPlayer.pause();
            play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    public void openAlbum(View view) {

        Intent i = new Intent(this, AlbumActivity.class);
        startActivity(i);

    }
    public void openReproductor(View view) {
        Intent i = new Intent(this, ReproductorActivity.class);
        startActivity(i);

    }
}