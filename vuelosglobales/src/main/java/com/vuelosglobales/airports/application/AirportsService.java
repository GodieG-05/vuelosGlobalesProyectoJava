package com.vuelosglobales.airports.application;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.airports.domain.models.Airports;
import com.vuelosglobales.airports.infrastructure.AirportsRepository;


public class AirportsService {
    private final AirportsRepository AirportsRepository;

    public AirportsService(AirportsRepository AirportsRepository) {
        this.AirportsRepository = AirportsRepository;
    }

    public void createAirports(Airports Airport) {
        AirportsRepository.save(Airport);
    }

    public void updateAirports(Airports Airport) {
        AirportsRepository.update(Airport);
    }

    public Optional<Airports> getAirportsById(String id) {
        return AirportsRepository.findById(id);
    }

    public void deleteAirports(String id) {
        AirportsRepository.delete(id);
    }

    public List<Airports> getAllAirports() {
        return AirportsRepository.findAll();
    }

    public List<String> getTableValues(String tableName) {
        return AirportsRepository.getTableValues(tableName);
    }

    public List<String> getIDs(String tableName) {
        return AirportsRepository.getIDs(tableName);
    }
}