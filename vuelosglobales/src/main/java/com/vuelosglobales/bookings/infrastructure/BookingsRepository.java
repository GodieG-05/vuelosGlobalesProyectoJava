package com.vuelosglobales.bookings.infrastructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.bookings.domain.models.Bookings;

public interface BookingsRepository {
    int getLastId();
    void save(Bookings booking);
    void delete(int id);
    Optional<Bookings> findById(int id);
    List<Bookings> findAll();
    List<String> getTableValues(String tableName);
    List<Integer> getIDs(String tableName);
}