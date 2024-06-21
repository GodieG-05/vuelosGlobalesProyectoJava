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

    public void printAllAirports() {
        List<Airports> airportsList = airportService.getAllAirports();
        for (Airports airport : airportsList) {
            System.out.println("[ID: " + airport.getId() + ", Nombre: " + airport.getName() + "]");
        }
        System.out.println("\n");
    }
    public void registrarAeropuerto(String header, Scanner sc){
        Main.clearScreen();
        System.out.println(header);
        System.out.print("Ingrese el id del aeropuerto: ");
        String id = sc.nextLine();
        System.out.print("Ingrese el nombre del aeropuerto: ");
        String name = sc.nextLine();
        System.out.print("Ingrese el id de la ciudad: "); 
        String id_city = sc.nextLine();
        Airports newAirport = new Airports(id, name, id_city);
        airportService.createAirports(newAirport);
    }
    public void actualizarAeropuerto(String header, Scanner sc){
        Main.clearScreen();
        System.out.println(header);
        System.out.println("Aeropuertos:\n");
        printAllAirports();
        System.out.print("Ingrese el id del aeropuerto a actualizar: ");
        String id = sc.nextLine();
        Optional<Airports> selectedAirport = airportService.getAirportsById(id);
        if(selectedAirport.isPresent()){
            System.out.println("[ID: " + selectedAirport.get().getId() + ", Nombre: " + selectedAirport.get().getName() + ", ID ciudad: " + selectedAirport.get().getId_city() + "]");
            System.out.print("\nIngrese el nuevo nombre del aeropuerto: ");
            String name = sc.nextLine();
            System.out.print("Ingrese el nuevo id de la ciudad: "); 
            String id_city = sc.nextLine();
            Airports airportToUpdate = new Airports(id, name, id_city);
            airportService.updateAirports(airportToUpdate);
        } else {
            System.out.println("Aeropuerto no encontrado");
        }
        Main.clearScreen();
        System.out.println(header);
        System.out.println("El aeropuerto fue actualizado con exito: ");
        Optional<Airports> updatedAirport = airportService.getAirportsById(id);
        updatedAirport.ifPresentOrElse(a -> System.out.println("[ID: " + a.getId() + ", Nombre: " + a.getName() + ", ID ciudad: " + a.getId_city() + "]"),
        () -> System.out.println("Aeropuerto no encontrado"));
    }

    public void elimninarAeropuerto(String header, Scanner sc){
        Main.clearScreen();
        System.out.println(header);
        System.out.println("Aeropuertos:\n");
        printAllAirports();
        System.out.println("Ingrese el ID del aeropuerto a eliminar: ");
        String id = sc.nextLine();
        Optional<Airports> airport = airportService.getAirportsById(id);
        if(airport.isPresent()){
            System.out.println("\nEl aerpuerto con [ID: " + airport.get().getId() + ", Nombre: " + airport.get().getName() + ", ID ciudad: " + airport.get().getId_city() + "] será eliminado");
            System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
            String rta = sc.nextLine();
            if(rta.isEmpty()){
                airportService.deleteAirports(id);
                System.out.println("Aeropuerto eliminado exitosamente");
            } else {
                System.out.println("El aeropuerto no ha sido eliminado");
            }
        } else {
            System.out.println("[¡]ERROR. Aeropuerto no registrado ");
        }
    }
    public void start() {
        
        String header = """
            ---------------
            | AEROPUERTOS |
            ---------------
            """;
        String[] menu = {"Registrar Aeropuerto","Actualizar Aeropuerto","Consultar Aeropuerto","Eliminar Aeropuerto","Salir"};
        
        String errMessage = "Error: El dato ingresado es incorrecto, intentelo de nuevo ";
        
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
            String respuesta;
            switch (op) {
                case 1:
                    do {
                        registrarAeropuerto(header, sc);
                        System.out.println("Desea ingresar otro aeropuerto? si/ENTER");
                        respuesta = sc.nextLine();
                    } while (!respuesta.isEmpty());
                    break;
                case 2:
                    do {
                        actualizarAeropuerto(header, sc);
                        System.out.println("Desea actualizar otro aeropuerto? si/ENTER");
                        respuesta = sc.nextLine();
                    } while (!respuesta.isEmpty());
                    break;
                case 3:
                    break;                   
                case 4:
                    do {
                        elimninarAeropuerto(header, sc);
                        System.out.println("Desea eliminar otro aeropuerto? si/ENTER");
                        respuesta = sc.nextLine();
                    } while (!respuesta.isEmpty());
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
        sc.close();
    }
}