package com.vuelosglobales.airports.adapters.out;

import com.vuelosglobales.airports.domain.models.Airports;
import com.vuelosglobales.airports.infrastructure.AirportsRepository;
import java.sql.*;
import java.util.Optional;

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
            String query = "INSERT INTO airports (name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, airport.getName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Airports airport) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE airports SET name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, airport.getName());
                statement.setInt(2, airport.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Airports> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM airportes WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Airports airport = new Airports(
                            resultSet.getInt("id"),
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
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM airports WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}