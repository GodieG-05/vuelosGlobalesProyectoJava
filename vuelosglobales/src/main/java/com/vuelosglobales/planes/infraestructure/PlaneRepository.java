package com.vuelosglobales.planes.infraestructure;
 import java.util.List;
 import java.util.Optional;
 
 import com.vuelosglobales.planes.domain.models.Plane;

public interface PlaneRepository {
    void save(Plane plane);
    void update(Plane plane);
    Optional<Plane> findById(int id);
    void delete(int id);
    List<Plane> findAll();
}
