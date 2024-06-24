package com.vuelosglobales.trips.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vuelosglobales.trips.domain.models.Trips;

public interface TripsRepository {
    void assignX(Object idTrip, Object idEmployee, String tableName);
    void update(Trips trip);
    void delete(int id);
    Optional<Trips> findById(int id);
    Optional<String> findAssignation(Object idTrip, Object idX, String tableName);
    Optional<ArrayList<String>> findTripulation(int idTrip);
    Optional<ArrayList<String>> findScales(int idTrip);
    List<Trips> findAll();
    List<String> getTableValues(String tableName);
    List<Object> getIDs(String tableName);
}