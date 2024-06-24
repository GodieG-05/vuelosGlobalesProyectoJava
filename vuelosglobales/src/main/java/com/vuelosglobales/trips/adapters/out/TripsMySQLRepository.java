package com.vuelosglobales.trips.adapters.out;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vuelosglobales.trips.domain.models.Trips;
import com.vuelosglobales.trips.infrastructure.TripsRepository;

public class TripsMySQLRepository implements TripsRepository{
    private final String url;
    private final String user;
    private final String password;

    public TripsMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    @Override
    public void assignX(Object idTrip, Object idX, String tablename) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query1 = "SELECT id FROM flight_connections WHERE id_trip = " + idTrip + " LIMIT 1";
            int idConnection;
            try (PreparedStatement statement = connection.prepareStatement(query1);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idConnection = resultSet.getInt("id");
                } else { idConnection = -1; }
            }
            String query2;
            switch (tablename) {
                case "employee":
                    query2 = "INSERT INTO trip_crews (id_employee, id_connection) VALUES (?,?)";
                    try (PreparedStatement statement = connection.prepareStatement(query2)) {
                        statement.setObject(1, idX);
                        statement.setObject(2, idConnection);
                        statement.executeUpdate();
                    }    
                    break;
                case "planes":
                    query2 = "INSERT INTO flight_connections (id_trip, id_plane) VALUES (?,?)";
                    try (PreparedStatement statement = connection.prepareStatement(query2)) {
                        statement.setObject(1, idTrip);
                        statement.setObject(2, idX);
                        statement.executeUpdate();
                    }    
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }       
    }

    @Override
    public void update(Trips trip) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE trips SET trip_date = ?, price_trip = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, trip.getTripDate());
                statement.setDouble(2, trip.getPriceTrip());
                statement.setInt(3, trip.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query1 = """
                DELETE FROM trip_booking_details tp 
                WHERE id_trip_booking = (
                    SELECT id FROM trip_booking WHERE id_trip = ?  LIMIT 1 
                    )""";
            try (PreparedStatement statement = connection.prepareStatement(query1)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query2 = "DELETE FROM trip_booking WHERE id_trip = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query3 = """
                DELETE FROM trip_crews WHERE id_connection = (
                    SELECT id FROM flight_connections WHERE id_trip = ? LIMIT 1
                )""";
            try (PreparedStatement statement = connection.prepareStatement(query3)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query4 = "DELETE FROM flight_connections WHERE id_trip = ?";
            try (PreparedStatement statement = connection.prepareStatement(query4)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query5 = "DELETE FROM trips WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query5)){
                statement.setInt(1, id);
                statement.executeUpdate();
            }
                    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Trips> findAll() {
        List<Trips> trips = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM trips";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Trips trip = new Trips(
                        resultSet.getInt("id"),
                        resultSet.getDate("trip_date"),
                        resultSet.getDouble("price_trip")
                    );
                    trips.add(trip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    @Override
    public Optional<Trips> findById(int id) {
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM trips WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Trips trip = new Trips(
                            resultSet.getInt("id"),
                            resultSet.getDate("trip_date"),
                            resultSet.getDouble("price_trip")
                        );
                        return Optional.of(trip);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<String> findAssignation(Object idTrip, Object idXObject, String tableName) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query1 = "SELECT id FROM flight_connections WHERE id_trip = " + idTrip + " LIMIT 1";
            int idConnection;
            String idX = String.valueOf(idXObject);
            try (PreparedStatement statement = connection.prepareStatement(query1);
                 ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    idConnection = resultSet.getInt("id");
                } else { idConnection = -1; }
            }
            String query2;
            switch (tableName) {
                case "employees":
                    query2 = """
                            SELECT e.id, e.name, t.id AS id_trip FROM employees e
                            LEFT JOIN trip_crews tc ON e.id = tc.id_employee
                            LEFT JOIN flight_connections c ON tc.id_connection = c.id
                            LEFT JOIN trips t ON c.id_trip= t.id
                            WHERE tc.id_employee = ? AND tc.id_connection = ? LIMIT 1
                            """;
                    try (PreparedStatement statement1 = connection.prepareStatement(query2)) {
                        statement1.setString(1, idX);
                        statement1.setInt(2, idConnection);
                        try (ResultSet resultSet = statement1.executeQuery()) {
                            if (resultSet.next()) {
                                String assignations = "[id=" + resultSet.getString("id") + ", name=" + resultSet.getString("name") + ", id_trip=" + resultSet.getInt("id_trip") + "]";
                                return Optional.of(assignations);
                            }
                        }
                    } 
                    break; 
                case "planes":
                    query2 = """
                            SELECT p.id, p.plates, t.id AS id_trip FROM planes p
                            LEFT JOIN flight_connections c ON p.id = c.id_plane
                            LEFT JOIN trips t ON c.id_trip= t.id
                            WHERE c.id_plane = ? AND c.id_trip = ? LIMIT 1
                            """;
                    try (PreparedStatement statement1 = connection.prepareStatement(query2)) {
                        statement1.setString(1, idX);
                        statement1.setObject(2, idTrip);
                        try (ResultSet resultSet = statement1.executeQuery()) {
                            if (resultSet.next()) {
                                String assignations = "[id=" + resultSet.getInt("id") + ", plates=" + resultSet.getString("plates") + ", id_trip=" + resultSet.getInt("id_trip") + "]";
                                return Optional.of(assignations);
                            }
                        }
                    } 
                    break;
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<String>> findTripulation (int idTrip){
        ArrayList<String> tripulationArray = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = """
                SELECT t.id AS id_trip, e.id, e.name
                FROM trips t
                JOIN flight_connections fc ON t.id = fc.id_trip
                JOIN trip_crews tc ON fc.id = tc.id_connection
                JOIN employees e ON tc.id_employee = e.id
                WHERE t.id = ?
                    """;
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, idTrip);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            while (resultSet.next()) {
                                String tripulation = "[id_trip=" + resultSet.getInt("id_trip") + ", id_employee=" + resultSet.getString("id") + ", name=" + resultSet.getString("name") + "]";
                                tripulationArray.add(tripulation);
                            }
                            return Optional.of(tripulationArray);
                        }
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ArrayList<String>> findScales(int idTrip) {
        ArrayList<String> scalesArray = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = """
                SELECT fc.id_trip, c1.name AS oc, c2.name AS dc FROM flight_connections fc 
                JOIN airports a1 on fc.id_origin = a1.id
                JOIN airports a2 on fc.id_destination = a2.id
                JOIN cities c1 on a1.id_city = c1.id
                JOIN cities c2 on a2.id_city = c2.id
                WHERE id_trip = ?
                    """;
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, idTrip);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String scale = "[id_trip=" + resultSet.getInt("id_trip") + ", origin_city=" + resultSet.getString("oc") + ", destination_city=" + resultSet.getString("dc") + "]";
                            scalesArray.add(scale);
                        }
                    }
                    return Optional.of(scalesArray);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;
            switch (tableName) {
                case "trips":
                    query = "SELECT id, trip_date, price_trip FROM " + tableName;
                    break;
                case "employees":
                    query = "SELECT id, name FROM " + tableName;
                    break;
                case "planes":
                    query = "SELECT id, plates FROM " + tableName;
                    break;
                default:
                    query = "";
                    break;
            }
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    String value = "";
                    while (resultSet.next()) {
                        switch (tableName) {
                            case "trips":
                                int idTrip = resultSet.getInt("id");
                                Date tripDate = resultSet.getDate("trip_date");
                                Double priceTrip = resultSet.getDouble("price_trip");
                                value = "[id=" + idTrip + ", trip_date=" + tripDate + ", price_trip=" + priceTrip + "]";
                                break;
                            case "employees":
                                String idEmployee = resultSet.getString("id");
                                String name = resultSet.getString("name");
                                value = "[id=" + idEmployee + ", name=" + name + "]";
                                break;
                            case "planes":
                                String idPLane = resultSet.getString("id");
                                String plates = resultSet.getString("plates");
                                value = "[id=" + idPLane + ", plates=" + plates + "]";
                                break;
                        }
                        values.add(value);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public List<Object> getIDs(String tableName) {
        List<Object> IDsLsit = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id FROM " + tableName;                
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Object id = resultSet.getObject("id");
                        IDsLsit.add(id);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDsLsit;
    }
}
