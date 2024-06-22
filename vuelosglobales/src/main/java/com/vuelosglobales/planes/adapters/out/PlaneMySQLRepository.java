package com.vuelosglobales.planes.adapters.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.vuelosglobales.planes.domain.models.Plane;
import com.vuelosglobales.planes.infraestructure.PlaneRepository;


//falta importar


public class PlaneMySQLRepository implements PlaneRepository{
    private final String url;
    private final String user;
    private final String password;
    
    public PlaneMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void save (Plane plane) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO planes (plates, capacity, fabricationDate, idStatus, idModel) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, plane.getPlates());
                statement.setInt(2, plane.getCapacity());
                statement.setDate(3, plane.getFabricationDate());
                statement.setInt(4, plane.getIdStatus());
                statement.setInt(5, plane.getIdModel());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Plane plane) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE planes SET plates = ?, capacity = ?, fabricationDate = ?, idStatus = ?, idModels = ? WHERE id = ?"; // se debe actualizar el query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, plane.getPlates());
                statement.setInt(2, plane.getCapacity());
                statement.setDate(3, plane.getFabricationDate());
                statement.setInt(4, plane.getIdStatus());
                statement.setInt(5, plane.getIdModel());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Plane> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM planes WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Plane plane = new Plane(
                            resultSet.getInt("id"),
                            resultSet.getString("plates"),
                            resultSet.getInt("capacity"),
                            resultSet.getDate("fabricationDate"),
                            resultSet.getInt("idStatus"),
                            resultSet.getInt("idModel")
                        );
                        return Optional.of(plane);
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
            String query = "DELETE FROM plane WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Plane> findAll() {
        List<Plane> planes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM planes";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Plane plane = new Plane(
                        resultSet.getInt("id"),
                        resultSet.getString("plates"),
                        resultSet.getInt("capacity"),
                        resultSet.getDate("fabricationDate"),
                        resultSet.getInt("idStatus"),
                        resultSet.getInt("idModel")
                    );
                    planes.add(plane);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planes;
    }


    
}
