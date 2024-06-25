package com.vuelosglobales.fares.application;

import java.util.Optional;

import com.vuelosglobales.fares.domain.models.Fares;
import com.vuelosglobales.fares.infraestructure.FaresRepository;

import java.util.List;

public class FaresService {
    private final FaresRepository flightFareRepository;

    public FaresService(FaresRepository flightFareRepository) {
        this.flightFareRepository = flightFareRepository;
    }

    public void createFlightFare(Fares flightFare){
        flightFareRepository.save(flightFare);
    }

    public void updateFlightFare(Fares flightFare){
        flightFareRepository.update(flightFare);
    }

    public void deleteFlightFare(int id) {
        flightFareRepository.delete(id);
    }

    public Optional<Fares> getFlightFareById(int id){
        return flightFareRepository.findById(id);
    }

    public List<Fares> getAllFlightFares(){
        return flightFareRepository.findAll();
    }

    public List<Integer> getIDs(String tableName) {
        return flightFareRepository.getIDs(tableName);
    }
}
