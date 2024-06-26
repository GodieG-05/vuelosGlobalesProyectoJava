package com.vuelosglobales.revisions.domain.models;

import java.sql.Date;

public class Revisions {
    private int id;
    private Date revisionDate;
    private int idPlane;
    
    public Revisions() {
    }

    public Revisions(int id, Date revisionDate, int idPlane) {
        this.id = id;
        this.revisionDate = revisionDate;
        this.idPlane = idPlane;
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public int getIdPlane() {
        return idPlane;
    }

    public void setIdPlane(int idPlane) {
        this.idPlane = idPlane;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", revisionDate=" + revisionDate + ", idPlane=" + idPlane + "]";
    }

}
