package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;

import java.io.IOException;
import java.util.ArrayList;

public class BilbiotecaActivity extends AppCompatActivity {

    ListView listSong;
    MediaPlayer mediaPlayer;

    int[] CancionesBiblioteca;
    ArrayList<Song> lista;
    //ArrayList<Song> listaBusqueda;
    SongAdapter songAdapter;
    ArrayList<artistas> ListaArtistas;
    ArrayList<String> Generos;

    //Reproductor
    Button play;
    Button skip_prev;
    Button skip_next;
    ImageView AlbumImg;
    TextView Info;
    MediaMetadataRetriever mmr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilbioteca);


        //Reproductor
        play = findViewById(R.id.play);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        skip_next = findViewById(R.id.skip_next);
        skip_prev = findViewById(R.id.skip_prev);
        AlbumImg = findViewById(R.id.AlbumImg);
        Info = findViewById(R.id.InfoSong);


        int Du = (int) getIntent().getExtras().getSerializable("Dur");
        int CancionActual = (int) getIntent().getExtras().getSerializable("Cancion");
        CancionActual(CancionActual);

        listSong = findViewById(R.id.list_biblioteca);
        CancionesBiblioteca = (int[]) getIntent().getExtras().getSerializable("Canciones");
        ListaArtistas = (ArrayList<artistas>) getIntent().getExtras().getSerializable("Artistas");
        Generos = (ArrayList<String>) getIntent().getExtras().getSerializable("Generos");
        lista = new ArrayList<>();
        ObtenCanciones();
    }

    public void CancionActual(int Uri){
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

    public void ObtenCanciones(){

        for(int i = 0; i < CancionesBiblioteca.length; i++) {

            final AssetFileDescriptor afd2 = getResources().openRawResourceFd(CancionesBiblioteca[i]);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());

            //OBTEN EL METADATA ALBUM
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String Name  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String albumName2 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String Gen = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            byte[] imgAlbum = mmr.getEmbeddedPicture();
            Song song = new Song(imgAlbum, Name, artist, CancionesBiblioteca[i], albumName2);
            lista.add(song);
            songAdapter = new SongAdapter(BilbiotecaActivity.this, R.layout.song_list, lista);
            listSong.setAdapter(songAdapter);
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


    public void openHome(View view) {

        finish();
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

    public void verArtistas(View view) {

        listSong.setAdapter(null);
        AdapterList AdapterList = new AdapterList(BilbiotecaActivity.this, R.layout.song_list, ListaArtistas);
        listSong.setAdapter(AdapterList);

    }

    public void AlbumesBiblio(View view) {
        listSong.setAdapter(null);
        AdapterGenList AdapterList = new AdapterGenList(BilbiotecaActivity.this, R.layout.song_list, Generos);
        listSong.setAdapter(AdapterList);
    }
}