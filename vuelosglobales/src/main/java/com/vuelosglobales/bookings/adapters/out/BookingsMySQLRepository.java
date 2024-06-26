package com.vuelosglobales.bookings.adapters.out;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import com.vuelosglobales.bookings.domain.models.Bookings;
import com.vuelosglobales.bookings.infrastructure.BookingsRepository;

public class BookingsMySQLRepository implements BookingsRepository{
    private final String url;
    private final String user;
    private final String password;

    public BookingsMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    @Override
    public int getLastId() {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT MAX(id) AS max_id FROM trip_booking";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()){
                        int maxId = resultSet.getInt("max_id");
                        return maxId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return -1;
    }

    @Override
    public void save(Bookings booking) {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "INSERT INTO trip_booking (date, id_trip) VALUES (?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setDate(1, booking.getDate());
                statement.setInt(2, booking.getIdTrip());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query1 = "DELETE FROM trip_booking_details WHERE id_trip_booking = ?";
            try (PreparedStatement statement = connection.prepareStatement(query1)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query2 = "DELETE FROM trip_booking WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Bookings> findAll() {
        List<Bookings> bookingsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM trip_booking";
            try (PreparedStatement statement = connection.prepareStatement(query); 
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bookings bookings = new Bookings(
                        resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getInt("id_trip")
                    );
                    bookingsList.add(bookings);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsList;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;
            switch (tableName) {
                case "trips":
                    query = "SELECT * FROM trips";
                    try (PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                Date tripDate = resultSet.getDate("trip_date");
                                int priceTrip = resultSet.getInt("price_trip");
                                String value = "[id=" + id + ", date=" + tripDate + ", id_trip=" + priceTrip + "]";
                                values.add(value);
                            }
                    }
                    break;
                case "trip_booking":
                    query = "SELECT * FROM trip_booking";
                    try (PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                Date date = resultSet.getDate("date");
                                int idTrip = resultSet.getInt("id_trip");
                                String value = "[id=" + id + ", date=" + date + ", id_trip=" + idTrip + "]";
                                values.add(value);
                            }
                    }
                    break;
                case "flight_connections":
                    query = "SELECT id, id_origin, id_destination FROM flight_connections";
                    try (PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String idOrg = resultSet.getString("id_origin");
                                String idDes = resultSet.getString("id_destination");
                                String value = "[id=" + id + ", id_origin=" + idOrg + ", id_destination=" + idDes + "]";
                                values.add(value);
                            }
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public Optional<Bookings> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM trip_booking WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Bookings booking = new Bookings(
                            resultSet.getInt("id"),
                            resultSet.getDate("date"),
                            resultSet.getInt("id_trip")
                        );
                        return Optional.of(booking);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Integer> getIDs(String tableName) {
        List<Integer> IDsLsit = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        IDsLsit.add(id);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDsLsit;
    }

}