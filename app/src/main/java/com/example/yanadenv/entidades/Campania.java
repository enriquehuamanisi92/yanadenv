package com.example.yanadenv.entidades;

public class Campania {

    String id ;
    String name;



    public Campania(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Campania() {

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
