package com.example.yanadenv.data.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocieconomicDTO {
    public SocieconomicDTO(String apartment, String material, String electric, String water, String drain,
                           int familynumber, int childrennumber, int adultnumber, int infantnumber, int pregnantnumber) {
        this.apartment = apartment;
        this.material = material;
        this.electric = electric;
        this.water = water;
        this.drain = drain;
        this.familynumber = familynumber;
        this.childrennumber = childrennumber;
        this.adultnumber = adultnumber;
        this.infantnumber = infantnumber;
        this.pregnantnumber = pregnantnumber;
    }

    public SocieconomicDTO() {
    }

    @SerializedName("apartment")
    @Expose
    private String apartment;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("electric")
    @Expose
    private String electric;
    @SerializedName("water")
    @Expose
    private String water;
    @SerializedName("drain")
    @Expose
    private String drain;
    @SerializedName("familynumber")
    @Expose
    private Integer familynumber;
    @SerializedName("childrennumber")
    @Expose
    private Integer childrennumber;
    @SerializedName("adultnumber")
    @Expose
    private Integer adultnumber;
    @SerializedName("infantnumber")
    @Expose
    private Integer infantnumber;
    @SerializedName("pregnantnumber")
    @Expose
    private Integer pregnantnumber;

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
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

    public Integer getFamilynumber() {
        return familynumber;
    }

    public void setFamilynumber(Integer familynumber) {
        this.familynumber = familynumber;
    }

    public Integer getChildrennumber() {
        return childrennumber;
    }

    public void setChildrennumber(Integer childrennumber) {
        this.childrennumber = childrennumber;
    }

    public Integer getAdultnumber() {
        return adultnumber;
    }

    public void setAdultnumber(Integer adultnumber) {
        this.adultnumber = adultnumber;
    }

    public Integer getInfantnumber() {
        return infantnumber;
    }

    public void setInfantnumber(Integer infantnumber) {
        this.infantnumber = infantnumber;
    }

    public Integer getPregnantnumber() {
        return pregnantnumber;
    }

    public void setPregnantnumber(Integer pregnantnumber) {
        this.pregnantnumber = pregnantnumber;
    }

}