package com.example.yanadenv.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IdParticipanteLectura {

    @SerializedName("participantId ")
    @Expose
    private String participantId;


    public IdParticipanteLectura(String participantId) {
        this.participantId = participantId;
    }

    public IdParticipanteLectura() {

    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

}