package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ArtistaActivity extends AppCompatActivity {

    ListView listSong;
    MediaPlayer mediaPlayer;
    Button play;
    artistas ArtistaActual;
    ArrayList<Song> lista;
    ImageView ImagenArtist;
    int[] CancionesArtistas;
    byte[] imgAlbum;
    byte[] albumArtis;
    ImageView ArtsAlb;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);

        listSong = findViewById(R.id.list_canciones2);
        ImagenArtist = findViewById(R.id.ImagenArtist);
        ArtsAlb = findViewById(R.id.ArtsAlb);
        ArtistaActual = (artistas) getIntent().getExtras().getSerializable("ArtistaDetails");
        lista = new ArrayList<>();
        CancionesArtistas = (int[]) getIntent().getExtras().getSerializable("Canciones");

        ObtenCanciones();
        ImagenArtist.setImageResource(ArtistaActual.getImg());
        ArtsAlb.setImageBitmap(BitmapFactory.decodeByteArray(albumArtis, 0, albumArtis.length));


    }
    public void ObtenCanciones(){

        for(int i = 0; i < CancionesArtistas.length; i++) {

            final AssetFileDescriptor afd2 = getResources().openRawResourceFd(CancionesArtistas[i]);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());

            //OBTEN EL METADATA ALBUM
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String Name  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String albumName2 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            imgAlbum = mmr.getEmbeddedPicture();

            if(ArtistaActual.getNombre().equals(artist)){
                Song song = new Song(imgAlbum, Name, ArtistaActual.getNombre(), CancionesArtistas[i], albumName2);
                lista.add(song);
                albumArtis = imgAlbum;
            }

        }
        SongAdapter songAdapter = new SongAdapter(this, R.layout.song_list, lista);
        listSong.setAdapter(songAdapter);

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
}