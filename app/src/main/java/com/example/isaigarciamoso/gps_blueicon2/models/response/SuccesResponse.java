package com.example.isaigarciamoso.gps_blueicon2.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class SuccesResponse {

    @SerializedName("Estatus")
    @Expose
    private boolean estatus;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    public SuccesResponse(boolean estatus, String mensaje) {
        this.estatus = estatus;
        this.mensaje = mensaje;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
