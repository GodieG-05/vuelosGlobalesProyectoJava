package com.vuelosglobales.airports.infrastructure;

import com.vuelosglobales.airports.domain.models.Airports;
import java.util.Optional;

public interface AirportsRepository {
    void save(Airports airport);
    void update(Airports airport);
    Optional<Airports> findById(int id);
    void delete(int id);
}