package com.vuelosglobales.customers.adapters.in;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.customers.application.CustomersService;
import com.vuelosglobales.customers.domain.models.Customers;

public class CustomersConsoleAdapter {
    private final CustomersService customerService;

    public CustomersConsoleAdapter(CustomersService customerService) {
        this.customerService = customerService;
    }

    String header = """
        ------------
        | CLIENTES |
        ------------
        """;
    String errMessage = "\nEl dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName){
        List<String> valuesList = customerService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public String correctPrimary(String txt, String errMessage, String tableName){
        List<Object> IDsLsit = customerService.getIDs(tableName);
        String pId;
        do {
            System.out.print(txt);
            pId = sc.nextLine();
            if (IDsLsit.contains(pId)) { System.out.println(errMessage); }
        } while (IDsLsit.contains(pId));
        return pId;
    }

    public Object existsId(String txt, String errMessage2, Boolean returnInt, String tableName){
        List<Object> IDsLsit = customerService.getIDs(tableName);
        printAllValues(tableName);
        if (returnInt) {
            int fId;
            do {
                fId = Main.validInt(sc, errMessage, txt);
                if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
            } while (!IDsLsit.contains(fId));
            return fId;
        } else {
            String fId;
            do {
                System.out.print(txt);
                fId = sc.nextLine();
                if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
            } while (!IDsLsit.contains(fId));
            return fId;
        }
    }

    public void registrarCliente(){
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            String id = correctPrimary("\nIngrese el id del cliente: ", "Este id ya fue utilizado, intentelo de nuevo", "customers");
            System.out.print("Ingrese el nombre del cliente: ");
            String name = sc.nextLine();
            int age = Main.validInt(sc, errMessage, "Ingrese la edad del cliente: ");
            System.out.println("\nTipos de documentos:\n");
            Object idDocument = existsId("\nIngrese el id del documento del cliente: ", "\nTipo de documento no encontrado, Intente de nuevo", true, "document_types");
            Customers newCustomer = new Customers(id, name, age, (Integer)idDocument);
            customerService.createCustomer(newCustomer);
            Optional<Customers> createdCustomer = customerService.getCustomerById(id);
            createdCustomer.ifPresentOrElse(c -> System.out.println("\nEl cliente: " + c.toString() + " fue registrado correctamente"), () -> System.out.println("Error al crear el cliente"));
            System.out.println("\n¿Desea ingresar otro cliente? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarCliente() {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Clientes:\n");
            Object id = existsId("\nIngrese el id del cliente a actualizar: ", "\nCliente no encontrado, Intente de nuevo", false, "customers");
            Optional<Customers> selectedCustomer = customerService.getCustomerById(id);
            if(selectedCustomer.isPresent()){
                System.out.println("\nCliente seleccionado: \n" + selectedCustomer.get().toString());
                System.out.print("\nIngrese el nuevo nombre del cliente: ");
                String name = sc.nextLine();
                int age = Main.validInt(sc, errMessage, "Ingrese la nueva edad del cliente: ");
                System.out.println("\nTipos de documentos:\n");
                Object idDocument = existsId("\nIngrese el id del nuevo documento del cliente: ", "\nTipo de documento no encontrado, Intente de nuevo", true, "document_types");
                Customers customerToUpdate = new Customers(String.valueOf(id), name, age, (Integer)idDocument);
                customerService.updateCustomer(customerToUpdate);               
            }
            Optional<Customers> updatedCustomer = customerService.getCustomerById(id);
            updatedCustomer.ifPresentOrElse(c -> System.out.println("\nEl cliente: " + c.toString() + " fue actualizado correctamente."),
            () -> System.out.println("[¡] Error al actualizar cliente"));
            System.out.println("\n¿Desea actualizar otro cliente? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarCliente(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Clientes:\n");
            Object id = existsId("\nIngrese el id del cliente a consultar: ", "\nCliente no encontrado, Intente de nuevo", false, "customers");
            Optional<Customers> customer = customerService.getCustomerById(id);
            if (customer.isPresent()) { System.out.println(customer.get().toString()); } else {
                System.out.println("\nCliente no encontrado");
            }
            System.out.println("\nDesea consultar otro cliente? si/ENTER");
            rta = sc.nextLine();
        }
    }
}