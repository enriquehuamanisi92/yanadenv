package com.example.yanadenv.data.model.readGroup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReadvalueDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("readdataDTOs")
    @Expose
    private List<Object> readdataDTOs = null;

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

    public List<Object> getReaddataDTOs() {
        return readdataDTOs;
    }

    public void setReaddataDTOs(List<Object> readdataDTOs) {
        this.readdataDTOs = readdataDTOs;
    }

}