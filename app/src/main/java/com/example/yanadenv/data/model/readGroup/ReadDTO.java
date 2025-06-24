package com.example.yanadenv.data.model.readGroup;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("readgroupId")
    @Expose
    private Object readgroupId;
    @SerializedName("readvalueDTOs")
    @Expose
    private List<ReadvalueDTO> readvalueDTOs = null;

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Object getReadgroupId() {
        return readgroupId;
    }

    public void setReadgroupId(Object readgroupId) {
        this.readgroupId = readgroupId;
    }

    public List<ReadvalueDTO> getReadvalueDTOs() {
        return readvalueDTOs;
    }

    public void setReadvalueDTOs(List<ReadvalueDTO> readvalueDTOs) {
        this.readvalueDTOs = readvalueDTOs;
    }

}