package com.vuelosglobales;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.fares.adapters.in.FaresConsoleAdapter;
import com.vuelosglobales.fares.adapters.out.FaresMySQLRepository;
import com.vuelosglobales.fares.application.FaresService;
// import com.vuelosglobales.airports.adapters.in.AirportsConsoleAdapter;
// import com.vuelosglobales.airports.adapters.out.AirportsMySQLRepository;
// import com.vuelosglobales.airports.application.AirportsService;
// import com.vuelosglobales.documentTypes.adapters.in.DocumentTypesConsoleAdapter;
// import com.vuelosglobales.documentTypes.adapters.out.DocumentTypesMySQLRepository;
// import com.vuelosglobales.documentTypes.application.DocumentTypesService;
// import com.vuelosglobales.planes.adapters.in.PlanesConsoleAdapter;
// import com.vuelosglobales.planes.adapters.out.PlaneMySQLRepository;
// import com.vuelosglobales.planes.application.PlaneService;
// import com.vuelosglobales.trips.adapters.in.TripsConsoleAdapter;
// import com.vuelosglobales.trips.adapters.out.TripsMySQLRepository;
// import com.vuelosglobales.trips.application.TripsService;


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
            System.out.println(txt);
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
        // AirportsMySQLRepository airportsMySQLRepository = new AirportsMySQLRepository("jdbc:mysql://localhost:3306/travel_agency", "root", "12345");
        // AirportsService airportsService = new AirportsService(airportsMySQLRepository);
        // AirportsConsoleAdapter airportsConsoleAdapter = new AirportsConsoleAdapter(airportsService);
        // airportsConsoleAdapter.start();
        // DocumentTypesMySQLRepository documentTypesMySQLRepository = new DocumentTypesMySQLRepository("jdbc:mysql://localhost:3306/travel_agency", "root", "12345");
        // DocumentTypesService documentTypesService = new DocumentTypesService(documentTypesMySQLRepository);
        // DocumentTypesConsoleAdapter documentTypesConsoleAdapter = new DocumentTypesConsoleAdapter(documentTypesService);
        // documentTypesConsoleAdapter.start();
        // PlaneMySQLRepository planeMySQLRepository = new PlaneMySQLRepository("jdbc:mysql://localhost:3306/travel_agency", "root", "12345");
        // PlaneService planeService = new PlaneService(planeMySQLRepository);
        // PlanesConsoleAdapter planesConsoleAdapter = new PlanesConsoleAdapter(planeService);
        // planesConsoleAdapter.start();
        // TripsMySQLRepository tripsMySQLRepository = new TripsMySQLRepository("jdbc:mysql://localhost:3306/travel_agency", "root", "12345");
        // TripsService tripsService = new TripsService(tripsMySQLRepository);
        // TripsConsoleAdapter tripsConsoleAdapter = new TripsConsoleAdapter(tripsService);
        // tripsConsoleAdapter.start();
        FaresMySQLRepository faresMySQLRepository = new FaresMySQLRepository("jdbc:mysql://localhost:3306/travel_agency", "campus2023", "campus2023");
        FaresService faresService = new FaresService(faresMySQLRepository);
        FaresConsoleAdapter faresConsoleAdapter = new FaresConsoleAdapter(faresService);
        faresConsoleAdapter.start();
    }
}