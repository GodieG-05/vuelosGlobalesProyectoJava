package com.vuelosglobales.trips.infrastructure;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vuelosglobales.trips.domain.models.Scales;
import com.vuelosglobales.trips.domain.models.Trips;

public interface TripsRepository {
    void assignX(Object idTrip, Object idEmployee, String tableName);
    void update(Trips trip);
    Optional<Scales> updateScale(Scales scale);
    void delete(int id);
    Optional<Scales> deleteScale(int id);
    Optional<Trips> findById(int id);
    Optional<ArrayList<String>> findByIdFromPlace(String idOrg, String idDes);
    Optional<String> findAssignation(Object idTrip, Object idX, String tableName);
    Optional<ArrayList<String>> findTripulation(int idTrip);
    Optional<ArrayList<String>> findScalesFromTrip(int idTrip);
    Optional<ArrayList<String>> findScale(int id);
    List<Trips> findAll();
    int selectFlight(String idCus, int idFli, int idFar, Date date);
    List<String> getTableValues(String tableName);
    List<Object> getIDs(String tableName);
}