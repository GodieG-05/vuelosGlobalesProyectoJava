package com.vuelosglobales.customers.infraestructure;

import com.vuelosglobales.customers.domain.models.Customers;
import java.util.Optional;
import java.util.List;

public interface CustomersRepository {
    void save(Customers customer);
    void update(Customers customer);
    Optional<Customers> findByID(Object id);
    List<Customers> findAll();
    List<String> getTableValues(String tableName);
    List<Object> getIDs(String tableName);
}
 