package com.example.neomorfismomusic;

import android.graphics.drawable.Drawable;

public class Song {


    String Artista;
    int Imagen;
    String Nombre;


    public Song(int imagen, String nombre, String artista) {
        Imagen = imagen;
        Nombre = nombre;
        Artista = artista;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getArtista() {
        return Artista;
    }

    public void setArtista(String artista) {
        Artista = artista;
    }

}

