package com.vuelosglobales.planes.adapters.in;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.planes.application.PlaneService;
import com.vuelosglobales.planes.domain.models.Plane;

public class PlanesConsoleAdapter {
    
    private final PlaneService planeService;

    public PlanesConsoleAdapter(PlaneService planeService){
        this.planeService = planeService;
    }

    String header = """
        -----------
        | AVIONES |
        -----------
        """;
    String errMessage = "El dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName){
        List<String> valuesList = planeService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public int existsId(String txt, String errMessage2, String tableName){
        List<Integer> IDsLsit = planeService.getIDs(tableName);
        printAllValues(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage, txt);
            if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarAvion(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            int id = planeService.getLastId() + 1;
            System.out.print("Ingrese la placa del avion: ");
            String plates = sc.nextLine();
            int capacity = Main.validInt(sc, errMessage, "Ingrese la capacidad del avion: ");
            Date date = Main.validDate(sc, errMessage, "Ingrese la fecha de fabricacion del avion en formato YYYY-MM-DD: ");
            System.out.println("\nEstados:\n");
            int idStatus = existsId("\nIngrese el ID del estado del avion: ", "Estado no encontrado, Intente de nuevo", "statuses");
            System.out.println("\nModelos:\n");
            int idModel = existsId("\nIngrese el ID del modelo del avion: ", "Modelo no encontrado, Intente de nuevo", "models");
            Plane newPlane = new Plane(id, plates, capacity, date, idStatus, idModel);
            planeService.createPlane(newPlane);
            Optional<Plane> createdPlane = planeService.getPlaneById(id);
            createdPlane.ifPresentOrElse(p -> System.out.println("\nEl avion: " + p.toString() + " fue registrado correctamente."), 
            () -> System.out.println("El avion no fue registrado correctamente"));
            System.out.print("\nDesea ingresar otro avion? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarAvion(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Aviones:\n");
            int id = existsId("\nIngrese el id del avion a actualizar: ", "\nAvion no encontrado, Intente de nuevo", "planes");
            Optional<Plane> slectedPlane = planeService.getPlaneById(id);
            if(slectedPlane.isPresent()){
                System.out.println("\nAvion seleccionado: \n" + slectedPlane.get().toString());
                System.out.print("\nIngrese la nueva placa del avion: ");
                String plates = sc.nextLine();
                int capacity = Main.validInt(sc, errMessage, "Ingrese la nueva capacidad del avion: ");
                Date date = Main.validDate(sc, errMessage, "Ingrese la nueva fecha de fabricacion del avion en formato YYYY-MM-DD: ");
                System.out.println("\nEstados:\n");
                int idStatus = existsId("\nIngrese el nuevo ID del estado del avion: ", "Estado no encontrado, Intente de nuevo",  "statuses");
                System.out.println("\nModelos:\n");
                int idModel = existsId("\nIngrese el nuevo ID del modelo del avion: ", "Modelo no encontrado, Intente de nuevo", "models");
                Plane planeToUpdate = new Plane(id, plates, capacity, date, idStatus, idModel);
                planeService.updatePlane(planeToUpdate);
            }
            Optional<Plane> updatedPlane = planeService.getPlaneById(id);
            updatedPlane.ifPresentOrElse(p -> System.out.println("\nEl avion: " + p.toString() + " fue actualizado correctamente."), 
            () -> System.out.println("El avion no fue actualizado correctamente"));
            System.out.print("\nDesea actualizar otro avion? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarAvion(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Aviones:\n");
            int id = existsId("\nIngrese el id del avion a consultar: ", "\nAvion no encontrado, Intente de nuevo", "planes");
            Optional<Plane> selectedPlane = planeService.getPlaneById(id);
            if (selectedPlane.isPresent()) { System.out.println(selectedPlane.get().toString()); }
            System.out.println("\nDesea consultar otro avion? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void eliminarAvion(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Aviones:\n");
            int id = existsId("\nIngrese el id del avion a eliminar: ", "\nAvion no encontrado, Intente de nuevo","planes");
            Optional<Plane> slectedPlane = planeService.getPlaneById(id);
            if (slectedPlane.isPresent()) {
                System.out.println(MessageFormat.format("\nEl avion {0} será eliminado", slectedPlane.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if (cnf.isEmpty()) {
                    planeService.deletePlane(id);
                } else {
                    System.out.println("El avion no ha sido eliminado");
                }
            }
            Optional<Plane> deletedPlane = planeService.getPlaneById(id);
            if (!deletedPlane.isPresent()) { System.out.println("Avion eliminado exitosamente"); }
            System.out.println("Desea eliminar otro avion? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void start() {

        String[] menu = {"Registrar Avion","Actualizar Avion","Consultar Avion","Eliminar Avion","Salir"};
        
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
                    registrarAvion();
                    break;
                case 2:
                    actualizarAvion();
                    break;      
                case 3:
                    consultarAvion();
                    break;                   
                case 4:
                    eliminarAvion();
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