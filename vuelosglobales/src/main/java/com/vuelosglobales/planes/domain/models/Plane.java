package com.vuelosglobales.planes.domain.models;

import java.util.Date;
public class Plane {
    private int id;
    private String plates;
    private int capacity;
    private Date fabricationDate;
    private int idStatus;
    private int idModel;

    public Plane() {
    }

    public Plane(int id, String plates, int capacity, Date fabricationDate, int idStatus, int idModel) {
        this.id = id;
        this.plates = plates;
        this.capacity = capacity;
        this.fabricationDate = fabricationDate;
        this.idStatus = idStatus;
        this.idModel = idModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getFabricationDate() {
        return fabricationDate;
    }

    public void setFabricationDate(Date fabricationDate) {
        this.fabricationDate = fabricationDate;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    @Override
    public String toString() {
        return "Plane [id=" + id + ", plates=" + plates + ", capacity=" + capacity + ", fabricationDate="
                + fabricationDate + ", idStatus=" + idStatus + ", idModel=" + idModel + "]";
    }
    

    
}
