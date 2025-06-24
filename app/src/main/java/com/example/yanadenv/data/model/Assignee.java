package com.example.yanadenv.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Assignee {

    @SerializedName("id")
    //PROBANDOasd
    //PROBANsssasdasdasdaASDASD
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("docnumber")
    @Expose
    private String docnumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public Assignee(String id, Boolean enabled, String name, String lastname, String sex, String doctype, String docnumber) {
        this.id = id;
        this.enabled = enabled;
        this.name = name;
        this.lastname = lastname;
        this.sex = sex;
        this.doctype = doctype;
        this.docnumber = docnumber;
    }

    public Assignee() {

    }
}