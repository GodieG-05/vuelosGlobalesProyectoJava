package com.vuelosglobales.bookings.application;

import java.util.List;
import java.util.Optional;

import com.vuelosglobales.bookings.domain.models.Bookings;
import com.vuelosglobales.bookings.infrastructure.BookingsRepository;

public class BookingsService {
    private final BookingsRepository BookingsRepository;

    public BookingsService (BookingsRepository BookingsRepository){
        this.BookingsRepository = BookingsRepository;
    }
    
    public int getLastId(String tableName){
        return BookingsRepository.getLastId(tableName);
    }
    public void createBookings(Bookings Booking){
        BookingsRepository.save(Booking);
    }

    public void updateBookings(Bookings Booking){
        BookingsRepository.save(Booking);
    }

    public Optional<Bookings> getBookingById(int id){
        return BookingsRepository.findById(id);
    }

    public void deleteBookings(int id) {
        BookingsRepository.delete(id);
    }

    public List<String> getAllValues(String tableName) {
        return BookingsRepository.getTableValues(tableName);
    }

    public List<Integer> getIDs(String tableName) {
        return BookingsRepository.getIDs(tableName);
    }
}
 