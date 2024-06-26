package com.vuelosglobales.revisions.infraestructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.revisions.domain.models.Revisions;

public interface RevisionsRepository {
    int getLastId();
    void save(Revisions revision);
    void update(Revisions revision);
    void delete(int id);
    Optional<Revisions> findById(int id);
    List<Revisions> findAll();
    List<String> getTableValues(String tableName);
    List<Integer> getIDs(String tableName);
}
