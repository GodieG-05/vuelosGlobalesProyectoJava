package com.vuelosglobales;

import java.util.Scanner;

import com.vuelosglobales.airports.adapters.in.AirportsConsoleAdapter;
import com.vuelosglobales.airports.adapters.out.AirportsMySQLRepository;
import com.vuelosglobales.airports.application.AirportsService;

// import com.vuelosglobales.airports.adapters.in.AirportsConsoleAdapter;

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
        System.out.print(txt);
        try {
            x = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.print(errMesage);
            sc.nextLine();
            x = -1;
            return x;
        }
        return x;
    }
    public static void main(String[] args) {
        AirportsMySQLRepository airportsMySQLRepository = new AirportsMySQLRepository("jdbc:mysql://localhost:3306/travel_agency", "campus2023", "campus2023");
        AirportsService airportsService = new AirportsService(airportsMySQLRepository);
        AirportsConsoleAdapter airportsConsoleAdapter = new AirportsConsoleAdapter(airportsService);
        airportsConsoleAdapter.start();
    }
}