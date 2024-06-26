package com.vuelosglobales.customers.adapters.out;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.vuelosglobales.customers.infraestructure.CustomersRepository;
import com.vuelosglobales.customers.domain.models.Customers;

public class CustomersMySQLRepository implements CustomersRepository{
    private final String url;
    private final String user;
    private final String password;

    public CustomersMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void save(Customers customer){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO customers (id, name, age, id_document) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, customer.getId());
                statement.setString(2, customer.getName());
                statement.setInt(3, customer.getAge());
                statement.setInt(4, customer.getIdDocument());
                statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customers customer){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "UPDATE customers SET name = ?, age = ?, id_document = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, customer.getName());
                statement.setInt(2, customer.getAge());
                statement.setInt(3, customer.getIdDocument());
                statement.setString(4, customer.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customers> findByID(Object id){
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "SELECT * FROM customers WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setObject(1, id);
                try (ResultSet resultSet = statement.executeQuery()){
                    if (resultSet.next()){
                        Customers customer = new Customers(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age"),
                            resultSet.getInt("id_document")
                        );
                        return Optional.of(customer);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Customers> findAll(){
        List<Customers> customers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM customers";
            try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Customers customer = new Customers(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("id_document")
                    );
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM " + tableName;
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
                                Object id = resultSet.getObject("id");
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
