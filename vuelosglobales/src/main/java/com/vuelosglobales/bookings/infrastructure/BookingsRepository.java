package com.vuelosglobales.bookings.infrastructure;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.bookings.domain.models.Bookings;
import com.vuelosglobales.bookings.domain.models.Payment;

public interface BookingsRepository {
    int getLastId(String tableName);
    void save(Bookings booking);
    void savePayment(Payment payment);
    void update(Bookings booking);
    void delete(int id);
    Optional<Bookings> findById(int id);
    Optional<Payment> findPaymentById(int id);
    List<Bookings> findAll();
    List<String> getTableValues(String tableName);
    List<Integer> getIDs(String tableName);
}