package com.vuelosglobales;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;

// import com.vuelosglobales.users.Admin;
import com.vuelosglobales.users.Agent;

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

    public static void main(String[] args) {
        Agent agent = new Agent();
        agent.start();
        // Admin admin = new Admin();
        // admin.start();
    }
}