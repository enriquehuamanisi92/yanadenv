package com.example.yanadenv.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Post {

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
    private String campainId;
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
    private Double imc;

    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("pregnant")
    @Expose
    private String pregnant;

    @SerializedName("education")
    @Expose
    private String education;


    @SerializedName("assigneeDTO")
    @Expose
    private AssigneeDTO assigneeDTO;
    @SerializedName("clinicdataDTOs")
    @Expose
    private List<ClinicdataDTO> clinicdataDTOs = null;
    @SerializedName("socieconomicDTO")
    @Expose
    private SocieconomicDTO socieconomicDTO;


    public Post(String name, String lastname, String sex, String doctype, String docnumber,
                String campainId, String countryId, String contactnumber, String civilstatus,
                Integer weight, Integer stature, Double imc, Integer age, String pregnant, String education , AssigneeDTO assigneeDTO,
                List<ClinicdataDTO> clinicdataDTOs, SocieconomicDTO socieconomicDTO) {
        this.name = name;
        this.lastname = lastname;
        this.sex = sex;
        this.doctype = doctype;
        this.docnumber = docnumber;
        this.campainId = campainId;
        this.countryId = countryId;
        this.contactnumber = contactnumber;
        this.civilstatus = civilstatus;
        this.weight = weight;
        this.stature = stature;
        this.imc = imc;
        this.age =age;
        this.pregnant =pregnant;
        this.education =education;
        this.assigneeDTO = assigneeDTO;
        this.clinicdataDTOs = clinicdataDTOs;
        this.socieconomicDTO = socieconomicDTO;
    }

    public Post() {
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

    public String getCampainId() {
        return campainId;
    }

    public void setCampainId(String campainId) {
        this.campainId = campainId;
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

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Double imc) {
        this.age = age;
    }

    //NUEVO AGREGADO
    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String  education) {
        this.education = education;
    }





    public AssigneeDTO getAssigneeDTO() {
        return assigneeDTO;
    }

    public void setAssigneeDTO(AssigneeDTO assigneeDTO) {
        this.assigneeDTO = assigneeDTO;
    }

    public List<ClinicdataDTO> getClinicdataDTOs() {
        return clinicdataDTOs;
    }

    public void setClinicdataDTOs(List<ClinicdataDTO> clinicdataDTOs) {
        this.clinicdataDTOs = clinicdataDTOs;
    }

    public SocieconomicDTO getSocieconomicDTO() {
        return socieconomicDTO;
    }

    public void setSocieconomicDTO(SocieconomicDTO socieconomicDTO) {
        this.socieconomicDTO = socieconomicDTO;
    }

}