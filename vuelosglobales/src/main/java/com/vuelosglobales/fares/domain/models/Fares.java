package com.vuelosglobales.fares.domain.models;

public class Fares {
    private int id;
    private String descrition;
    private String details;
    private double value;
    public Fares() {
    }
    public Fares(int id, String descrition, String details, double value) {
        this.id = id;
        this.descrition = descrition;
        this.details = details;
        this.value = value;
    }

    // getters and setters
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescrition() {
        return descrition;
    }
    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "[id=" + id + ", descrition=" + descrition + ", details=" + details + ", value=" + value + "]";
    }

}
