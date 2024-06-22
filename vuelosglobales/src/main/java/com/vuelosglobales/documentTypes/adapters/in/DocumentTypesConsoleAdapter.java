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

    public void printAllDocumentTypes() {
        List<DocumentTypes> documentTypesList = documentTypesService.getAllDocumentTypes();
        for (DocumentTypes documentType : documentTypesList) {
            System.out.println(documentType.toString());
        }
        System.out.println("\n");
    }

    public void registrarTipoDocumento(String header, String errMessage ,Scanner sc, String rta){
        while (!rta.isEmpty()) {            
            Main.clearScreen();
            System.out.println(header);
            int id = documentTypesService.getLastId();
            System.out.print("Ingrese el nombre del tipo de documento: ");
            String name = sc.nextLine();
            DocumentTypes newDocumentType = new DocumentTypes(id, name);
            documentTypesService.createDocumentTypes(newDocumentType);
            System.out.println("Desea ingresar otro tipo de documento? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarTipoDocumento(String header, String errMessage, Scanner sc, String rta){
        op2Loop:
        while (!rta.isEmpty()) {            
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tipos de documento:\n");
            printAllDocumentTypes();
            int id = Main.validInt(sc, errMessage, "Ingrese el id del tipo de documento a actualizar: ");
            if(id == -1){
                continue op2Loop;
            }
            Optional<DocumentTypes> selectedDocumentType = documentTypesService.getDocumentTypesById(id);
            if(selectedDocumentType.isPresent()){
                System.out.println(selectedDocumentType.get().toString());
                System.out.print("\nIngrese el nuevo nombre del tipo de documento: ");
                String name = sc.nextLine();
                DocumentTypes documentTypeToUpdate = new DocumentTypes(id, name);
                documentTypesService.updateDocumentTypes(documentTypeToUpdate);
            } else {
                System.out.println("Tipo de documento no encontrado");
            }
            Main.clearScreen();
            System.out.println(header);
            System.out.println("El tipo de documento fue actualizado con exito: ");
            Optional<DocumentTypes> updatedAirport = documentTypesService.getDocumentTypesById(id);
            updatedAirport.ifPresentOrElse(a -> System.out.println(a.toString()),
            () -> System.out.println("Tipo de documento no encontrado"));
            System.out.println("\nDesea ingresar otro tipo de documento? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarTipoDocumento(String header, String errMessage, Scanner sc, String rta){
        op3Loop:
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tipos de documento:\n");
            printAllDocumentTypes();
            int id = Main.validInt(sc, errMessage, "Ingrese el ID del tipo de documento a consultar: "); 
            if (id == -1){
                continue op3Loop;
            } 
            Optional<DocumentTypes> updatedDocumentType = documentTypesService.getDocumentTypesById(id);
            updatedDocumentType.ifPresentOrElse(a -> System.out.println(a.toString()),
            () -> System.out.println("Aeropuerto no encontrado"));
            System.out.println("Desea eliminar otro aeropuerto? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void elimninarTipoDocumento(String header, String errMessage, Scanner sc, String rta){
        op3Loop:
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Tipos de documento:\n");
            printAllDocumentTypes();
            int id = Main.validInt(sc, errMessage, "Ingrese el ID del tipo de documento a eliminar: "); 
            if (id == -1){
                continue op3Loop;
            }
            Optional<DocumentTypes> documentType = documentTypesService.getDocumentTypesById(id);
            if(documentType.isPresent()){
                System.out.println(MessageFormat.format("\nEl tipo de documento {0} será eliminado", documentType.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if(cnf.isEmpty()){
                    documentTypesService.deleteDocumentTypes(id);
                    Main.clearScreen();
                    System.out.println(header);
                    System.out.println("Tipo de documento eliminado exitosamente");
                } else {
                    System.out.println("El tipo de documento no ha sido eliminado");
                }
            } else {
                System.out.println("[¡]ERROR: tipo de documento no registrado ");
            }
            System.out.println("Desea eliminar otro aeropuerto? si/ENTER");
            rta = sc.nextLine();
        }
    }
    public void start() {
        
        String header = """
            ----------------------
            | TIPOS DE DOCUMENTO |
            ----------------------
            """;
        String[] menu = {"Registrar tipos de documento","Actualizar tipos de documento","Consultar tipos de documento","Eliminar tipos de documento","Salir"};
        
        String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
        
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
                    registrarTipoDocumento(header, errMessage, sc, rta);
                    break;
                case 2:
                    actualizarTipoDocumento(header, errMessage, sc, rta);
                    break;
                case 3:
                    consultarTipoDocumento(header, errMessage, sc, rta);
                    break;                   
                case 4:
                    elimninarTipoDocumento(header, errMessage, sc, rta);
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