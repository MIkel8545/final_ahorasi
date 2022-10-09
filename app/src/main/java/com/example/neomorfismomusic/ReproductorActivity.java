package com.example.neomorfismomusic;

import static com.example.neomorfismomusic.R.id.ImgSong;
import static com.example.neomorfismomusic.R.id.textView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.borutsky.neumorphism.NeumorphicFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReproductorActivity extends AppCompatActivity {


    private NeumorphicFrameLayout btn;
    private NeumorphicFrameLayout btn2;
    private NeumorphicFrameLayout btn3;
    private ImageButton btnlike ;
    private ImageButton btnadd ;
    MediaPlayer mediaPlayer;
    private ImageButton btnshufle ;
    private ImageButton btnback;
    ImageView ImgSong;
    TextView textView3;
    TextView textView4;
    SeekBar seekBar;
    boolean wasPlaying = false;
    FloatingActionButton fab;
    Handler handler;
    Runnable runnable;
    ImageButton btnpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        btnpress= findViewById(R.id.imageBtn_play);
        ImageButton btnnext = findViewById(R.id.imageBtn_next);
        ImageButton btnprev = findViewById(R.id.imageBtn_prev);
       ImgSong = findViewById(R.id.ImgSong);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        seekBar = findViewById(R.id.seekBar2);




        btn = findViewById(R.id.flat_play);
        btn2 = findViewById(R.id.flat_prev);
        btn3 = findViewById(R.id.flat_next);
        btnlike = findViewById(R.id.imageBtnLike);
        btnadd = findViewById(R.id.imageBtnAdd);
        btnshufle = findViewById(R.id.imageBtnShuffle);
        btnback = findViewById(R.id.imageBtn_back);




        ButtonHandler blistener = new ButtonHandler();

        btnpress.setOnClickListener(blistener);
        btnnext.setOnClickListener(blistener);
        btnprev.setOnClickListener(blistener);

        btnlike.setOnClickListener(blistener);
        btnadd.setOnClickListener(blistener);
        btnshufle.setOnClickListener(blistener);
        btnback.setOnClickListener(blistener);

        handler = new Handler();
        StartMusic(R.raw.axe);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ///Info de ka cancion
        final AssetFileDescriptor afd =getResources().openRawResourceFd(R.raw.axe);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String cancionName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String art = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        byte[] img = mmr.getEmbeddedPicture();
        long dur = Long.parseLong(duration);
        String seconds = String.valueOf((dur % 60000) / 1000);
        ImgSong.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
        textView3.setText(cancionName);
        textView4.setText(art);

    }

    private void skeebarProgress() {

        int currPos = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(currPos);
        runnable = new Runnable(){

            @Override
            public void run() {
                skeebarProgress();
            }
        };
        handler.postDelayed(runnable, 1000);

    }

    private void StartMusic(int U){
        //mediaPlayer = new MediaPlayer();
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer =  MediaPlayer.create(ReproductorActivity.this, U);
       /* try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    private class  ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageBtn_play:
                    btnPresionado("play", btn);
                    if(!mediaPlayer.isPlaying()){
                        seekBar.setMax(mediaPlayer.getDuration());
                         mediaPlayer.start();
                        btnpress.setBackgroundResource(R.drawable.ic_baseline_pause_24);

                        skeebarProgress();
                    }
                 else {
                    mediaPlayer.pause();
                        btnpress.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                 }
                    break;
                case R.id.imageBtn_next:
                    btnPresionado("next", btn3);
                    break;
                case R.id.imageBtn_prev:
                    btnPresionado("prev", btn2);
                    break;
                case R.id.imageBtnAdd:
                    iconoPresionado("add", btnadd);
                    break;
                case R.id.imageBtnShuffle:
                    iconoPresionado("shuffle",btnshufle);
                    break;
                case R.id.imageBtnLike:
                    iconoPresionado("like",btnlike);
                    break;
                case R.id.imageBtn_back:
                    btnBack();
                    break;
                default:
                    btnPresionado("Error",btn);
            }
        }
    }

    void iconoPresionado(String s, ImageButton btn) {

        btn.setImageTintList(ColorStateList.valueOf(Color.argb(100,78, 125, 237)));
        Toast.makeText(ReproductorActivity.this, "Boton " + s + " pulsado", Toast.LENGTH_SHORT).show();

    }

    void btnBack(){
        finish();
    }




    void btnPresionado(String s,NeumorphicFrameLayout btn) {

        btn.setState( NeumorphicFrameLayout.State.PRESSED);
        Toast.makeText(ReproductorActivity.this, "Boton " + s + " pulsado", Toast.LENGTH_SHORT).show();

        final long changeTime = 100L;
        btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setState( NeumorphicFrameLayout.State.FLAT);
            }
        }, changeTime);

    }
}