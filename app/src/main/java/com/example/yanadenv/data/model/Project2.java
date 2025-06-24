package com.example.yanadenv.data.model;



import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Project2 {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("projectId")
    @Expose
    private String projectId;
    @SerializedName("clinicDTOs")
    @Expose
    private List<ClinicDTO> clinicDTOs = null;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<ClinicDTO> getClinicDTOs() {
        return clinicDTOs;
    }

    public void setClinicDTOs(List<ClinicDTO> clinicDTOs) {
        this.clinicDTOs = clinicDTOs;
    }

}