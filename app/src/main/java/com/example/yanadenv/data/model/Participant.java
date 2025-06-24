package com.example.yanadenv.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Participant {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("assignedById")
    @Expose
    private String assignedById;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("docnumber")
    @Expose
    private String docnumber;
    @SerializedName("campainId")
    @Expose
    private String campainId;
    @SerializedName("assigneeId")
    @Expose
    private String assigneeId;
    @SerializedName("socieconomicId")
    @Expose
    private String socieconomicId;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("contactnumber")
    @Expose
    private String contactnumber;
    @SerializedName("civilstatus")
    @Expose
    private String civilstatus;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("stature")
    @Expose
    private Integer stature;
    @SerializedName("imc")
    @Expose
    private Float imc;
    @SerializedName("participantstate")
    @Expose
    private String participantstate;
    @SerializedName("quality")
    @Expose
    private Object quality;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("descriptionstate")
    @Expose
    private Object descriptionstate;

    @SerializedName("labplomo")
    @Expose
    private String labplomo;


    @SerializedName("labmercurio")
    @Expose
    private String labmercurio;

    @SerializedName("labcadmio")
    @Expose
    private String labcadmio;


    @SerializedName("labarsenico")
    @Expose
    private String labarsenico;

    @SerializedName("labmolibdeno")
    @Expose
    private String labmolibdeno;




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

    // NUEVO

    public String getLabPlomo() {
        return labplomo;
    }

    public void setLabPlomo(String labPlomo) {
        this.labplomo = labplomo;
    }

    public String getLabMercurio() {
        return labmercurio;
    }

    public void setLabMercurio(String labmercurio) {
        this.labmercurio = labmercurio;
    }


    public String getLabCadmio() {
        return labcadmio;
    }

    public void setLabCadmio(String labcadmio) {
        this.labcadmio = labcadmio;
    }


    public String getLabmolibdeno() {
        return labmolibdeno;
    }

    public void setLabmolibdeno(String labcadmio) {
        this.labmolibdeno = labmolibdeno;
    }






    public String getLabarsenico() {
        return labarsenico;
    }

    public void setLabarsenico(String labarsenico) {
        this.labarsenico = labarsenico;
    }





    public String getAssignedById() {
        return assignedById;
    }

    public void setAssignedById(String assignedById) {
        this.assignedById = assignedById;
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

    public String getCampainId() {
        return campainId;
    }

    public void setCampainId(String campainId) {
        this.campainId = campainId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getSocieconomicId() {
        return socieconomicId;
    }

    public void setSocieconomicId(String socieconomicId) {
        this.socieconomicId = socieconomicId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getCivilstatus() {
        return civilstatus;
    }

    public void setCivilstatus(String civilstatus) {
        this.civilstatus = civilstatus;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getStature() {
        return stature;
    }

    public void setStature(Integer stature) {
        this.stature = stature;
    }

    public Float getImc() {
        return imc;
    }

    public void setImc(Float imc) {
        this.imc = imc;
    }

    public String getParticipantstate() {
        return participantstate;
    }

    public void setParticipantstate(String participantstate) {
        this.participantstate = participantstate;
    }

    public Object getQuality() {
        return quality;
    }

    public void setQuality(Object quality) {
        this.quality = quality;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getDescriptionstate() {
        return descriptionstate;
    }

    public void setDescriptionstate(Object descriptionstate) {
        this.descriptionstate = descriptionstate;
    }

    public Participant(String name, String lastname, String sex, String assignedById,
                       String doctype, String docnumber, String campainId, String assigneeId,
                       String socieconomicId, String countryId, String contactnumber, String civilstatus,
                       Integer weight, Integer stature, Float imc, String participantstate,
                       Object quality, Object state, Object descriptionstate) {
        this.name = name;
        this.lastname = lastname;
        this.sex = sex;
        this.assignedById = assignedById;
        this.doctype = doctype;
        this.docnumber = docnumber;
        this.campainId = campainId;
        this.assigneeId = assigneeId;
        this.socieconomicId = socieconomicId;
        this.countryId = countryId;
        this.contactnumber = contactnumber;
        this.civilstatus = civilstatus;
        this.weight = weight;
        this.stature = stature;
        this.imc = imc;
        this.participantstate = participantstate;
        this.quality = quality;
        this.state = state;
        this.descriptionstate = descriptionstate;
    }

    public Participant() {
    }
}