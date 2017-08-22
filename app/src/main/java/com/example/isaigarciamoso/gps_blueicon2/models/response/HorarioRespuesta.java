package com.example.isaigarciamoso.gps_blueicon2.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class HorarioRespuesta  {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("NegocioId")
    @Expose
    private int negocioId;
    @SerializedName("HoraInicio")
    @Expose
    private String horarioInicio;
    @SerializedName("HoraFin")
    @Expose
    private String horarioFin;
    @SerializedName("Dias")
    @Expose
    private int dias;

    public HorarioRespuesta(int id, int negocioId, String horarioInicio, String horarioFin, int dias) {
        this.id = id;
        this.negocioId = negocioId;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.dias = dias;
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

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}
