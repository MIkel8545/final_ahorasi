package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;

import java.io.IOException;
import java.util.ArrayList;

public class BilbiotecaActivity extends AppCompatActivity {

    ListView listSong;
    MediaPlayer mediaPlayer;
    Button play;
    int[] CancionesBiblioteca;
    ArrayList<Song> lista;
    //ArrayList<Song> listaBusqueda;
    SongAdapter songAdapter;
    ArrayList<artistas> ListaArtistas;
    ArrayList<String> Generos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilbioteca);

        listSong = findViewById(R.id.list_biblioteca);
        CancionesBiblioteca = (int[]) getIntent().getExtras().getSerializable("Canciones");
        ListaArtistas = (ArrayList<artistas>) getIntent().getExtras().getSerializable("Artistas");
        Generos = (ArrayList<String>) getIntent().getExtras().getSerializable("Generos");
        lista = new ArrayList<>();
        ObtenCanciones();
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