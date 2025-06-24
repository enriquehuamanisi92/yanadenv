package com.example.yanadenv.entidades;

public class ParticipanteAudios {

    int id;
    String  idParticipante;
    String Nombre;
    String Apellido;
    String Edad;

    public ParticipanteAudios(int id, String idParticipante, String nombre, String apellido, String edad) {
        this.id = id;
        this.idParticipante = idParticipante;
        Nombre = nombre;
        Apellido = apellido;
        Edad  = edad;
    }

    public ParticipanteAudios() {

    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }
}
