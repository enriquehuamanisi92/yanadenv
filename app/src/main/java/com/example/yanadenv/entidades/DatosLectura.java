package com.example.yanadenv.entidades;

public class DatosLectura {


    int id;
    String idParticipante;
    String IdLectura;
    String nombre;
    String ruta;

    public DatosLectura(int id, String idParticipante, String idLectura, String nombre, String ruta) {
        this.id = id;
        this.idParticipante = idParticipante;
        IdLectura = idLectura;
        this.nombre = nombre;
        this.ruta = ruta;
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

    public String getIdLectura() {
        return IdLectura;
    }

    public void setIdLectura(String idLectura) {
        IdLectura = idLectura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }


    //
//       dato.setId(cursor.getInt(0));
//            dato.setIdParticipante(cursor.getString(1));
//            dato.setIdLectura(cursor.getString(2));
//            dato.setNombre(cursor.getString(3));
//            dato.setRuta(cursor.getString(4));
}
