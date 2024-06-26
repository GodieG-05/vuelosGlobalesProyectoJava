package com.vuelosglobales.airports.adapters.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.vuelosglobales.airports.domain.models.Airports;
import com.vuelosglobales.airports.infrastructure.AirportsRepository;
 
public class AirportsMySQLRepository implements AirportsRepository{
    private final String url;
    private final String user;
    private final String password;
 
    public AirportsMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void save(Airports airport) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO airports (id, name, id_city) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, airport.getId());
                statement.setString(2, airport.getName());
                statement.setString(3, airport.getIdCity());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Airports airport) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE airports SET name = ?, id_city = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, airport.getName());
                statement.setString(2, airport.getIdCity());
                statement.setString(3, airport.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Airports> findById(String id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM airports WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Airports airport = new Airports(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("id_city")
                        );
                        return Optional.of(airport);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query1 = "DELETE FROM flight_connections WHERE id_origin = ? OR id_destination = ?";
            try (PreparedStatement statement = connection.prepareStatement(query1)) {
                statement.setString(1, id);
                statement.setString(2, id);
                statement.executeUpdate();
            }
            String query2 = "UPDATE employees SET id_airport = NULL WHERE id_airport = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setString(1, id);
                statement.executeUpdate();
            }
            String query3 = "DELETE FROM gates WHERE id_airport = ?";
            try (PreparedStatement statement = connection.prepareStatement(query3)) {
                statement.setString(1, id);
                statement.executeUpdate();
            }
            String query4 = "DELETE FROM airports WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query4)) {
                statement.setString(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }

    @Override
    public List<Airports> findAll() {
        List<Airports> airports = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM airports";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Airports airport = new Airports(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("id_city")
                    );
                    airports.add(airport);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id, name FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String id = resultSet.getString("id");
                        String name = resultSet.getString("name");
                        String value = "[id=" + id + ", name=" + name + "]";
                        values.add(value);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public List<String> getIDs(String tableName) {
        List<String> IDsLsit = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String id = resultSet.getString("id");
                        IDsLsit.add(id);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDsLsit;
    }

}