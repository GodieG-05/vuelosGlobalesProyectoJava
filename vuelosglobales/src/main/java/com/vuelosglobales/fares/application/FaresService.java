package com.vuelosglobales.fares.application;

import java.util.Optional;

import com.vuelosglobales.fares.domain.models.Fares;
import com.vuelosglobales.fares.infraestructure.FaresRepository;

import java.util.List;

public class FaresService {
    private final FaresRepository faresRepository;

    public FaresService(FaresRepository flightFareRepository) {
        this.faresRepository = flightFareRepository;
    }

    public int getLastId(){
        return faresRepository.getLastId();
    }

    public void createFare(Fares flightFare){
        faresRepository.save(flightFare);
    }

    public void updateFare(Fares flightFare){
        faresRepository.update(flightFare);
    }

    public void deleteFare(int id) {
        faresRepository.delete(id);
    }

    public Optional<Fares> getFareById(int id){
        return faresRepository.findById(id);
    }

    public List<Fares> getAllFares(){
        return faresRepository.findAll();
    }

    public List<String> getAllValues(String tableName) {
        return faresRepository.getTableValues(tableName);
    }

    public List<Integer> getIDs(String tableName) {
        return faresRepository.getIDs(tableName);
    }
}
