package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.borutsky.neumorphism.NeumorphicFrameLayout;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class AlbumActivity extends AppCompatActivity {

    private ImageButton btnback;
    private NeumorphicFrameLayout btn;
    private ImageView img;
    private TextView alb;
    private TextView can;
    private TextView yea;
    ListView listSong;
    private Album album;
    private int CancionesAlbum[];
    String albumName2;
    ArrayList<Song> lista;
    String AlbumActualName;
    int NumCanciones;
    ImageView imageView;
    static MediaPlayer mediaPlayer;
    MediaMetadataRetriever mmr;
    static ImageView AlbumImg;
    static TextView Info;
    int CancionActual;

    //Reproductor
    static Button play;
    Button skip_prev;
    Button skip_next;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);


        //Reproductor
        play = findViewById(R.id.play);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        skip_next = findViewById(R.id.skip_next);
        skip_prev = findViewById(R.id.skip_prev);
        AlbumImg = findViewById(R.id.AlbumImg);
        Info = findViewById(R.id.InfoSong);


        btn = findViewById(R.id.flat_back);
        btnback = findViewById(R.id.imageBtn_back2);

        AlbumActivity.ButtonHandler blistener = new AlbumActivity.ButtonHandler();

        btnback.setOnClickListener(blistener);

        listSong = findViewById(R.id.listView_canciones);

        imageView = findViewById(R.id.imageView);

        alb = findViewById(R.id.textView_album);

        can = findViewById(R.id.textView_canciones);

        yea = findViewById(R.id.textView_year);
        AlbumImg = findViewById(R.id.AlbumImg);
        Info = findViewById(R.id.InfoSong);


        album = (Album) getIntent().getExtras().getSerializable("AlbumDetails");
        CancionesAlbum = (int[]) getIntent().getExtras().getSerializable("Canciones");

        lista = new ArrayList<>();
        ObtenCanciones();
        AlbumActualName = album.getNombreAlbum();
        alb.setText(album.getNombreAlbum().toUpperCase());
        can.setText(NumCanciones +" canciones");
        yea.setText("Album "+album.getYear());
        int Du = (int) getIntent().getExtras().getSerializable("Dur");
        CancionActual = (int) getIntent().getExtras().getSerializable("Cancion");
        CancionActual(CancionActual);
    }

    public void CancionActual(int Uri){
        mediaPlayer =  MediaPlayer.create(AlbumActivity.this, Uri);
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

    private void IsPlaying(){

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

    public void ObtenCanciones(){

        for(int i = 0; i < CancionesAlbum.length; i++) {

            final AssetFileDescriptor afd2 = getResources().openRawResourceFd(CancionesAlbum[i]);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());

            //OBTEN EL METADATA ALBUM
            albumName2 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String Name  = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            Log.d("COMPARACION", "Metadata: " + albumName2 +" - AlbumActual: "+ album.getNombreAlbum());

             if(album.getNombreAlbum().equals(albumName2)) {
                 Song song = new Song(album.getImagen(), Name, album.getArtista(), CancionesAlbum[i], albumName2);
                 lista.add(song);
                 NumCanciones++;

             }
        }
        SongAdapter songAdapter = new SongAdapter(this, R.layout.song_list, lista);
        listSong.setAdapter(songAdapter);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(album.getImagen(), 0,album.getImagen().length));

    }

    public void openHome(View view) {

        finish();
    }
    public void IniciarCanc(View v){
        ReproducirCancion(mediaPlayer);
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
    public static void ReproducirCancion( MediaPlayer mediaPlayer){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();

            play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }
        else {
            mediaPlayer.pause();
            play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);

        }
    }

    public void AnteriorSong(View view){
        if(CancionActual > CancionesAlbum.length-1){
            CancionActual = 0;
            mediaPlayer.reset();
            CancionActual(CancionesAlbum[CancionActual]);
            ReproducirCancion(mediaPlayer);
        }
        else
        {
            CancionActual++;
            mediaPlayer.reset();
            CancionActual(CancionesAlbum[CancionActual]);
            ReproducirCancion(mediaPlayer);
        }
    }

    public void SiguienteSong(View v){
        if(CancionActual > 0){
            CancionActual--;
            mediaPlayer.reset();
            CancionActual(CancionesAlbum[CancionActual]);
            ReproducirCancion(mediaPlayer);
            Log.d("Reproduciendo", String.valueOf(CancionActual) + CancionesAlbum[CancionActual]);
        }
        else {
            CancionActual = 0;
            mediaPlayer.reset();
            CancionActual(CancionesAlbum[CancionActual]);
            ReproducirCancion(mediaPlayer);
            Log.d("Reproduciendo", String.valueOf(CancionActual) + CancionesAlbum[CancionActual]);
        }
    }
    /*
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

    */

}
