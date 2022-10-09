package com.example.neomorfismomusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterGenList extends ArrayAdapter<String> {
    private Context mContext;
    private int mResource;

    public AdapterGenList(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
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

        //int prueba = getItem(position).getImagen();
        // String msg = String.valueOf(prueba);

        //Log.i("ImagenID: ", msg);

        //image.setImageResource(getItem(position).getImagen());
        nombre.setText(getItem(position));
        artista.setText("");

        //image.setImageResource();
        return convertView;
    }
}
