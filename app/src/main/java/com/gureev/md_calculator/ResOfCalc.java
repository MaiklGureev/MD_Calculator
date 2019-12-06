package com.gureev.md_calculator;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class ResOfCalc implements Serializable {
    public double result;
    public String expr;
    public String datatime;
    public String lat;
    public String lon;

    public void setResult(Double result) {
        this.result = result;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public ResOfCalc() {
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Double getResult() {
        return result;
    }

    public String getExpr() {
        return expr;
    }

    public String getDatatime() {
        return datatime;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public ResOfCalc(String expr, Double result, String datatime, String lat, String lon) {
        this.result = result;
        this.expr = expr;
        this.datatime = datatime;
        this.lat = lat;
        this.lon = lon;
    }
}
