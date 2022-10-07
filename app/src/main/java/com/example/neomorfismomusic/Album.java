package com.example.neomorfismomusic;

import java.io.Serializable;

public class Album implements Serializable {


    byte[] Imagen;
    String NombreAlbum;
    String Artista;
    int Canciones;
    String Year;

    public Album(byte[] imagen, String nombreAlbum, String artista, int canciones, String year) {
        Imagen = imagen;
        NombreAlbum = nombreAlbum;
        Artista = artista;
        Canciones = canciones;
        Year = year;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
        this.Imagen = imagen;
    }

    public String getNombreAlbum() {
        return NombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        NombreAlbum = nombreAlbum;
    }

    public String getArtista() {
        return Artista;
    }

    public void setArtista(String artista) {
        Artista = artista;
    }

    public int getCanciones() {
        return Canciones;
    }

    public void setCanciones(int  canciones) {
        Canciones = canciones;
    }

    public String getYear() {
        return this.Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
