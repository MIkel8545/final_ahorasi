package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.borutsky.neumorphism.NeumorphicFrameLayout;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private ImageButton btnback;
    private NeumorphicFrameLayout btn;
    private ImageView img;
    private TextView alb;
    private TextView can;
    private TextView yea;
    ListView listSong;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);


        btn = findViewById(R.id.flat_back);
        btnback = findViewById(R.id.imageBtn_back2);

        AlbumActivity.ButtonHandler blistener = new AlbumActivity.ButtonHandler();

        btnback.setOnClickListener(blistener);

    listSong = findViewById(R.id.listView_canciones);

    img = findViewById(R.id.imageView);

    alb = findViewById(R.id.textView_album);

    can = findViewById(R.id.textView_canciones);

    yea = findViewById(R.id.textView_year);


    Album album = new Album(R.drawable.item1, "IMPERA", "Ghost", "12 canciones", " Album 2022");

        img.setImageResource(album.getImagen());
        alb.setText(album.getNombreAlbum());
        can.setText(album.getCanciones());
        yea.setText(album.getYear());


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
    private class  ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.imageBtn_back2:
                    btnBack();
                    break;
                default:

            }
        }
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

    void btnBack(){
        finish();
    }




    void btnPresionado(String s, NeumorphicFrameLayout btn) {

        btn.setState( NeumorphicFrameLayout.State.PRESSED);
        Toast.makeText(AlbumActivity.this, "Boton " + s + " pulsado", Toast.LENGTH_SHORT).show();

        final long changeTime = 100L;
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setState( NeumorphicFrameLayout.State.FLAT);
            }
        }, changeTime);

    }



}
