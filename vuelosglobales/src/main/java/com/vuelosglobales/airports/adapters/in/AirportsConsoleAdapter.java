package com.vuelosglobales.airports.adapters.in;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.airports.application.AirportsService;
import com.vuelosglobales.airports.domain.models.Airports;

public class AirportsConsoleAdapter {

    private final AirportsService airportService;

    public AirportsConsoleAdapter(AirportsService airportService){
        this.airportService = airportService;
    }
    
    String header = """
        ---------------
        | AEROPUERTOS |
        ---------------
        """;
    String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName) {
        List<String> valuesList = airportService.getTableValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public String correctPrimary(String txt, String errMessage, String tableName){
        List<String> IDsLsit = airportService.getIDs(tableName);
        String pId;
        do {
            System.out.print(txt);
            pId = sc.nextLine();
            if (IDsLsit.contains(pId)) { System.out.println(errMessage); }
        } while (IDsLsit.contains(pId));
        return pId;
    }

    public String existsId(String txt, String errMessage, String tableName){
        List<String> IDsLsit = airportService.getIDs(tableName);
        printAllValues(tableName);
        String fId;
        do {
            System.out.print(txt);
            fId = sc.nextLine();
            if (!IDsLsit.contains(fId)) { System.out.println(errMessage); }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarAeropuerto(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            String id = correctPrimary("\nIngrese el id del aeropuerto: ", 
            "Este id ya fue utilizado, intentelo de nuevo","airports");
            System.out.print("Ingrese el nombre del aeropuerto: ");
            String name = sc.nextLine();
            System.out.println("\nCiudades:");
            String idCity = existsId("\nIngrese el id de la ciudad: ", 
            "Ciudad no encontrada, Intente de nuevo", "cities");
            Airports newAirport = new Airports(id, name, idCity);
            airportService.createAirports(newAirport);
            Optional<Airports> createdAirport = airportService.getAirportsById(id);
            createdAirport.ifPresentOrElse(a -> System.out.println("\nEl aeropuerto: " + a.toString() + " fue registrado correctamente."), 
            () -> System.out.println("El aeropuerto no fue registrado correctamente"));
            System.out.print("\nDesea ingresar otro aeropuerto? si/ENTER ");
            rta = sc.nextLine();
        }
    }
    
    public void actualizarAeropuerto(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Aeropuertos:\n");
            String id = existsId("\nIngrese el id del aeropuerto a actualizar: ", 
            "Aeropuerto no encontrado, Intente de nuevo","airports");
            Optional<Airports> selectedAirport = airportService.getAirportsById(id);
            if(selectedAirport.isPresent()){
                System.out.println("\nAeropuerto seleccionado: \n" + selectedAirport.get().toString());
                System.out.print("\nIngrese el nuevo nombre del aeropuerto: ");
                String name = sc.nextLine();
                System.out.println("\nCiudades:");
                String idCity = existsId("\nIngrese el nuevo id de la ciudad: ", 
                "Ciudad no encontrada, Intente de nuevo", "cities");
                Airports airportToUpdate = new Airports(id, name, idCity);
                airportService.updateAirports(airportToUpdate);
            }
            Main.clearScreen();
            System.out.println(header);
            Optional<Airports> updatedAirport = airportService.getAirportsById(id);
            if (updatedAirport.isEmpty()) { System.out.println("Aeropuerto actualizado exitosamente"); }
            System.out.print("\nDesea actualizar otro aeropuerto? si/ENTER ");
            rta = sc.nextLine();
        }
    }

    public void consultarAeropuerto(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Aeropuertos:\n");
            String id = existsId("\nIngrese el ID del aeropuerto a consultar: ", 
            "Aeropuerto no encontrado, Intente de nuevo", "airports"); 
            Optional<Airports> selectedAirport = airportService.getAirportsById(id);
            if (selectedAirport.isPresent()) { System.out.println(selectedAirport.get().toString()); }
            System.out.print("\nDesea consultar otro aeropuerto? si/ENTER ");
            rta = sc.nextLine();
        }
    }

    public void elimninarAeropuerto(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Aeropuertos:\n");
            String id = existsId("\nIngrese el ID del aeropuerto a consultar: ", 
            "\nAeropuerto no encontrado, Intente de nuevo", "airports");  
            Optional<Airports> airport = airportService.getAirportsById(id);
            if(airport.isPresent()){
                System.out.println(MessageFormat.format("\nEl aerpuerto {0} será eliminado", airport.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if(cnf.isEmpty()){
                    airportService.deleteAirports(id);
                } else {
                    System.out.println("El aeropuerto no ha sido eliminado");
                }
            }
            Optional<Airports> deletedAirport = airportService.getAirportsById(id);
            if (deletedAirport.isEmpty()) { System.out.println("Aeropuerto eliminado exitosamente"); }
            System.out.print("\nDesea eliminar otro aeropuerto? si/ENTER ");
            rta = sc.nextLine();
        }
    }

    
    public void start() {
        String[] menu = {"Registrar Aeropuerto","Actualizar Aeropuerto","Consultar Aeropuerto","Eliminar Aeropuerto","Salir"};
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
            switch (op) {
                case 1:
                    registrarAeropuerto();
                    break;
                case 2:
                    actualizarAeropuerto();
                    break;
                case 3:
                    consultarAeropuerto();
                    break;                   
                case 4:
                    elimninarAeropuerto();
                    break;      
                case 5:    
                    isActive = false;     
                    break; 
                default:
                    System.out.println(errMessage);
                    sc.nextLine();
                    break;
            } 
        } 
    }
}