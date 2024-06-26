package com.vuelosglobales.documentTypes.application;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.documentTypes.domain.models.DocumentTypes;
import com.vuelosglobales.documentTypes.infrastructure.BookingsRepository;


public class BookingsService {
    private final BookingsRepository DocumentTypesReporitory;

    public BookingsService(BookingsRepository DocumentTypesReporitory){
        this.DocumentTypesReporitory = DocumentTypesReporitory; 
    }
    
    public int getLastId(){
        return DocumentTypesReporitory.getLastId();
    }
    public void createDocumentTypes(DocumentTypes DocumentType){
        DocumentTypesReporitory.save(DocumentType);
    }

    public void updateDocumentTypes(DocumentTypes DocumentType){
        DocumentTypesReporitory.update(DocumentType);
    }

    public Optional<DocumentTypes> getDocumentTypesById(int id){
        return DocumentTypesReporitory.findById(id);
    }

    public void deleteDocumentTypes(int id) {
        DocumentTypesReporitory.delete(id);
    }

    public List<String> getAllValues(String tableName) {
        return DocumentTypesReporitory.getTableValues(tableName);
    }

    public List<Integer> getIDs(String tableName) {
        return DocumentTypesReporitory.getIDs(tableName);
    }
}
