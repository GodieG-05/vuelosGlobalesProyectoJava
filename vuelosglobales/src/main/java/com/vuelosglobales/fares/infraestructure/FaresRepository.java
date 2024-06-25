package com.vuelosglobales.fares.infraestructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.fares.domain.models.Fares;

public interface FaresRepository {
    int getLastId();
    void save(Fares flightFare);
    void update(Fares flightFare);
    void delete(int id);
    Optional<Fares> findById(int id);
    List<Fares> findAll();
    List<String> getTableValues(String tableName);
    List<Integer> getIDs(String tableName);

}
