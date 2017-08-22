package com.example.isaigarciamoso.gps_blueicon2.models.response;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class Semana {
    private String nombreDia;
    private boolean estatus;
    private int numdia;

    public Semana(String nombreDia, boolean estatus, int numdia) {
        this.nombreDia = nombreDia;
        this.estatus = estatus;
        this.numdia = numdia;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public int getNumdia() {
        return numdia;
    }

    public void setNumdia(int numdia) {
        this.numdia = numdia;
    }
}

