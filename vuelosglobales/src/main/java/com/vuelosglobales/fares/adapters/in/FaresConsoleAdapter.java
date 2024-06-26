package com.vuelosglobales.fares.adapters.in;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.fares.application.FaresService;
import com.vuelosglobales.fares.domain.models.Fares;

public class FaresConsoleAdapter {
    private final FaresService faresService;

    public FaresConsoleAdapter(FaresService flightFareService) {
        this.faresService = flightFareService;
    }

    String header = """
        --------------------
        | Tarifas de Vuelo |
        --------------------
        """;
    String errMessage = "El dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName){
        List<String> valuesList = faresService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public int existsId(String txt, String errMessage2, String tableName){
        List<Integer> IDsLsit = faresService.getIDs(tableName);
        printAllValues(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage, txt);
            if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarTarifa(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            int id = faresService.getLastId() + 1;
            System.out.print("\nIngrese la descripción de la tarifa: ");
            String description = sc.nextLine();
            System.out.println("\nIngrese los detalles de la tarifa: \n");
            String details = sc.nextLine();
            int price = Main.validInt(sc, errMessage, "\nIngrese el valor de la tarifa: ");
            Fares newFlightFare = new Fares(id, description, details, price);
            faresService.createFare(newFlightFare);
            Optional<Fares> createdFlightFare = faresService.getFareById(id);
            createdFlightFare.ifPresentOrElse(f -> System.out.println("\nLa tarifa de vuelo: " + f.toString() + " fue registrada correctamente.") , () -> System.out.println("La tarifa no fue registrada correctamente"));
            System.out.println("\n¿Desea ingresar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarTarifa(){
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tarifas de vuelo:\n");
            int id = existsId("\nIngrese el id de la tarifa de vuelo a actualizar: ", "\nTarifa de vuelo no encontrada, Intente de nuevo", "flight_fares");
            Optional<Fares> selectedFlightFare = faresService.getFareById(id);
            if(selectedFlightFare.isPresent()){
                System.out.println("\nTarifa de vuelo seleccionada:\n" + selectedFlightFare.get().toString());
                System.out.print("\nIngrese la nueva descripción de la tarifa: ");
                String description = sc.nextLine();
                System.out.println("\nIngrese los nuevos detalles de la tarifa: \n");
                String details = sc.nextLine();
                int price = Main.validInt(sc, errMessage, "\nIngrese nuevo el valor de la tarifa: ");
                Fares flightFareToUpdate = new Fares(id, description, details, price);
                faresService.updateFare(flightFareToUpdate);
            }
            Optional<Fares> updatedFare = faresService.getFareById(id);
            updatedFare.ifPresentOrElse(f -> System.out.println("\nLa tarifa: " + f.toString() + " fue actualizada correctamente."), 
            () -> System.out.println("La tarifa de vuelo no fue actualizada correctamente"));
            System.out.println("\n¿Desea actulizar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarTarifa(){
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tarifas de vuelo:\n");
            int id = existsId("\nIngrese el id de la tarifa de vuelo a consultar: ", "\nTarifa de vuelo no encontrada, Intente de nuevo", "flight_fares");
            Optional<Fares> selectedFare = faresService.getFareById(id);
            selectedFare.ifPresentOrElse(f -> System.out.println(selectedFare.get().toString()),
            () -> System.out.println("\nTarifa de vuelo no encontrada"));
            System.out.println("\n¿Desea consultar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void eliminarTarifa(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tarifas de vuelo:\n");
            int id = existsId("\nIngrese el id de la tarifa de vuelo a eliminar: ", "\nTarifa de vuelo no encontrada, Intente de nuevo", "flight_fares");
            Optional<Fares> selectedFlightFare = faresService.getFareById(id);
            if (selectedFlightFare.isPresent()){
                System.out.println(MessageFormat.format("\nLa tarifa de vuelo {0} será eliminada", selectedFlightFare.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String choice = sc.nextLine();
                if (choice.isEmpty()) {
                    faresService.deleteFare(id);
                } else {
                    System.out.println("La tarifa de vuelo no ha sido eliminada");
                }
            }
            Optional<Fares> deletedFlightFare = faresService.getFareById(id);
            if(!deletedFlightFare.isPresent()) {System.out.println("La tarifa de vuelo ha sido eliminada exitosamente");}
            System.out.println("¿Desea eliminar otra tarifa de vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void start() {
        

        String[] menu = {"Registrar Tarifa","Actualizar Tarifa","Consultar Tarifa","Eliminar Tarifa","Salir"};
        
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
                    registrarTarifa();
                    break;
                case 2:
                    actualizarTarifa();
                    break;      
                case 3:
                    consultarTarifa();
                    break;                   
                case 4:
                    eliminarTarifa();
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
