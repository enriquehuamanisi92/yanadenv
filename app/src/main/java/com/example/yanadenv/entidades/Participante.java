package com.example.yanadenv.entidades;

import java.io.Serializable;

public class Participante  implements Serializable {

    private int id;
    private String name;
    private String lastname;
    private String sex;
    private String doctype;
    private String docnumber;
    private String campainId;
    private String countryId;
    private String contactnumber;
    private String civilstatus;
    private Integer weight;
    private Integer stature;
    private Double imc;
    private String nameApoderado;
    private String lastnameApoderado;
    private String sexApoderado;
    private String doctypeApoderado;
    private String docnumberApoderado;
    private String apartament;
    private String material;
    private String electric;
    private String water;
    private String drain;
    private String familyNumber;
    private String ChildrenNumber;
    private String adultNumber;
    private String infantNumber;
    private String pregnatNumber;
    private String edad;
    private String gestante;
    private String educacion;
    private Integer flag;
    private String idParticipante;
    private String idParticipanteServidor;
    // private Apoderado apoderado;


    public Participante(int id, String name, String lastname, String sex, String doctype, String docnumber,
                        String campainId, String countryId, String contactnumber, String civilstatus, Integer weight,
                        Integer stature, Double imc, String nameApoderado, String lastnameApoderado, String sexApoderado,
                        String doctypeApoderado, String docnumberApoderado, String apartament, String material, String electric,
                        String water, String drain, String familyNumber, String childrenNumber, String adultNumber, String infantNumber,
                        String pregnatNumber, String edad, String gestante,String educacion,Integer flag, String idParticipante, String idParticipanteServidor) {
        this.id = id;
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
        this.nameApoderado = nameApoderado;
        this.lastnameApoderado = lastnameApoderado;
        this.sexApoderado = sexApoderado;
        this.doctypeApoderado = doctypeApoderado;
        this.docnumberApoderado = docnumberApoderado;
        this.apartament = apartament;
        this.material = material;
        this.electric = electric;
        this.water = water;
        this.drain = drain;
        this.familyNumber = familyNumber;
        ChildrenNumber = childrenNumber;
        this.adultNumber = adultNumber;
        this.infantNumber = infantNumber;
        this.pregnatNumber = pregnatNumber;
        this.edad = edad;
        this.gestante = gestante;
        this.educacion = educacion;
        this.flag = flag;
        this.idParticipante = idParticipante;
        this.idParticipanteServidor = idParticipanteServidor;
    }

    public Participante() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNameApoderado() {
        return nameApoderado;
    }

    public void setNameApoderado(String nameApoderado) {
        this.nameApoderado = nameApoderado;
    }

    public String getLastnameApoderado() {
        return lastnameApoderado;
    }

    public void setLastnameApoderado(String lastnameApoderado) {
        this.lastnameApoderado = lastnameApoderado;
    }

    public String getSexApoderado() {
        return sexApoderado;
    }

    public void setSexApoderado(String sexApoderado) {
        this.sexApoderado = sexApoderado;
    }

    public String getDoctypeApoderado() {
        return doctypeApoderado;
    }

    public void setDoctypeApoderado(String doctypeApoderado) {
        this.doctypeApoderado = doctypeApoderado;
    }

    public String getDocnumberApoderado() {
        return docnumberApoderado;
    }

    public void setDocnumberApoderado(String docnumberApoderado) {
        this.docnumberApoderado = docnumberApoderado;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getDrain() {
        return drain;
    }

    public void setDrain(String drain) {
        this.drain = drain;
    }

    public String getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(String familyNumber) {
        this.familyNumber = familyNumber;
    }

    public String getChildrenNumber() {
        return ChildrenNumber;
    }

    public void setChildrenNumber(String childrenNumber) {
        ChildrenNumber = childrenNumber;
    }

    public String getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(String adultNumber) {
        this.adultNumber = adultNumber;
    }

    public String getInfantNumber() {
        return infantNumber;
    }

    public void setInfantNumber(String infantNumber) {
        this.infantNumber = infantNumber;
    }

    public String getPregnatNumber() {
        return pregnatNumber;
    }

    public void setPregnatNumber(String pregnatNumber) {
        this.pregnatNumber = pregnatNumber;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    // NUEVO GESTANTE

    public String getGestante() {
        return gestante;
    }

    public void setGestante(String gestante) {
        this.gestante = gestante;
    }

    // NUEVO EDUCACION

    public String getEducacion() {
        return educacion;
    }

    public void setEducacion(String educacion) {
        this.educacion = educacion;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getIdParticipanteServidor() {
        return idParticipanteServidor;
    }

    public void setIdParticipanteServidor(String idParticipanteServidor) {
        this.idParticipanteServidor = idParticipanteServidor;
    }
}
