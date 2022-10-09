package com.example.neomorfismomusic;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterAlbum extends RecyclerView.Adapter<AdapterAlbum.ViewHolder> {

    ArrayList<Album> Albumes;
    Context context;

    public AdapterAlbum(Context context, ArrayList<Album> Albumes){
        this.context = context;
        this.Albumes = Albumes;
    }
    @NonNull
    @Override
    public AdapterAlbum.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AdapterAlbum.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlbum.ViewHolder holder, int position) {
        final Album album = Albumes.get(position);

        holder.textView.setText(Albumes.get(position).getNombreAlbum());
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(Albumes.get(position).getImagen(), 0, Albumes.get(position).getImagen().length));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Du = MainActivity.mediaPlayer.getCurrentPosition();
                Intent i = new Intent(holder.itemView.getContext(), AlbumActivity.class);
                i.putExtra("AlbumDetails", album);
                i.putExtra("Canciones", MainActivity.idCanciones);
                i.putExtra("Dur", Du);
                i.putExtra("Cancion", MainActivity.idCanciones[MainActivity.CancionActual]);
                //MainActivity.mediaPlayer.stop();
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Albumes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImgAlbum);
            textView = itemView.findViewById(R.id.NombreAlbum);
        }
    }
}
