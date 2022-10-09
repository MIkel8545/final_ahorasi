package com.example.neomorfismomusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class AdaptadorArtist extends RecyclerView.Adapter<AdaptadorArtist.ViewHolder> {
    ArrayList<artistas> ListArtist;
    Context context;

    public AdaptadorArtist(Context context, ArrayList<artistas> ListArtist){
        this.context = context;
        this.ListArtist = ListArtist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        artistas artista = ListArtist.get(position);
        //Glide.with(context).load(ListArtist.get(position).getImg()).into(holder.imageView);
        holder.textView.setText(ListArtist.get(position).getNombre());
        holder.imageView.setImageResource(ListArtist.get(position).getImg());
        //holder.imageView.setImageResource(ListArtist.get(position).getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(), ArtistaActivity.class);
                i.putExtra("ArtistaDetails", artista);
                i.putExtra("Canciones", MainActivity.idCanciones);
                holder.itemView.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ListArtist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ImageView);
            textView = itemView.findViewById(R.id.TextView);

        }
    }
}
