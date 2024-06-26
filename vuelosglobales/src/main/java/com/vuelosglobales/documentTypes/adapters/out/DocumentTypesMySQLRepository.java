package com.vuelosglobales.documentTypes.adapters.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vuelosglobales.documentTypes.domain.models.DocumentTypes;
import com.vuelosglobales.documentTypes.infrastructure.BookingsRepository;

public class DocumentTypesMySQLRepository implements BookingsRepository{
    private final String url;
    private final String user;
    private final String password;

    public DocumentTypesMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public int getLastId(){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT MAX(id) AS max_id FROM document_types";
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
    public void save(DocumentTypes documentType) {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "INSERT INTO document_types (name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, documentType.getName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
    
    @Override
    public void update(DocumentTypes documentType) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE document_types SET name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, documentType.getName());
                statement.setInt(2, documentType.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<DocumentTypes> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM document_types WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        DocumentTypes documentType = new DocumentTypes(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                        );
                        return Optional.of(documentType);
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
            String query1 = "UPDATE customers SET id_document = NULL WHERE id_document = ?";
            try (PreparedStatement statement = connection.prepareStatement(query1)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
            String query2 = "DELETE FROM document_types WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DocumentTypes> findAll() {
        List<DocumentTypes> documentTypesList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM document_types";
            try (PreparedStatement statement = connection.prepareStatement(query); 
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DocumentTypes documentType = new DocumentTypes(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                    );
                    documentTypesList.add(documentType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentTypesList;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id, name FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
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
