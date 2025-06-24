package com.example.yanadenv.entidades;

public class upload   {


    String idLectura;
    String idParticipante;
    Object file;
    String nombreArchivo;
    String nombreGrabacion;
    int flag;
    String dni;
    String nombreSigla;



    public upload(String idLectura, String idParticipante, Object file, String nombreArchivo, String nombreGrabacion,int flag, String dni, String nombreSigla) {
        this.idLectura = idLectura;
        this.idParticipante = idParticipante;
        this.file = file;
        this.nombreArchivo = nombreArchivo;
        this.nombreGrabacion = nombreGrabacion;
        this.flag = flag;
        this.dni = dni;
        this.nombreSigla = nombreSigla;

    }

    public upload() {

    }



    public String getIdLectura() {
        return idLectura;
    }

    public void setIdLectura(String idLectura) {
        this.idLectura = idLectura;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreGrabacion() {
        return nombreGrabacion;
    }
    public void setNombreGrabacion(String nombreGrabacion) {
        this.nombreGrabacion = nombreGrabacion;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreSigla() {
        return nombreSigla;
    }

    public void setNombreSigla(String nombreSigla) {
        this.nombreSigla = nombreSigla;
    }
}