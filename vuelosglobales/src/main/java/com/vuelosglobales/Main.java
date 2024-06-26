package com.vuelosglobales;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.revisions.adapters.in.RevisionsConsoleAdapter;
import com.vuelosglobales.revisions.adapters.out.RevisionsMySQLRepository;
import com.vuelosglobales.revisions.application.RevisionsService;
import com.vuelosglobales.users.Admin;
import com.vuelosglobales.users.Agent;
// import com.vuelosglobales.users.Customer;

// import com.vuelosglobales.users.Admin;
// import com.vuelosglobales.users.Agent;

public class Main {
    // Codigo para limipiar consola
    public static void clearScreen() {         
        try {             
            if (System.getProperty("os.name").contains("Windows")) {                 
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();             
            } else {                 
                new ProcessBuilder("clear").inheritIO().start().waitFor();             
            }         
        } catch (Exception e) {             
            System.out.println("Error al limpiar la pantalla: " + e.getMessage());         
        }     
    }
    // Validar entradas a menu del usuario
    public static int validInt(Scanner sc, String errMesage, String txt){
        int x;
        do {
            System.out.print(txt);
            try {
                x = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errMesage);
                x = -1;
                if (txt == "-> ") {
                    return x;
                }
            }
        } while (x == -1);
        return x;
    }
    public static Date validDate(Scanner sc, String errMesage, String txt){
        Optional<Date> x;
        do {
            System.out.print(txt);
            try {
                x = Optional.of(Date.valueOf(sc.nextLine()));
            } catch (IllegalArgumentException e) {
                x = Optional.empty();
                System.out.println(errMesage);
            }
        } while (!x.isPresent());
        return x.get();
    }

    public static void validPassword( Scanner sc, String txt,String password){
        String uPassword = "";
        do {
            System.out.print(txt);
            uPassword = sc.nextLine();
            if (!password.equals(uPassword)) {
                System.out.print("\nContraseña incorrecta, Intente de nuevo");
            }
        } while (!password.equals(uPassword));
    }
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/travel_agency";
        String user = "root";
        String password = "12345";
        RevisionsMySQLRepository revisionsMySQLRepository = new RevisionsMySQLRepository(url, user, password);
        RevisionsService revisionsService = new RevisionsService(revisionsMySQLRepository);
        RevisionsConsoleAdapter revisionsConsoleAdapter = new RevisionsConsoleAdapter(revisionsService);
        Admin admin = new Admin();
        Agent agent = new Agent();
        // Customer customer = new Customer();
        String header = """
        -------------------
        | VUELOS GLOBALES |
        -------------------
        """;
        String[] menu = {"Administrador del Sistema","Agente de vuelos","Tecnico","Cliente","Salir"};
        Scanner sc = new Scanner(System.in);
        String errMessage = "\nEl dato ingresado es incorrecto, intentelo de nuevo ";
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
                    validPassword(sc, "\nIngrese la contraseña del administrador: ", "administrador");
                    admin.start(url, user, password);
                    break;
                case 2:
                    validPassword(sc, "\nIngrese la contraseña del agente: ", "agente");
                    agent.start(url, user, password);
                    break;      
                case 3:
                    validPassword(sc, "\nIngrese la contraseña del tecnico: ", "tecnico");
                    revisionsConsoleAdapter.start();
                    break;                   
                case 4:
                    validPassword(sc, "\nIngrese la contraseña del culiente: ", "culiente");
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