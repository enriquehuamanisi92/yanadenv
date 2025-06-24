package com.example.yanadenv.entidades;

import java.io.Serializable;

public class Pais   implements Serializable {

    String id ;
    String name;


    public Pais(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Pais() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
