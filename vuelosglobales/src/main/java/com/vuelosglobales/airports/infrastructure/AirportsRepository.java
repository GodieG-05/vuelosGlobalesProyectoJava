package com.vuelosglobales.airports.infrastructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.airports.domain.models.Airports;

public interface AirportsRepository {
    void save(Airports airport);
    void update(Airports airport);
    void delete(String id);
    Optional<Airports> findById(String id);
    List<Airports> findAll();
    List<String> getTableValues(String tableName);
    List<String> getIDs(String tableName);
}