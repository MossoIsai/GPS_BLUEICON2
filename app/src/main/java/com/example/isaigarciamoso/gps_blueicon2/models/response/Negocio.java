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

    public Negocio() {
    }

    public Negocio(int id, int negocioId, String nombre) {
        this.id = id;
        this.negocioId = negocioId;
        this.nombre = nombre;
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

