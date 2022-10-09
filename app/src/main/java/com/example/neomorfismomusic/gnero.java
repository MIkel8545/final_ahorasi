package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class gnero extends AppCompatActivity {

    String GeneroActual;
    int[] CancionesGenero;
    ArrayList<Song> lista;
    ListView listSong;
    TextView TextGe;
    ImageView ArtisGen;
    ArrayList<artistas> ListArtis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gnero);

        GeneroActual = (String) getIntent().getExtras().getSerializable("GeneroDetails");
        CancionesGenero = (int[]) getIntent().getExtras().getSerializable("Canciones");
        ListArtis = (ArrayList<artistas>) getIntent().getExtras().getSerializable("artistas");
        ArtisGen = findViewById(R.id.ArtisGen);
        TextGe = findViewById(R.id.TextGe);
        listSong = findViewById(R.id.list_canciones3);
        lista = new ArrayList<>();
        TextGe.setText(GeneroActual);
        ObtenCanciones();
        BuscaArtistas();

    }

    public void BuscaArtistas(){
        for (int i = 0; i <ListArtis.size(); i++){
            if(ListArtis.get(i).getGenero().contains(GeneroActual))
            {
                ArtisGen.setImageResource(ListArtis.get(i).getImg());
            }
        }
    }

    public void ObtenCanciones(){


        for(int i = 0; i < CancionesGenero.length; i++) {

            final AssetFileDescriptor afd2 = getResources().openRawResourceFd(CancionesGenero[i]);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());

            //OBTEN EL METADATA ALBUM
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String Name  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String albumName2 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String Gen = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
            byte[] imgAlbum = mmr.getEmbeddedPicture();

            if (Gen.contains("/")) {
                String[] split = Gen.split("/");
                for (int y = 0; y < split.length; y++) {

                    if(GeneroActual.equals(split[y])){
                        Song song = new Song(imgAlbum, Name, artist, CancionesGenero[i], albumName2);
                        lista.add(song);
                    }

                }
            } else {
                if(GeneroActual.equals(Gen)){
                    Song song = new Song(imgAlbum, Name, artist, CancionesGenero[i], albumName2);
                    lista.add(song);
                }
            }


        }
        SongAdapter songAdapter = new SongAdapter(this, R.layout.song_list, lista);
        listSong.setAdapter(songAdapter);

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