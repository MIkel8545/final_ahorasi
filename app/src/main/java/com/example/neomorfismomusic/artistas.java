package com.example.neomorfismomusic;

import android.util.Log;

public class artistas {

    private String  Img;
    private String Nombre;
    private String Genero;

    public artistas(String Img, String Nombre, String Genero){
        this.Img = Img;
        this.Nombre = Nombre;
        this.Genero = Genero;
    }

    public String  getImg(){
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
