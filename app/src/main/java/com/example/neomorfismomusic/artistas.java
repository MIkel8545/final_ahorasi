package com.example.neomorfismomusic;

import android.util.Log;

import java.io.Serializable;

public class artistas implements Serializable {

    private int  Img;
    private String Nombre;
    private String Genero;

    public artistas(int Img, String Nombre, String Genero){
        this.Img = Img;
        this.Nombre = Nombre;
        this.Genero = Genero;
    }

    public int  getImg(){
        return this.Img;
    }
    public String getNombre(){
        return this.Nombre;
    }
    public  String getGenero(){
        return this.Genero;
    }
    public void Mostrar(){
        Log.d("Datos", "Nombre: " + this.Nombre+ ", Imagen: "+ this.Img);}
}
