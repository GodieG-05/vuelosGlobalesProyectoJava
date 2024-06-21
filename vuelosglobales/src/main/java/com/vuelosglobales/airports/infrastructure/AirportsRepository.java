package com.vuelosglobales.airports.infrastructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.airports.domain.models.Airports;

public interface AirportsRepository {
    void save(Airports airport);
    void update(Airports airport);
    Optional<Airports> findById(String id);
    void delete(String id);
    List<Airports> findAll();
}