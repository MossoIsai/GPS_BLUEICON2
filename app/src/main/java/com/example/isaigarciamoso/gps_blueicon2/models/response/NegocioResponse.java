package com.example.isaigarciamoso.gps_blueicon2.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class NegocioResponse {
    @SerializedName("NegocioDetalleTemporal")
    @Expose
    private DetalleNegocio negocioRequest;
    @SerializedName("Estatus")
    @Expose
    private boolean estatus;
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;


    public NegocioResponse(DetalleNegocio negocioRequest, boolean estatus, String mensaje) {
        this.negocioRequest = negocioRequest;
        this.estatus = estatus;
        this.mensaje = mensaje;
    }

    public DetalleNegocio getNegocioRequest() {
        return negocioRequest;
    }

    public void setNegocioRequest(DetalleNegocio negocioRequest) {
        this.negocioRequest = negocioRequest;
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