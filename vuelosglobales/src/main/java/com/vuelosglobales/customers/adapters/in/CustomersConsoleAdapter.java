package com.vuelosglobales.customers.adapters.in;
 
import java.text.MessageFormat;
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

    public void printAllValues(String tableName){
        List<String> valuesList = customerService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public Object existsId(String txt, String errMessage1, String errMessage2, Scanner sc, Boolean returnInt, String tableName){
        List<Object> IDsLsit = customerService.getIDs(tableName);
        printAllValues(tableName);
        if (returnInt) {
            int fId;
            do {
                fId = Main.validInt(sc, errMessage1, txt);
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

    public void registrarCliente(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            System.out.print("Ingrese el id del cliente: ");
            String id = sc.nextLine();
            System.out.print("Ingrese el nombre del cliente: ");
            String name = sc.nextLine();
            int age = Main.validInt(sc, errMessage, "Ingrese la edad del cliente: ");
            System.out.println("\nTipos de documentos:\n");
            Object idDocument = existsId("\nIngrese el id del documento del cliente: ", errMessage, "\nTipo de documento no encontrado, Intente de nuevo", sc, true, "document_types");
            Customers newCustomer = new Customers(id, name, age, (Integer)idDocument);
            customerService.createCustomer(newCustomer);
            Optional<Customers> createdCustomer = customerService.getCustomerById(id);
            createdCustomer.ifPresentOrElse(c -> System.out.println("\nEl cliente: " + c.toString() + " fue registrado correctamente"), () -> System.out.println("Error al crear el cliente"));
            System.out.println("\n¿Desea ingresar otro cliente? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarCliente(String header, String errMessage, Scanner sc, String rta) {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Clientes:\n");
            Object id = existsId("\nIngrese el id del cliente a actualizar: ", errMessage, "\nCliente no encontrado, Intente de nuevo", sc, false, "customers");
            Optional<Customers> selectedCustomer = customerService.getCustomerById(id);
            if(selectedCustomer.isPresent()){
                System.out.println("\nCliente seleccionado: \n" + selectedCustomer.get().toString());
                System.out.print("\nIngrese el nuevo nombre del cliente: ");
                String name = sc.nextLine();
                int age = Main.validInt(sc, errMessage, "Ingrese la nueva edad del cliente: ");
                System.out.println("\nTipos de documentos:\n");
                Object idDocument = existsId("\nIngrese el id del nuevo documento del cliente: ", errMessage, "\nTipo de documento no encontrado, Intente de nuevo", sc, true, "document_types");
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

    public void consultarCliente(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Clientes:\n");
            Object id = existsId("\nIngrese el id del cliente a consultar: ", errMessage, "\nCliente no encontrado, Intente de nuevo", sc, false, "customers");
            Optional<Customers> customer = customerService.getCustomerById(id);
            if (customer.isPresent()) { System.out.println(customer.get().toString()); } else {
                System.out.println("\nCliente no encontrado");
            }
            System.out.println("\nDesea consultar otro cliente? si/ENTER");
            rta = sc.nextLine();
        }
    }

        public void start() {
        
        String header = """
            ------------
            | CLIENTES |
            ------------
            """;
        String[] menu = {"Registrar clientes","Actualizar clientes","Consultar clientes","Salir"};
        
        String errMessage = "\nEl dato ingresado es incorrecto, intentelo de nuevo ";
        
        Scanner sc = new Scanner(System.in);
        boolean isActive = true;
        mainLoop:
        while (isActive) {
            Main.clearScreen();
            System.out.println(header);
            
            for (int i = 0; i < menu.length; i++) {
                System.out.println(MessageFormat.format("{0}. {1}.", (i+1), menu[i]));
            }
            int op = Main.validInt(sc, errMessage, "-> ");
            if(op == -1){
                continue mainLoop;
            }
            String rta = " ";
            switch (op) {
                case 1:
                    registrarCliente(header, errMessage, sc, rta);
                    break;
                case 2:
                    actualizarCliente(header, errMessage, sc, rta);
                    break;
                case 3:
                    consultarCliente(header, errMessage, sc, rta);
                    break;                   
                case 4:
                    isActive = false;     
                    break; 
                default:
                    System.out.println(errMessage);
                    sc.nextLine();
                    break;
            } 
        } 
        sc.close();
    }
}