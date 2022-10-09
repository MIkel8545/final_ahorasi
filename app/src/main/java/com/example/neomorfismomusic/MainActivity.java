package com.example.neomorfismomusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    String nombre;
    static MediaPlayer mediaPlayer;
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
    RecyclerView recyclerviewAlbum;
    ArrayList<artistas> ListArtis;
    ArrayList<String> Generos;
    ArrayList<String> NameAlbum;
    ArrayList<Album> Albumes;
    static ArrayList<Song> lista;

    //List<song> ListaCanciones = new ArrayList<song>();
    FirebaseFirestore firebaseFirestore;

    TextView prueba2;
    String [] img = new String[3];
    String [] text = new String[3];
    int cont = 0;
    int contGen=0;
    int contSong = 0;
    int contArt = 0;
    String Gen;
    int NumeroCanciones = 1;
    public static int[] idCanciones;
    int CancionActual = 0;

    AdaptadorArtist AdaptadorArtist;
    AdapterGenero AdapterGenero;
    AdapterAlbum AdapterAlbum;
    String Url;
    MediaMetadataRetriever mmr ;
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
        recyclerviewAlbum = findViewById(R.id.recyclerviewAlbum);


        ArtistaTres = findViewById(R.id.ArtistaTres);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        firebaseFirestore = FirebaseFirestore.getInstance();
        ObtenerSaludo();
        ListArtis = new ArrayList<>();
        Generos = new ArrayList<>();
        Albumes  = new ArrayList<>();
        NameAlbum = new ArrayList<>();

        //StartMusic();
        //Duracion();

        //Cargar canciones
        idCanciones = new int[]{R.raw.axe, R.raw.assassin, R.raw.baby, R.raw.beat, R.raw.billie,
                R.raw.creation, R.raw.cydonia, R.raw.delusion, R.raw.emptiness, R.raw.everytime, R.raw.explosia,
                R.raw.fall, R.raw.fire, R.raw.glorious, R.raw.guilt, R.raw.habit, R.raw.healer, R.raw.hole,
                R.raw.hoodoo, R.raw.inhaler, R.raw.invincible, R.raw.kala, R.raw.kala, R.raw.lady, R.raw.master,
                R.raw.milk, R.raw.mine, R.raw.moon, R.raw.nature, R.raw.night, R.raw.number, R.raw.obsolescence, R.raw.poem,
                R.raw.politics, R.raw.prelude, R.raw.pretty, R.raw.problematique, R.raw.providence, R.raw.sauvage,
                R.raw.starlight, R.raw.stepson, R.raw.take_a_bow, R.raw.thriller, R.raw.wanna, R.raw.winter, R.raw.woods};
        //Cargar el miniReproductor
        CargaMusica(idCanciones[CancionActual]);
        ObtenDatos();

    }

    private void ObtenDatos() {

        //Cargar Generos
        for (int i = 0; i < idCanciones.length; i++) {
            final AssetFileDescriptor afd2 = getResources().openRawResourceFd(idCanciones[i]);
            mmr = new MediaMetadataRetriever();
            mmr.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
            Gen = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            //ALBUM
            String albumName2 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            //IMAGEN
            byte[] img2 = mmr.getEmbeddedPicture();
            //AÑO
            String year = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
            //ARTISTA
            String Artista = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String Name  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            if (Gen.contains("/")) {
                String[] split = Gen.split("/");
                for (int y = 0; y < split.length; y++) {
                    if (!Generos.contains(split[y])) {
                        Generos.add(contGen, split[y]);
                        contGen++;
                    }
                }
            } else {
                if (!Generos.contains(Gen)) {
                    Generos.add(contGen, Gen);
                    contGen++;
                }
            }
            if (!NameAlbum.contains(albumName2)) {
                Album album = new Album(img2, albumName2, Artista, NumeroCanciones, year);
                Albumes.add(cont, album);
                NameAlbum.add(albumName2);
                cont++;
            }
        }


        /// GENEROOOOOOOSSSSSSSS
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewGen.setLayoutManager(layoutManager2);
        recyclerViewGen.setItemAnimator(new DefaultItemAnimator());
        AdapterGenero = new AdapterGenero(MainActivity.this, Generos);
        recyclerViewGen.setAdapter(AdapterGenero);

        ///ALBUMENESSS
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewAlbum.setLayoutManager(layoutManager);
        recyclerviewAlbum.setItemAnimator(new DefaultItemAnimator());
        AdapterAlbum = new AdapterAlbum(MainActivity.this, Albumes);
        recyclerviewAlbum.setAdapter(AdapterAlbum);

        ///ARTISTAS
        CargaArtist();
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager3);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        AdaptadorArtist = new AdaptadorArtist(MainActivity.this, ListArtis);
        recyclerView.setAdapter(AdaptadorArtist);

    }
    public void ObtenerSaludo(){

        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        if(hora < 12){
            Saludo.setText("¡Buenos Días!");
        }
        if(hora > 12 && hora < 19)
            Saludo.setText("¡Buenas tardes!");
        if(hora >19){
            Saludo.setText("¡Buenas noches!");
        }
    }

    public void Play(View view) {
        ReproducirCancion();
    }
    public void ReproducirCancion(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
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

    public void openBusqueda(View view) {
        Intent i = new Intent(this, BusquedaActivity.class);
        startActivity(i);
    }

    public void openHistorial(View view) {
        Intent i = new Intent(this, HistorialActivity.class);
        startActivity(i);
    }

    public void openBiblioteca(View view) {
        Intent i = new Intent(this, BilbiotecaActivity.class);
        startActivity(i);
    }

    public void CargaArtist(){
        artistas a1 = new artistas(R.drawable.gorija, "Gojira", "Metal");
        artistas a2 = new artistas(R.drawable.foals, "Foals", "Alternative / Rock / Indie Rock/Rock Pop");
        artistas a3 = new artistas(R.drawable.michael, "Michael Jackson", "Pop");
        artistas a4 = new artistas(R.drawable.muse, "Muse", "Alternative");
        ListArtis.add(0, a1);
        ListArtis.add(1, a2);
        ListArtis.add(2, a3);
        ListArtis.add(3, a4);
    }

    public void Next_song(View view) {

        if(CancionActual > idCanciones.length-1){
                CancionActual = 0;
                mediaPlayer.reset();
                CargaMusica(idCanciones[CancionActual]);
                ReproducirCancion();
        }
        else {
             CancionActual++;
             mediaPlayer.reset();
             CargaMusica(idCanciones[CancionActual]);
             ReproducirCancion();
        }
    }

    public void CargaMusica(int Uri){

        mediaPlayer =  MediaPlayer.create(MainActivity.this, Uri);
        final AssetFileDescriptor afd = getResources().openRawResourceFd(Uri);
        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String cancionName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String art = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        byte[] img = mmr.getEmbeddedPicture();
        long dur = Long.parseLong(duration);
        String seconds = String.valueOf((dur % 60000) / 1000);
        String minutes = String.valueOf(dur / 60000);
        String out = minutes + ":" + seconds;
        if (seconds.length() == 1) {
            Log.d("Duracion", "0" + minutes + ":0" + seconds);
        } else {
            Log.d("duracion", "0" + minutes + ":" + seconds);
        }

        try {
            mmr.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //AlbumImg.setBackgroundResource(picId);
        // Glide.with(MainActivity.this).load(document.getString("ImgUrl")).into(AlbumImg);

        AlbumImg.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
        Info.setText(cancionName + "\n" + art);
    }

    public void Prev_song(View view) {

        Log.d("INDICEEEEEE CANCION ACTUAL", String.valueOf(CancionActual));
        if(CancionActual > 0){
            CancionActual--;
            mediaPlayer.reset();
            CargaMusica(idCanciones[CancionActual]);
            ReproducirCancion();
            Log.d("Reproduciendo", String.valueOf(CancionActual) + idCanciones[CancionActual]);
        }
        else {
            CancionActual = 0;
            mediaPlayer.reset();
            CargaMusica(idCanciones[CancionActual]);
            ReproducirCancion();
            Log.d("Reproduciendo", String.valueOf(CancionActual) + idCanciones[CancionActual]);
        }
    }
}