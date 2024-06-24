package com.vuelosglobales.planes.infraestructure;

import java.util.List;
import java.util.Optional;
 
import com.vuelosglobales.planes.domain.models.Plane;

public interface PlaneRepository {
    int getLastId();
    void save(Plane plane);
    void update(Plane plane);
    void delete(int id);
    Optional<Plane> findById(int id);
    List<Plane> findAll();
    List<String> getTableValues(String tableName);
    List<Integer> getIDs(String tableName);
}