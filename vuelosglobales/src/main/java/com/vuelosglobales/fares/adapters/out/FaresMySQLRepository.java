package com.vuelosglobales.fares.adapters.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.vuelosglobales.fares.domain.models.Fares;
import com.vuelosglobales.fares.infraestructure.FaresRepository;

public class FaresMySQLRepository implements FaresRepository {
    private final String url;
    private final String user;
    private final String password;

    public FaresMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public int getLastId() {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT MAX(id) AS max_id FROM flight_fares";
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
    public void save (Fares flightFare){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "INSERT INTO flight_fares (description, details, value) VALUES (?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, flightFare.getDescrition());
                statement.setString(2, flightFare.getDetails());
                statement.setDouble(3, flightFare.getValue());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Fares flightFare) {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "UPDATE flight_fares SET description = ?, details = ?, value = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, flightFare.getDescrition());
                statement.setString(2, flightFare.getDetails());
                statement.setDouble(3, flightFare.getValue());
                statement.setInt(4, flightFare.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override  
    public Optional<Fares> findById(int id){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT * FROM flight_fares WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()){
                    if (resultSet.next()){
                        Fares flightFare = new Fares(
                            resultSet.getInt("id"),
                            resultSet.getString("description"),
                            resultSet.getString("details"),
                            resultSet.getDouble("value")
                        );
                        return Optional.of(flightFare);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(int id){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query2 = "DELETE FROM flight_fares WHERE id = ?";
            String query1 = "UPDATE trip_booking_details SET id_fares = NULL WHERE id_fares = ?";
            try (PreparedStatement statement = connection.prepareStatement(query1)){
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(query2)){
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Fares> findAll(){
        List<Fares> flightFares = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT * FROM flight_fares";
            try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    Fares flightFare = new Fares(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("details"),
                        resultSet.getDouble("value")

                    );
                    flightFares.add(flightFare);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightFares;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id, description, value FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String value = "[id=" + resultSet.getInt("id") + ", details=" + resultSet.getString("description") + ", value" + resultSet.getString("value") +"]";
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
