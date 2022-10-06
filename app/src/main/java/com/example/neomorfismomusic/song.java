package com.example.neomorfismomusic;

import android.graphics.drawable.Drawable;

public class song {

    private String NombreCancion;
    private String Artista;
    private String Album;
    private String Genero;
    private int Portada;
    private String UrSong;
    Drawable imagen ;

    song(String Nc, String Ar, String Al, String Gen, int Por, String UrS){
        this.NombreCancion = Nc;
        this.Artista = Ar;
        this.Album = Al;
        this.Genero = Gen;
        this.Portada = Por;
        this.UrSong = UrS;
    }

    public String getNombreCancion(){
        return this.NombreCancion;
    }
    public String Datos(){
        return this.NombreCancion +"\n" + this.Artista;
    }
    public String getArtista(){
        return this.Artista;
    }
    public int getPortada(){
        return this.Portada;
    }
    public String getUrSong(){
        return this.UrSong;
    }
}

