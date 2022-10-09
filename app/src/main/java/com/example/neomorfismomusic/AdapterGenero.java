package com.example.neomorfismomusic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterGenero extends RecyclerView.Adapter<AdapterGenero.ViewHolder> {
    ArrayList<String> ListGenero;
    Context context;

    public AdapterGenero(Context context, ArrayList<String> ListGenero){
        this.context = context;
        this.ListGenero = ListGenero;
    }
    @NonNull
    @Override
    public AdapterGenero.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genero, parent, false);
        return new AdapterGenero.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGenero.ViewHolder holder, int position) {

        String GeneroActual = ListGenero.get(position);
        holder.textGenero.setText(ListGenero.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.itemView.getContext(), gnero.class);
                i.putExtra("GeneroDetails",GeneroActual);
                i.putExtra("Canciones", MainActivity.idCanciones);
                i.putExtra("artistas", MainActivity.ListArtis);
                holder.itemView.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListGenero.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textGenero;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textGenero = itemView.findViewById(R.id.textGenero);

            }
    }
}
