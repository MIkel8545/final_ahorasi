package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista);



        listSong = findViewById(R.id.list_canciones2);


        ArrayList<Song> lista = new ArrayList<>();
        lista.add(new

                Song(R.drawable.item1, "Imperium","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Kaisarion","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Spillways","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Call Me Little Sunshine","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Hunter's Moon","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Watcher in the Sky","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Dominion","Ghost"));
        lista.add(new

                Song(R.drawable.item1, "Twenties","Ghost"));


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
}