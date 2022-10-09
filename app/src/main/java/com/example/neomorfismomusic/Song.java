package com.example.neomorfismomusic;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class Song {


    String Artista;
    byte[] Imagen;
    String Nombre;
    int mp3;
    String album;


    public Song(byte[] imagen, String nombre, String artista, int mp3, String album) {
        Imagen = imagen;
        Nombre = nombre;
        Artista = artista;
        this.mp3 = mp3;
        this.album = album;
    }

    public int getMp3(){return this.mp3;}
    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
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

    public String getAlbum(){
        return this.album;
    }

    public void Mostrar(){
        Log.d("Muestra datos", this.Nombre + " - " + this.Artista);
    }
}

