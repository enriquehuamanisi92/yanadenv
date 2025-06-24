package com.example.yanadenv.entidades;

import java.io.Serializable;

public class Apoderado implements Serializable {
    private String name;
    private String lastname;
    private String sex;
    private String doctype;
    private String docnumber;

    public Apoderado(String name, String lastname, String sex, String   doctype, String docnumber) {
        this.setName(name);
        this.setLastname(lastname);
        this.setSex(sex);
        this.setDoctype(doctype);
        this.setDocnumber(docnumber);
    }


    public Apoderado() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(String docnumber) {
        this.docnumber = docnumber;
    }
}
