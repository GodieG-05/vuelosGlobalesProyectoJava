package com.vuelosglobales.fares.adapters.in;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.fares.application.FaresService;
import com.vuelosglobales.fares.domain.models.Fares;

public class FaresConsoleAdapter {
    private final FaresService flightFareService;

    public FaresConsoleAdapter(FaresService flightFareService) {
        this.flightFareService = flightFareService;
    }

    public int existsId(String txt, String errMessage1, String errMessage2, Scanner sc, String tableName){
        List<Integer> IDsLsit = flightFareService.getIDs(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage1, txt);
            if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarTarifa(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Ingrese la descripción de la tarifa: ");
            String description = sc.nextLine();
            System.out.println("Ingrese el detalle de la tarifa: ");
            String details = sc.nextLine();
            Double value = Main.validDouble(sc, errMessage, "Ingrese el valor de la tarifa");
            int id = 0; // Reevaluar valor del id
            id =+1;
            Fares newFlightFare = new Fares(id, description, details, value);
            flightFareService.createFlightFare(newFlightFare);
            Optional<Fares> createdFlightFare = flightFareService.getFlightFareById(id);
            createdFlightFare.ifPresentOrElse(fF -> System.out.println("\nLa tarifa de vuelo: " + fF.toString() + " fue registrado correctamente.") , () -> System.out.println("[¡] Error al crear la tarifa de vuelo"));
            System.out.println("\n¿Desea ingresar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarTarifa(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tarifas de vuelo:\n");
            int id = existsId("\nIngrese el id de la tarifa de vuelo a actualizar", errMessage, "\nTarifa de vuelo no encontrada, Intente de nuevo", sc, "flight_fares");
            Optional<Fares> selectedFlightFare = flightFareService.getFlightFareById(id);
            if(selectedFlightFare.isPresent()){
                System.out.println("\nTarifa de vuelo seleccionada:\n" + selectedFlightFare.get().toString());
                System.out.println("\nIngrese la nueva descripción de la tarifa");
                String description = sc.nextLine();
                System.out.println("\nIngrese el nuevo detalle de la tarifa");
                String detail = sc.nextLine();
                Double value = Main.validDouble(sc, errMessage, "Ingrese el nuevo valor de la tarifa");
                Fares flightFareToUpdate = new Fares(id, description, detail, value);
                flightFareService.updateFlightFare(flightFareToUpdate);
            }
            Optional<Fares> updatedFlightFare = flightFareService.getFlightFareById(id);
            updatedFlightFare.ifPresentOrElse(fF -> System.out.println("\nLa tarifa: " + fF.toString() + " fue actualizada correctamente."), 
            () -> System.out.println("[¡] Error al actualizar tarifa de vuelo"));
            System.out.println("\n¿Desea actulizar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarTarifa(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tarifas de vuelo:\n");
            int id = existsId("\nIngrese el id de la tarifa de vuelo a consultar: ", errMessage, "\nTarifa de vuelo no encontrada, Intente de nuevo", sc, "flight_fares");
            Optional<Fares> selectedFlightFare = flightFareService.getFlightFareById(id);
            selectedFlightFare.ifPresentOrElse(fF -> System.out.println(selectedFlightFare.get().toString()),
            () -> System.out.println("\nTarifa de vuelo no encontrada"));
            System.out.println("\n¿Desea consultar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void eliminarTarifa(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tarifas de vuelo:\n");
            int id = existsId("\nIngrese la tarifa de vuelo a eliminar", errMessage, "\nTarifa de vuelo no encontrada, Intente de nuevo", sc, "flight_fares");
            Optional<Fares> selectedFlightFare = flightFareService.getFlightFareById(id);
            if (selectedFlightFare.isPresent()){
                System.out.println(MessageFormat.format("\nLa tarifa de vuelo {0} será eliminada", selectedFlightFare.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String choice = sc.nextLine();
                if (choice.isEmpty()) {
                    flightFareService.deleteFlightFare(id);
                } else {
                    System.out.println("La tarifa de vuelo no ha sido eliminada");
                }
            }
            Optional<Fares> deletedFlightFare = flightFareService.getFlightFareById(id);
            if(!deletedFlightFare.isPresent()) {System.out.println("La tarifa de vuelo ha sido eliminada exitosamente");}
            System.out.println("¿Desea eliminar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void start() {
        
        String header = """
            --------------------
            | Tarifas de Vuelo |
            --------------------
            """;
        String[] menu = {"Registrar Tarifa","Actualizar Tarifa","Consultar Tarifa","Eliminar Tarifa","Salir"};
        
        String errMessage = "El dato ingresado es incorrecto, intentelo de nuevo ";
        
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

            switch (op) {
                case 1:
                    registrarTarifa(header, errMessage, sc, errMessage);
                    break;
                case 2:
                    actualizarTarifa(header, errMessage, sc, errMessage);
                    break;      
                case 3:
                    consultarTarifa(header, errMessage, sc, errMessage);
                    break;                   
                case 4:
                    eliminarTarifa(header, errMessage, sc, errMessage);
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
