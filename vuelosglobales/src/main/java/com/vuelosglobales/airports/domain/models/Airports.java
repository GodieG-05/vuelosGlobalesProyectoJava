package com.vuelosglobales.airports.domain.models;

public class Airports {
    private int id;
    private String name;
    private String id_city;

    public Airports(int id,String name, String id_city) {
        this.id = id;
        this.name = name;
        this.id_city = id_city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Airports [id=" + id + ", name=" + name + ", id_city=" + id_city + "]";
    }

    
}