package com.vuelosglobales.trips.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vuelosglobales.trips.domain.models.Scales;
import com.vuelosglobales.trips.domain.models.Trips;
import com.vuelosglobales.trips.infrastructure.TripsRepository;

public class TripsService {
    private final TripsRepository TripsRepository;

    public TripsService(TripsRepository TripsRepository) {
        this.TripsRepository = TripsRepository;
    }
    public void assignX(Object idTrip, Object idEmployee, String tableName) {
        TripsRepository.assignX(idTrip, idEmployee, tableName);
    }

    public void updateTrips(Trips trip) {
        TripsRepository.update(trip);
    }

    public Optional<Scales> updateScale(Scales scale) {
        return TripsRepository.updateScale(scale);
    }

    public Optional<Trips> getTripsById(int id) {
        return TripsRepository.findById(id);
    }

    public Optional<ArrayList<String>> getTripulation(int idTrip) {
        return TripsRepository.findTripulation(idTrip);
    }

    public Optional<ArrayList<String>> getScalesFromTrip(int idTrip) {
        return TripsRepository.findScalesFromTrip(idTrip);
    }

    public Optional<ArrayList<String>> getScales(int id) {
        return TripsRepository.findScale(id);
    }

    public Optional<String> getAssignations(Object idTrip, Object idX, String tableName) {
        return TripsRepository.findAssignation(idTrip, idX, tableName);
    }

    public void deleteTrips(int id) {
        TripsRepository.delete(id);
    
    }
    public Optional<Scales> deleteScale(int id) {
        return TripsRepository.deleteScale(id);
    }

    public List<String> getAllValues(String tableName) {
        return TripsRepository.getTableValues(tableName);
    }

    public List<Object> getIDs(String tableName) {
        return TripsRepository.getIDs(tableName);
    }
}
