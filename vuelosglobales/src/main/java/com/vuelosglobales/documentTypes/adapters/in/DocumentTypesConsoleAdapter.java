package com.vuelosglobales.documentTypes.adapters.in;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.documentTypes.application.DocumentTypesService;
import com.vuelosglobales.documentTypes.domain.models.DocumentTypes;

public class DocumentTypesConsoleAdapter {

    private DocumentTypesService documentTypesService;

    public DocumentTypesConsoleAdapter(DocumentTypesService documentTypesService){
        this.documentTypesService = documentTypesService;
    }

    String header = """
            ----------------------
            | TIPOS DE DOCUMENTO |
            ----------------------
            """;
    String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName){
        List<String> valuesList = documentTypesService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public int existsId(String txt, String errMessage2, String tableName){
        List<Integer> IDsLsit = documentTypesService.getIDs(tableName);
        printAllValues(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage, txt);
            if (!IDsLsit.contains(fId)) {
                System.out.println(errMessage2);
            }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarTipoDocumento(){
        while (!rta.isEmpty()) {            
            Main.clearScreen();
            System.out.println(header);
            int id = documentTypesService.getLastId() + 1;
            System.out.print("Ingrese el nombre del tipo de documento: ");
            String name = sc.nextLine();
            DocumentTypes newDocumentType = new DocumentTypes(id, name);
            documentTypesService.createDocumentTypes(newDocumentType);
            Optional<DocumentTypes> createdDocumentType = documentTypesService.getDocumentTypesById(id);
            createdDocumentType.ifPresentOrElse(a -> System.out.println("\nEl tipo de documento: " + a.toString() + " fue registrado correctamente."),
            () -> System.out.println("El tipo de documento no fue registrado correctamente"));
            System.out.println("Desea ingresar otro tipo de documento? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarTipoDocumento(){
        while (!rta.isEmpty()) {            
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tipos de documento:\n");
            int id = existsId("\nIngrese el id del tipo de documento a actualizar: ", "\nTipo de documento no encontrado, Intente de nuevo", "document_types");            
            Optional<DocumentTypes> selectedDocumentType = documentTypesService.getDocumentTypesById(id);
            if(selectedDocumentType.isPresent()){
                System.out.println("\nTipo de documento seleccionado: \n" + selectedDocumentType.get().toString());
                System.out.print("\nIngrese el nuevo nombre del tipo de documento: ");
                String name = sc.nextLine();
                DocumentTypes documentTypeToUpdate = new DocumentTypes(id, name);
                documentTypesService.updateDocumentTypes(documentTypeToUpdate);
            }
            Main.clearScreen();
            System.out.println(header);
            Optional<DocumentTypes> updatedDocumentType = documentTypesService.getDocumentTypesById(id);
            updatedDocumentType.ifPresentOrElse(a -> System.out.println("El tipo de documento: " + a.toString() + " fue actualizado correctamente."), 
            () -> System.out.println("El tipo de documento no fue actualizado correctamente"));
            System.out.println("\nDesea actualizar otro tipo de documento? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarTipoDocumento(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tipos de documento:\n");
            int id = existsId("\nIngrese el id del tipo de documento a consultar: ", "\nTipo de documento no encontrado, Intente de nuevo", "document_types");             
            Optional<DocumentTypes> selectedDocumentType = documentTypesService.getDocumentTypesById(id);
            if (selectedDocumentType.isPresent()) { System.out.println(selectedDocumentType.get().toString()); }
            System.out.println("\nDesea consultar otro aeropuerto? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void elimninarTipoDocumento(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tipos de documento:\n");
            int id = existsId("\nIngrese el id del tipo de documento a eliminar: ", "\nTipo de documento no encontrado, Intente de nuevo", "document_types");
            Optional<DocumentTypes> documentType = documentTypesService.getDocumentTypesById(id);
            if(documentType.isPresent()){
                System.out.println(MessageFormat.format("\nEl tipo de documento {0} será eliminado", documentType.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if(cnf.isEmpty()){
                    documentTypesService.deleteDocumentTypes(id);
                } else {
                    System.out.println("El tipo de documento no ha sido eliminado");
                }
            }
            Optional<DocumentTypes> deletedDocumentType = documentTypesService.getDocumentTypesById(id);
            if (!deletedDocumentType.isPresent()) { System.out.println("Tipo de documento eliminado exitosamente"); }
            System.out.println("Desea eliminar otro tipo de documento? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void start() {
        String[] menu = {"Registrar tipos de documento","Actualizar tipos de documento","Consultar tipos de documento","Eliminar tipos de documento","Salir"};
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
                    registrarTipoDocumento();
                    break;
                case 2:
                    actualizarTipoDocumento();
                    break;
                case 3:
                    consultarTipoDocumento();
                    break;
                case 4:
                    elimninarTipoDocumento();
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