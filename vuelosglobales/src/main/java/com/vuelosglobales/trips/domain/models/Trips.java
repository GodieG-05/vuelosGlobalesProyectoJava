package com.vuelosglobales.trips.domain.models;

import java.sql.Date;

public class Trips {
    int id;
    Date tripDate;
    double priceTrip;
    
    public Trips(int id, Date tripDate, double priceTrip) {
        this.id = id;
        this.tripDate = tripDate;
        this.priceTrip = priceTrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date trip_date) {
        this.tripDate = trip_date;
    }

    public double getPriceTrip() {
        return priceTrip;
    }

    public void setPriceTrip(double price_trip) {
        this.priceTrip = price_trip;
    }

    public String toString() {
        return "[id=" + id + ", trip_date=" + tripDate + ", price_trip=" + priceTrip + "]";
    }
    
}
