package com.vuelosglobales.customers.application;

import com.vuelosglobales.customers.domain.models.Customers;
import com.vuelosglobales.customers.infraestructure.CustomersRepository;
import java.util.Optional;
import java.util.List;
 
public class CustomersService {
    private final CustomersRepository customerRepository;

    public CustomersService(com.vuelosglobales.customers.infraestructure.CustomersRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customers customer){
        customerRepository.save(customer);
    }

    public void updateCustomer(Customers customer){
        customerRepository.update(customer);
    }

    public Optional<Customers> getCustomerById(Object id){
        return customerRepository.findByID(id);
    }

    public List<Customers> getAllCustomers(){
        return customerRepository.findAll();
    }

    public List<String> getAllValues(String tableName) {
        return customerRepository.getTableValues(tableName);
    }

    public List<Object> getIDs(String tableName){
        return customerRepository.getIDs(tableName);
    }
}
