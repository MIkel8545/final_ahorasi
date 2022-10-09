package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class BusquedaActivity extends AppCompatActivity {

    ListView listSong;
    MediaPlayer mediaPlayer;
    Button play;
    EditText editText_Busqueda;
    int[] Canciones;
    ArrayList<Song> lista;
    ArrayList<Song> listaBusqueda;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        listSong = findViewById(R.id.list_busqueda);
        editText_Busqueda = findViewById(R.id.editText_Busqueda);
        Canciones = (int[]) getIntent().getExtras().getSerializable("Canciones");
        lista = new ArrayList<>();
        listaBusqueda = new ArrayList<>();
        ObtenCanciones();

        editText_Busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                listaBusqueda.clear();
                listSong.setAdapter(null);
                if(charSequence != "" || charSequence != " ") {
                    for (int y = 0; y < lista.size(); y++) {
                        if (lista.get(y).getNombre().contains(charSequence)) {
                            Song song = new Song(lista.get(y).getImagen(), lista.get(y).getNombre(), lista.get(y).getArtista(),
                                    lista.get(y).getMp3(), lista.get(y).getAlbum());
                            listaBusqueda.add(song);
                        }
                    }
                   songAdapter = new SongAdapter(BusquedaActivity.this, R.layout.song_list, listaBusqueda);
                    if(listaBusqueda.isEmpty()){
                        byte[] img = new byte[0];
                        Song songVacio = new Song(img,"No se encontraron resultados","", 0, "");
                        listaBusqueda.add(songVacio);
                    }
                    listSong.setAdapter(songAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void ObtenCanciones(){

        for(int i = 0; i < Canciones.length; i++) {

            final AssetFileDescriptor afd2 = getResources().openRawResourceFd(Canciones[i]);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());

            //OBTEN EL METADATA ALBUM
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String Name  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String albumName2 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String Gen = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            byte[] imgAlbum = mmr.getEmbeddedPicture();
            Song song = new Song(imgAlbum, Name, artist, Canciones[i], albumName2);
            lista.add(song);
            songAdapter = new SongAdapter(BusquedaActivity.this, R.layout.song_list, lista);
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
}