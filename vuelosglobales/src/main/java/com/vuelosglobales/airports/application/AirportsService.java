package com.vuelosglobales.airports.application;

import com.vuelosglobales.airports.domain.models.Airports;
import com.vuelosglobales.airports.infrastructure.AirportsRepository;
import java.util.Optional;


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

    public Optional<Airports> getAirportsById(int id) {
        return AirportsRepository.findById(id);
    }

    public void deleteAirports(int id) {
        AirportsRepository.delete(id);
    }
}