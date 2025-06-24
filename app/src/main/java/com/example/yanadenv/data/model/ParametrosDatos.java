package com.example.yanadenv.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParametrosDatos {

    @SerializedName("draw")
    @Expose
    private Integer draw;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("length")
    @Expose
    private Integer length;

    public ParametrosDatos(Integer draw, Integer start, Integer length) {
        this.draw = draw;
        this.start = start;
        this.length = length;
    }

    public ParametrosDatos() {
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}




