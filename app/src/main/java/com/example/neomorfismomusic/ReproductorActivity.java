package com.example.neomorfismomusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.borutsky.neumorphism.NeumorphicFrameLayout;

public class ReproductorActivity extends AppCompatActivity {


    private NeumorphicFrameLayout btn;
    private NeumorphicFrameLayout btn2;
    private NeumorphicFrameLayout btn3;
    private ImageButton btnlike ;
    private ImageButton btnadd ;
    private ImageButton btnshufle ;
    private ImageButton btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        ImageButton btnpress = findViewById(R.id.imageBtn_play);
        ImageButton btnnext = findViewById(R.id.imageBtn_next);
        ImageButton btnprev = findViewById(R.id.imageBtn_prev);




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

    }
    private class  ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imageBtn_play:
                    btnPresionado("play", btn);
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