package com.vuelosglobales.bookings.domain.models;

import java.sql.Date;

public class Bookings {
    private int id;
    private Date date;
    private int idTrip;
    
    public Bookings(int id, Date date, int idTrip) {
        this.id = id;
        this.date = date;
        this.idTrip = idTrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", date=" + date + ", idTrip=" + idTrip + "]";
    }

}
 