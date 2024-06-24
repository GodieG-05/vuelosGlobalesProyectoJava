package com.vuelosglobales.planes.application;

import com.vuelosglobales.planes.domain.models.Plane;
import com.vuelosglobales.planes.infraestructure.PlaneRepository;
import java.util.List;
import java.util.Optional;

public class PlaneService {
    private final PlaneRepository planeRepository;

    public PlaneService(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    public int getLastId(){
        return planeRepository.getLastId();
    }

    public void createPlane(Plane plane) {
        planeRepository.save(plane);
    }

    public void updatePlane(Plane plane) {
        planeRepository.update(plane);
    }

    public Optional<Plane> getPlaneById(int id) {
        return planeRepository.findById(id);
    }

    public void deletePlane(int id) {
        planeRepository.delete(id);
    }

    public List<String> getAllValues(String tableName) {
        return planeRepository.getTableValues(tableName);
    }

    public List<Integer> getIDs(String tableName) {
        return planeRepository.getIDs(tableName);
    }
}
