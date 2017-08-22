package com.example.isaigarciamoso.gps_blueicon2.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by isaigarciamoso on 19/08/17.
 */

public class DetalleNegocio {
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("NegocioId")
    @Expose
    private int negocioId;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Latitud")
    @Expose
    private String lalitud;
    @SerializedName("Longitud")
    @Expose
    private String longitud;
    @SerializedName("Direccion")
    @Expose
    private String direccion;
    @SerializedName("TelefonoPrincipal")
    @Expose
    private String telefonoPrincipal;
    @SerializedName("TelefonoSecundario")
    @Expose
    private String telefonoSecundario;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;
    @SerializedName("Mail")
    @Expose
    private String mail;
    @SerializedName("Facebook")
    @Expose
    private String facebook;
    @SerializedName("Twitter")
    @Expose
    private String twitter;
    @SerializedName("Giro")
    @Expose
    private String giro;
    @SerializedName("Estacionamiento")
    @Expose
    private String estacionamiento;
    @SerializedName("Horario")
    @Expose
    private String horario;
    @SerializedName("ServicioDocimicilio")
    @Expose
    private boolean servicioDomicilio;
    @SerializedName("NombreContacto")
    @Expose
    private String nombreContacto;
    @SerializedName("MailContacto")
    @Expose
    private String mailContacto;
    @SerializedName("TelefonoContacto")
    @Expose
    private String telefonoContacto;
    @SerializedName("lsHorarios")
    @Expose
    private List<HorarioRespuesta> horarioRespuestas;
    @SerializedName("Eliminado")
    @Expose
    private boolean eliminado;
    @SerializedName("Observaciones")
    @Expose
    private String observaciones;
    @SerializedName("ProximaVisita")
    @Expose
    private String proximaVisita;
    @SerializedName("Pendiente")
    @Expose
    private boolean pendiente;


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

    public String getLalitud() {
        return lalitud;
    }

    public void setLalitud(String lalitud) {
        this.lalitud = lalitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoPrincipal() {
        return telefonoPrincipal;
    }

    public void setTelefonoPrincipal(String telefonoPrincipal) {
        this.telefonoPrincipal = telefonoPrincipal;
    }

    public String getTelefonoSecundario() {
        return telefonoSecundario;
    }

    public void setTelefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(String estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isServicioDomicilio() {
        return servicioDomicilio;
    }

    public void setServicioDomicilio(boolean servicioDomicilio) {
        this.servicioDomicilio = servicioDomicilio;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getMailContacto() {
        return mailContacto;
    }

    public void setMailContacto(String mailContacto) {
        this.mailContacto = mailContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public List<HorarioRespuesta> getHorarioRespuestas() {
        return horarioRespuestas;
    }

    public void setHorarioRespuestas(List<HorarioRespuesta> horarioRespuestas) {
        this.horarioRespuestas = horarioRespuestas;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getProximaVisita() {
        return proximaVisita;
    }

    public void setProximaVisita(String proximaVisita) {
        this.proximaVisita = proximaVisita;
    }

    public boolean isPendiente() {
        return pendiente;
    }

    public void setPendiente(boolean pendiente) {
        this.pendiente = pendiente;
    }
}
