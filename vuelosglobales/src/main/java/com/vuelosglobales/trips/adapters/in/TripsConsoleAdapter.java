package com.vuelosglobales.trips.adapters.in;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.trips.application.TripsService;
import com.vuelosglobales.trips.domain.models.Scales;
import com.vuelosglobales.trips.domain.models.Trips;

public class TripsConsoleAdapter {

    private final TripsService tripsService;

    public TripsConsoleAdapter(TripsService tripsService) {
        this.tripsService = tripsService;
    }

    public void printAllValues(String tableName){
        List<String> valuesList = tripsService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public Object existsId(String txt, String errMessage1, String errMessage2, Scanner sc, Boolean returnInt, String tableName){
        List<Object> IDsLsit = tripsService.getIDs(tableName);
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
    
    public void asignarTripulacion(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("\nTrayectos:");
            Object idTrip = existsId("\nIngrese el id del trayecto: ", errMessage, 
            "Trayecto no encontrada, Intente de nuevo", sc, true,"trips");
            System.out.println("\nEmpleados:");
            Object idEmployee = existsId("\nIngrese el id del empleado a asignar: ", errMessage, "Empleado no encontrado, Intente de nuevo", sc, false, "employees");
            tripsService.assignX(idTrip, idEmployee, "employee");
            Optional<String> assignedEmployee = tripsService.getAssignations(idTrip, idEmployee, "employees");
            assignedEmployee.ifPresentOrElse(e -> System.out.println("\nLa asignacion: " + e.toString() + " fue realizada correctamente."), 
            () -> System.out.println("La asignacion no fue realizada correctamente"));
            System.out.print("\nDesea realizar otra asignacion? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void asignarAvion (String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("\nTrayectos:");
            Object idTrip = existsId("\nIngrese el id del trayecto: ", errMessage, 
            "Trayecto no encontrada, Intente de nuevo", sc, true,"trips");
            System.out.println("\nAviones:");
            Object idPlane = existsId("\nIngrese el id del avion a asignar: ", errMessage, "Avion no encontrado, Intente de nuevo", sc, true,"planes");
            tripsService.assignX(idTrip, idPlane, "planes");
            Optional<String> assignedPlane = tripsService.getAssignations(idTrip, idPlane, "planes");
            assignedPlane.ifPresentOrElse(e -> System.out.println("\nLa asignacion: " + e.toString() + " fue realizada correctamente."), 
            () -> System.out.println("La asignacion no fue realizada correctamente"));
            System.out.print("\nDesea realizar otra asignacion? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarAsignacion (String header, String errMessage, Scanner sc, String rta) {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Trayectos:\n");
            Object id = existsId("\nIngrese el id del trayecto para consultar su tripulacion: ", errMessage, "\nTrayecto no encontrado, Intente de nuevo", sc, true, "trips");
            Optional<ArrayList<String>> tripulation = tripsService.getTripulation((Integer)id);
            if (tripulation.isPresent()) { 
                System.out.println("\nTripulacion del trayecto:");
                for (String empleado : tripulation.get()) {
                    System.out.println(empleado); 
                }
            } else {
                System.out.println("\nTrayecto sin tripulacion asignada");
            }
            System.out.println("\nDesea consultar otra tripulacion? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarEscala (String header, String errMessage, Scanner sc, String rta) {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Trayectos:\n");
            Object id = existsId("\nIngrese el id del trayecto para consultar sus escalas: ", errMessage, "\nTrayecto no encontrado, Intente de nuevo", sc, true, "trips");
            Optional<ArrayList<String>> escalas = tripsService.getScalesFromTrip((Integer)id);
            if (escalas.isPresent()) { 
                System.out.println("\nEscalas del trayecto:");
                for (String escala : escalas.get()) {
                    System.out.println(escala); 
                }
            } else {
                System.out.println("\nTrayecto sin escalas asignadas");
            }
            System.out.println("\nDesea consultar otra escala? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarEscala (String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Escalas:\n");
            Object id = existsId("\nIngrese el id de la escala a actualizar: ", errMessage, "\nEscala no encontrada, Intente de nuevo", sc, true, "flight_connections");
            Optional<ArrayList<String>> selectedScale = tripsService.getScales((Integer)id);
            Optional<Scales> updatedScale = Optional.empty();
            if (selectedScale.isPresent()){
                System.out.println("\nEscala seleccionada: \n" + selectedScale.get().toString());
                System.out.println("\nAerouertos:");
                Object idOrg = existsId("\nIngrese el id del aeropuerto de origen de la escala: ", errMessage, "\nAeropuerto no encontrado, Intente de nuevo", sc, false, "airports");
                Object idDes;
                do {
                    System.out.println("\nAerouertos:");
                    idDes = existsId("\nIngrese el id del aeropuerto de destino de la escala: ", errMessage, "\nAeropuerto no encontrado, Intente de nuevo", sc, false, "airports");
                    if (idOrg.equals(idDes)) { System.out.println("\nEl aeropuerto de destino no puede ser el mismo que el de origen, Intente de nuevo"); }
                } while (idOrg.equals(idDes));
                Scales scale = new Scales((Integer)id, String.valueOf(idOrg), String.valueOf(idDes));
                updatedScale = tripsService.updateScale(scale);
            }
            updatedScale.ifPresentOrElse((s -> System.out.println("\nLa escala: " + s.toString() + " fue actualizada correctamente.")),
            () -> System.out.println("La escala no fue actualizada correctamente"));
            System.out.print("\nDesea actualizar otra escala? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void eliminarEscala (String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Escalas:\n");
            Object id = existsId("\nIngrese el id de la escala a eliminar: ", errMessage, "\nEscala no encontrada, Intente de nuevo", sc, true, "flight_connections");
            Optional<ArrayList<String>> selectedScale = tripsService.getScales((Integer)id);
            Optional<Scales> deletedScale = Optional.empty();
            if (selectedScale.isPresent()) {
                System.out.println(MessageFormat.format("\nLa escala {0} será eliminada", selectedScale.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if (cnf.isEmpty()) {
                    tripsService.deleteScale((Integer)id);
                } else {
                    System.out.println("La escala no ha sido eliminada");
                }
            }
            if (!deletedScale.isPresent()) { System.out.println("Trayecto eliminado exitosamente"); }
            System.out.println("Desea eliminar otro trayecto? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void actualizarTrayecto (String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Trayectos:\n");
            Object id = existsId("\nIngrese el id del trayecto a actualizar: ", errMessage, "\nTrayecto no encontrado, Intente de nuevo", sc, true, "trips");
            Optional<Trips> selectedTrip = tripsService.getTripsById((Integer)id);
            if (selectedTrip.isPresent()){
                System.out.println("\nAvion seleccionado: \n" + selectedTrip.get().toString());
                Date tripDate = Main.validDate(sc, errMessage, "\nIngrese la nueva fecha del trayecto en formato YYYY-MM-DD: ");
                int priceTrip = Main.validInt(sc, errMessage, "Ingrese el nuevo precio del trayecto: ");
                Trips tripToUpdate = new Trips((Integer)id, tripDate, priceTrip);
                tripsService.updateTrips(tripToUpdate);
            }
            Optional<Trips> updatedTrip = tripsService.getTripsById((Integer)id);
            updatedTrip.ifPresentOrElse((t -> System.out.println("\nEl trayecto: " + t.toString() + " fue actualizado correctamente.")), 
            () -> System.out.println("El trayecto no fue actualizado correctamente"));
            System.out.print("\nDesea actualizar otro trayecto? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void consultarTrayecto(String header, String errMessage, Scanner sc, String rta) {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Trayectos:\n");
            Object id = existsId("\nIngrese el id del trayecto a consultar: ", errMessage, "\nTrayecto no encontrado, Intente de nuevo", sc, true, "trips");
            Optional<Trips> selectedTrip = tripsService.getTripsById((Integer)id);
            if (selectedTrip.isPresent()) { System.out.println(selectedTrip.get().toString()); }
            System.out.println("\nDesea consultar otro trayecto? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void eliminarTrayecto(String header, String errMessage, Scanner sc, String rta){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Trayecto:\n");
            Object id = existsId("\nIngrese el id del trayecto a eliminar: ", errMessage,
            "\nTrayecto no encontrado, Intente de nuevo", sc, true, "trips");
            Optional<Trips> selectedTrip = tripsService.getTripsById((Integer)id);
            if (selectedTrip.isPresent()) {
                System.out.println(MessageFormat.format("\nEl trayecto {0} será eliminado", selectedTrip.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if (cnf.isEmpty()) {
                    tripsService.deleteTrips((Integer)id);
                } else {
                    System.out.println("El trayecto no ha sido eliminado");
                }
            }
            Optional<Trips> deletedTrip = tripsService.getTripsById((Integer)id);
            if (!deletedTrip.isPresent()) { System.out.println("Trayecto eliminado exitosamente"); }
            System.out.println("Desea eliminar otro trayecto? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void start() {
        
        String header = """
            -------------
            | TRAYECTOS |
            -------------
            """;
        String[] menu = {"Asignar tripulación a trayecto","Asignar aeronave a trayecto", "Consultar asignación de tripulación", "Consultar escala", "Actualizar escala","Eiminar escala","Consultar trayecto", "Actualizar trayecto" ,"Eliminar trayecto","Salir"};
        
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
            String rta = " ";
            switch (op) {
                case 1:
                    asignarTripulacion(header, errMessage, sc, rta);
                    break;
                case 2:
                    asignarAvion(header, errMessage, sc, rta);
                    break;
                case 3:
                    consultarAsignacion(header, errMessage, sc, rta);
                    break;
                case 4:
                    consultarEscala(header, errMessage, sc, rta);
                    break;
                case 5:
                    actualizarEscala(header, errMessage, sc, rta);
                    break;
                case 6:
                    eliminarEscala(header, errMessage, sc, rta);
                    break;
                case 7:
                    consultarTrayecto(header, errMessage, sc, rta);
                    break;                   
                case 8:
                    actualizarTrayecto(header, errMessage, sc, rta);
                    break;       
                case 9:
                    eliminarTrayecto(header, errMessage, sc, rta);
                    break;       
                case 10:    
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
