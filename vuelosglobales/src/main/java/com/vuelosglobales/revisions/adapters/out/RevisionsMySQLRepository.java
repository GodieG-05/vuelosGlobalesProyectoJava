package com.vuelosglobales.revisions.adapters.out;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.vuelosglobales.revisions.domain.models.Revisions;
import com.vuelosglobales.revisions.infraestructure.RevisionsRepository;

public class RevisionsMySQLRepository implements RevisionsRepository{
    private final String url;
    private final String user;
    private final String password;

    public RevisionsMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public int getLastId() {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT MAX(id) AS max_id FROM revisions";
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
    public void save (Revisions revision){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "INSERT INTO revisions (revision_date, id_plane) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, revision.getRevisionDate());
                statement.setInt(2, revision.getIdPlane());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Revisions revision) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE revisions SET revision_date = ?, id_plane = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setDate(1, revision.getRevisionDate());
                statement.setInt(2, revision.getIdPlane());
                statement.setInt(3, revision.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM revisions WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Revisions> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT * FROM revisions WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Revisions revision = new Revisions(
                            resultSet.getInt("id"),
                            resultSet.getDate("revision_date"),
                            resultSet.getInt("id_plane")
                        );
                        return Optional.of(revision);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Revisions> findAll() {
        List<Revisions> revisionsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECTED * FROM revisions";
            try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Revisions revision = new Revisions(
                        resultSet.getInt("id"),
                        resultSet.getDate("revision_date"),
                        resultSet.getInt("id_plane")
                    );
                    revisionsList.add(revision);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revisionsList;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;
            switch (tableName) {
                case "planes":   
                    query = "SELECT id, plates FROM " + tableName;
                    try (PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String plates = resultSet.getString("plates");
                                String value = "[id=" + id + ", plates=" + plates + "]";
                                values.add(value);
                            }
                    }
                    break;
                case "revisions":   
                    query = "SELECT * FROM " + tableName;
                    try (PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                Date revDate = resultSet.getDate("revision_date");
                                int idPlane = resultSet.getInt("id_plane");
                                String value = "[id=" + id + ", revision_date=" + revDate + "id_plane" + idPlane + "]";
                                values.add(value);
                            }
                    }
                    break;
                default:
                    query = "SELECT id, name FROM " + tableName;
                    try (PreparedStatement statement = connection.prepareStatement(query);
                        ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String name = resultSet.getString("name");
                                String value = "[id=" + id + ", name=" + name + "]";
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
