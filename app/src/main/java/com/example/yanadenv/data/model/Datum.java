package com.example.yanadenv.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Object enabled;
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
    @SerializedName("campainId")
    @Expose
    private Object campainId;
    @SerializedName("assigneeId")
    @Expose
    private String assigneeId;
    @SerializedName("countryId")
    @Expose
    private Object countryId;
    @SerializedName("contactnumber")
    @Expose
    private Object contactnumber;
    @SerializedName("civilstatus")
    @Expose
    private Object civilstatus;
    @SerializedName("weight")
    @Expose
    private Object weight;
    @SerializedName("stature")
    @Expose
    private Object stature;
    @SerializedName("imc")
    @Expose
    private Object imc;
    @SerializedName("centerName")
    @Expose
    private String centerName;
    @SerializedName("campainName")
    @Expose
    private String campainName;
    @SerializedName("assigneeName")
    @Expose
    private String assigneeName;

    public Datum(String id, Object enabled, String name, String lastname, String sex, String doctype,
                 String docnumber, Object campainId, String assigneeId, Object countryId, Object contactnumber,
                 Object civilstatus, Object weight, Object stature, Object imc, String centerName, String campainName,
                 String assigneeName) {
        this.id = id;
        this.enabled = enabled;
        this.name = name;
        this.lastname = lastname;
        this.sex = sex;
        this.doctype = doctype;
        this.docnumber = docnumber;
        this.campainId = campainId;
        this.assigneeId = assigneeId;
        this.countryId = countryId;
        this.contactnumber = contactnumber;
        this.civilstatus = civilstatus;
        this.weight = weight;
        this.stature = stature;
        this.imc = imc;
        this.centerName = centerName;
        this.campainName = campainName;
        this.assigneeName = assigneeName;
    }


    public Datum() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getEnabled() {
        return enabled;
    }

    public void setEnabled(Object enabled) {
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

    public Object getCampainId() {
        return campainId;
    }

    public void setCampainId(Object campainId) {
        this.campainId = campainId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Object getCountryId() {
        return countryId;
    }

    public void setCountryId(Object countryId) {
        this.countryId = countryId;
    }

    public Object getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(Object contactnumber) {
        this.contactnumber = contactnumber;
    }

    public Object getCivilstatus() {
        return civilstatus;
    }

    public void setCivilstatus(Object civilstatus) {
        this.civilstatus = civilstatus;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Object getStature() {
        return stature;
    }

    public void setStature(Object stature) {
        this.stature = stature;
    }

    public Object getImc() {
        return imc;
    }

    public void setImc(Object imc) {
        this.imc = imc;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCampainName() {
        return campainName;
    }

    public void setCampainName(String campainName) {
        this.campainName = campainName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

}