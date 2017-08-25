package com.example.isaigarciamoso.gps_blueicon2.models.response;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Negocio {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("NegocioId")
    @Expose
    private int negocioId;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Eliminado")
    @Expose
    private  boolean eliminado;

    public Negocio() {
    }

    public Negocio(int id, int negocioId, String nombre, boolean eliminado) {
        this.id = id;
        this.negocioId = negocioId;
        this.nombre = nombre;
        this.eliminado = eliminado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(int negocioId) {
        this.negocioId = negocioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "Negocio{" +
                "id=" + id +
                ", negocioId=" + negocioId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

