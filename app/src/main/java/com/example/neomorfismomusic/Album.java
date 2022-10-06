package com.example.neomorfismomusic;

public class Album {


    int Imagen;
    String NombreAlbum;
    String Artista;
    String Canciones;
    String Year;

    public Album(int imagen, String nombreAlbum, String artista, String canciones, String year) {
        Imagen = imagen;
        NombreAlbum = nombreAlbum;
        Artista = artista;
        Canciones = canciones;
        Year = year;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
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

    public String getCanciones() {
        return Canciones;
    }

    public void setCanciones(String  canciones) {
        Canciones = canciones;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
