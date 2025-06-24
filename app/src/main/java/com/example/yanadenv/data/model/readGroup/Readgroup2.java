package com.example.yanadenv.data.model.readGroup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Readgroup2 {

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
    private Object projectId;
    @SerializedName("readDTOs")
    @Expose
    private List<ReadDTO> readDTOs = null;

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

    public Object getProjectId() {
        return projectId;
    }

    public void setProjectId(Object projectId) {
        this.projectId = projectId;
    }

    public List<ReadDTO> getReadDTOs() {
        return readDTOs;
    }

    public void setReadDTOs(List<ReadDTO> readDTOs) {
        this.readDTOs = readDTOs;
    }

}