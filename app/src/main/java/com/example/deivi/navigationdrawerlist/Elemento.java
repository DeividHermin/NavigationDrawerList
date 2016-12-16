package com.example.deivi.navigationdrawerlist;

import java.io.Serializable;

public class Elemento implements Serializable {
    //private static final long serialVersionUID = 1L;
    private long id;
    private String nombre;

    public Elemento(long id, String nombre){
        this.nombre=nombre;
        this.id=id;
    }


    public String getNombre(){return nombre;}
    public long getId(){return id;}
    public void setId(long id){this.id = id;}
    public void setNombre(String nombre){this.nombre = nombre;}

}
