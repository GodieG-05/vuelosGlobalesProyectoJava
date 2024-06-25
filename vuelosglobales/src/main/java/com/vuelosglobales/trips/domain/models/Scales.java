package com.vuelosglobales.trips.domain.models;

public class Scales {
    private int id;
    private String idOrg;
    private String idDes;

    public Scales(int id, String idOrg, String idDes) {
        this.id = id;
        this.idOrg = idOrg;
        this.idDes = idDes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(String idOrg) {
        this.idOrg = idOrg;
    }

    public String getIdDes() {
        return idDes;
    }

    public void setIdDes(String idDes) {
        this.idDes = idDes;
    }

    @Override
    public String toString() {
        return "Scales [id=" + id + ", id_origin_airport=" + idOrg + ", id_destination_airport=" + idDes + "]";
    }
    
}