package com.vuelosglobales.documentTypes.infrastructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.documentTypes.domain.models.DocumentTypes;

public interface DocumentTypesReporitory {
    int getLastId();
    void save(DocumentTypes documentTypes);
    void update(DocumentTypes documentTypes);
    void delete(int id);
    Optional<DocumentTypes> findById(int id);
    List<DocumentTypes> findAll();
}
