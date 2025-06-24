package com.example.yanadenv.data.model.editar;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ClinicdataDTO {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("participantId")
    @Expose
    private Object participantId;
    @SerializedName("clinicvalueId")
    @Expose
    private Object clinicvalueId;
    @SerializedName("clinicId")
    @Expose
    private Object clinicId;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Object participantId) {
        this.participantId = participantId;
    }

    public Object getClinicvalueId() {
        return clinicvalueId;
    }

    public void setClinicvalueId(Object clinicvalueId) {
        this.clinicvalueId = clinicvalueId;
    }

    public Object getClinicId() {
        return clinicId;
    }

    public void setClinicId(Object clinicId) {
        this.clinicId = clinicId;
    }

}