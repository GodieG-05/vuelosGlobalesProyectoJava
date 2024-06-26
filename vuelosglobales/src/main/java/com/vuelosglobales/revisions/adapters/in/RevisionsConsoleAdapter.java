package com.vuelosglobales.revisions.adapters.in;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.revisions.application.RevisionsService;
import com.vuelosglobales.revisions.domain.models.Revisions;

public class RevisionsConsoleAdapter {

    private final RevisionsService revisionService;

    public RevisionsConsoleAdapter(RevisionsService revisionService) {
        this.revisionService = revisionService;
    }

    String header = """
        --------------
        | REVISIONES |
        --------------
        """;
    String errMessage = "\nEl dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName){
        List<String> valuesList = revisionService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public int existsId(String txt, String errMessage2, String tableName){
        List<Integer> IDsLsit = revisionService.getIDs(tableName);
        printAllValues(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage, txt);
            if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarRevision() {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            int id = revisionService.getLastId() + 1;
            Date revisionDate = Main.validDate(sc, errMessage, "Ingrese la fecha de revisión del avión en formato YYYY-MM-DD: ");
            System.out.println("\nAviones:\n");
            int idPlane = existsId("\nIngrese el id del avión a revisar: ", "\nAvion no encontrado, Intente de nuevo", "planes");
            Revisions newRevision = new Revisions(id, revisionDate, idPlane);
            revisionService.createRevision(newRevision);
            Optional<Revisions> createdRevision = revisionService.getRevisionById(id);
            createdRevision.ifPresentOrElse(r -> System.out.println("\nLa revisión: " + r.toString() + " fue registrada correctamente."),
            () -> System.out.println("[¡] Error al registrar la revisión"));
            System.out.println("\n¿Desea ingresar otra revisión? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarRevision() {
        while (!rta.isEmpty()){
            Main.clearScreen();
            System.out.println(header);
            System.out.println("\nRevisiones:\n");
            int id = existsId("\nIngrese el id de la revisión a actualizar: ", "\nRevisión no encontrada, Intente de nuevo", "revisions");
            Optional<Revisions> selectedRevision = revisionService.getRevisionById(id);
            if(selectedRevision.isPresent()){
                System.out.println("\nRevisión seleccionada: \n" + selectedRevision.get().toString());
                Date revisionDate = Main.validDate(sc, errMessage, "\nIngrese la nueva fecha de revision del avión en formato YYYY-MM-DD: ");
                System.out.println("\nAviones:\n");
                int idPlane = existsId("\nIngrese el nuevo id del avión a revisar: ", "\nAvion no encontrado, Intente de nuevo", "planes");
                Revisions revisionToUpdate = new Revisions(id, revisionDate, idPlane);
                revisionService.updateRevision(revisionToUpdate);
            }
            Optional<Revisions> updatedRevision = revisionService.getRevisionById(id);
            updatedRevision.ifPresentOrElse(r -> System.out.println("\nLa revisión: " + r.toString() + " fue actualizada correctamente."),
            () -> System.out.println("[¡] Error al actualizar la revisión"));
            System.out.println("\n¿Desea actualizar otra revisión? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarRevision(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            int id = existsId("\nIngrese el ID de la revisión a consultar: ", "\nRevisión no encontrada, Intente de nuevo", "revisions");
            Optional<Revisions> selectedRevision = revisionService.getRevisionById(id);
            if (selectedRevision.isPresent()) {System.out.println(selectedRevision.get().toString());}
            System.out.println("\n¿Desea consultar otra revisión? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void eliminarRevision(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("\nRevisiones:\n");
            int id = existsId("\nIngrese el ID de la revisión a eliminar: ",
            "\nRevision no encontrado, Intente de nuevo", "revisions");
            Optional<Revisions> selectedRevision = revisionService.getRevisionById(id);
            if (selectedRevision.isPresent()) {
                System.out.println(MessageFormat.format("\nLa revisión {0} será eliminada", selectedRevision.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if (cnf.isEmpty()) {
                    revisionService.deleteRevision(id);
                } else {
                    System.out.println("La revisión no ha sido eliminada");
                }
            }
            Optional<Revisions> deletedRevision = revisionService.getRevisionById(id);
            if (!deletedRevision.isPresent()) { System.out.println("Revisión eliminada exitosamente"); }
            System.out.println("Desea eliminar otro revisión? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void start() {
        String[] menu = {"Registrar revisión","Actualizar revisión","Consultar revisión","Eliminar revisión","Salir"};
        
        
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
                    registrarRevision();
                    break;
                case 2:
                    actualizarRevision();
                    break;      
                case 3:
                    consultarRevision();
                    break;                   
                case 4:
                    eliminarRevision();
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
