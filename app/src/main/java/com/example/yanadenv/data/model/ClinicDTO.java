package com.example.yanadenv.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClinicDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("clinicgroupId")
    @Expose
    private String clinicgroupId;
    @SerializedName("input")
    @Expose
    private String input;
    @SerializedName("clinicvalueDTOs")
    @Expose
    private List<ClinicvalueDTO> clinicvalueDTOs = null;
    @SerializedName("clinicdataDTOs")
    @Expose
    private List<Object> clinicdataDTOs = null;

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

    public String getClinicgroupId() {
        return clinicgroupId;
    }

    public void setClinicgroupId(String clinicgroupId) {
        this.clinicgroupId = clinicgroupId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<ClinicvalueDTO> getClinicvalueDTOs() {
        return clinicvalueDTOs;
    }

    public void setClinicvalueDTOs(List<ClinicvalueDTO> clinicvalueDTOs) {
        this.clinicvalueDTOs = clinicvalueDTOs;
    }

    public List<Object> getClinicdataDTOs() {
        return clinicdataDTOs;
    }

    public void setClinicdataDTOs(List<Object> clinicdataDTOs) {
        this.clinicdataDTOs = clinicdataDTOs;
    }

    public ClinicDTO(String id, Boolean enabled, String name, String clinicgroupId, String input, List<ClinicvalueDTO> clinicvalueDTOs, List<Object> clinicdataDTOs) {
        this.id = id;
        this.enabled = enabled;
        this.name = name;
        this.clinicgroupId = clinicgroupId;
        this.input = input;
        this.clinicvalueDTOs = clinicvalueDTOs;
        this.clinicdataDTOs = clinicdataDTOs;
    }

    public ClinicDTO() {

    }
}