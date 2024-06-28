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
    public int getLastId() {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT MAX(id) AS max_id FROM planes";
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
    public int getSeatings(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT capacity FROM planes WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("capacity");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void save (Plane plane) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO planes (plates, capacity, fabrication_date, id_status, id_model) VALUES (?,?,?,?,?)";
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
            String query = "UPDATE planes SET plates = ?, capacity = ?, fabrication_date = ?, id_status = ?, id_model = ? WHERE id = ?"; // se debe actualizar el query
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, plane.getPlates());
                statement.setInt(2, plane.getCapacity());
                statement.setDate(3, plane.getFabricationDate());
                statement.setInt(4, plane.getIdStatus());
                statement.setInt(5, plane.getIdModel());
                statement.setInt(6, plane.getId());
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
                            resultSet.getDate("fabrication_date"),
                            resultSet.getInt("id_status"),
                            resultSet.getInt("id_model")
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
            String query1 = "DELETE FROM revisions WHERE id_plane = ?";
            try (PreparedStatement statement = connection.prepareStatement(query1)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query2 = "DELETE FROM flight_connections WHERE id_plane = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query3 = "DELETE FROM planes WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query3)) {
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
                        resultSet.getDate("fabrication_date"),
                        resultSet.getInt("id_status"),
                        resultSet.getInt("id_model")
                    );
                    planes.add(plane);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planes;
    }



    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = (tableName == "planes") ? "SELECT id, plates FROM " + tableName : "SELECT id, name FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String value = "";
                        switch (tableName) {
                            case "planes":
                                int idPlane = resultSet.getInt("id");
                                String plates = resultSet.getString("plates");
                                value = "[id=" + idPlane + ", plates=" + plates + "]";
                                break;
                            default:
                                int id = resultSet.getInt("id");
                                String name = resultSet.getString("name");
                                value = "[id=" + id + ", name=" + name + "]";
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
