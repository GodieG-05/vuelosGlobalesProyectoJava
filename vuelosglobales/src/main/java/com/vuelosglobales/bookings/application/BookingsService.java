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
    
    public int getLastId(){
        return BookingsRepository.getLastId();
    }
    public void createBookings(Bookings DocumentType){
        BookingsRepository.save(DocumentType);
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
 