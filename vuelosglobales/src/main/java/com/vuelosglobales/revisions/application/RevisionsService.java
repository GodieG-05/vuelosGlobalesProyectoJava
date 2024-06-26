package com.vuelosglobales.revisions.application;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.revisions.domain.models.Revisions;
import com.vuelosglobales.revisions.infraestructure.RevisionsRepository;

public class RevisionsService {
    private final RevisionsRepository RevisionRepository;

    public RevisionsService(RevisionsRepository revisionRepository){
        this.RevisionRepository = revisionRepository;
    }

    public int getLastId() {
        return RevisionRepository.getLastId();
    }

    public void createRevision(Revisions revision) {
        RevisionRepository.save(revision);
    }

    public void updateRevision(Revisions revision) {
        RevisionRepository.update(revision);
    }

    public void deleteRevision(int id) {
        RevisionRepository.delete(id);
    }

    public Optional<Revisions> getRevisionById(int id) {
        return RevisionRepository.findById(id);
    }

    public List<String> getAllValues(String tableName) {
        return RevisionRepository.getTableValues(tableName);
    }

    public List<Integer> getIDs(String tableName){
        return RevisionRepository.getIDs(tableName);
    }
}
