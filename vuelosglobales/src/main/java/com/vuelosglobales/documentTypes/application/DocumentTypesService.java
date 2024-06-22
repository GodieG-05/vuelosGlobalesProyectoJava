package com.vuelosglobales.documentTypes.application;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.documentTypes.domain.models.DocumentTypes;
import com.vuelosglobales.documentTypes.infrastructure.DocumentTypesReporitory;


public class DocumentTypesService {
    private final DocumentTypesReporitory DocumentTypesReporitory;

    public DocumentTypesService(DocumentTypesReporitory DocumentTypesReporitory){
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

    public List<DocumentTypes> getAllDocumentTypes() {
        return DocumentTypesReporitory.findAll();
    }
}
