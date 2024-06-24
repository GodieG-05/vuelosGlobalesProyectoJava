package com.vuelosglobales.airports.domain.models;

public class Airports {
    private String id;
    private String name;
    private String idCity;

    public Airports(String id,String name, String idCity) {
        this.id = id;
        this.name = name;
        this.idCity = idCity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String id_city) {
        this.idCity = id_city;
    }

    public String toString() {
        return "[id=" + id + ", name=" + name + ", id_city=" + idCity + "]";
    }   

}