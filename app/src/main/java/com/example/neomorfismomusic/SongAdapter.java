package com.example.neomorfismomusic;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    private Context mContext;
    private int mResource;
    MediaPlayer mediaPlayer;

    public SongAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        ImageView image = convertView.findViewById(R.id.song_image);
        TextView nombre = convertView.findViewById(R.id.song_name);
        TextView artista = convertView.findViewById(R.id.song_artist);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlbumActivity.mediaPlayer.stop();
                int mp3 = getItem(position).getMp3();
                mediaPlayer = MediaPlayer.create(mContext, mp3);
                AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(mp3);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
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

                AlbumActivity.AlbumImg.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
                AlbumActivity.Info.setText(cancionName + "\n" + art);
               mediaPlayer.start();
               AlbumActivity.play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
               // AlbumActivity.ReproducirCancion(mediaPlayer);
            }
        });

        //int prueba = getItem(position).getImagen();
       // String msg = String.valueOf(prueba);

        //Log.i("ImagenID: ", msg);

        //image.setImageResource(getItem(position).getImagen());
        nombre.setText(getItem(position).getNombre());
        artista.setText(getItem(position).getArtista());
        image.setImageBitmap(BitmapFactory.decodeByteArray(getItem(position).getImagen(), 0,getItem(position).getImagen().length));
        return convertView;
    }


}
