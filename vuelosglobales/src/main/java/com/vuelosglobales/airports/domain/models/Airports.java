package com.vuelosglobales.airports.domain.models;

public class Airports {
    private String id;
    private String name;
    private String id_city;

    public Airports(String id,String name, String id_city) {
        this.id = id;
        this.name = name;
        this.id_city = id_city;
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

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    public String toString() {
        return "[id=" + id + ", name=" + name + ", id_city=" + id_city + "]";
    }   

}