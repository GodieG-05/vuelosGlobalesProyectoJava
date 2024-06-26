package com.vuelosglobales.users;

import java.text.MessageFormat;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.airports.adapters.in.AirportsConsoleAdapter;
import com.vuelosglobales.airports.adapters.out.AirportsMySQLRepository;
import com.vuelosglobales.airports.application.AirportsService;
import com.vuelosglobales.documentTypes.adapters.in.DocumentTypesConsoleAdapter;
import com.vuelosglobales.documentTypes.adapters.out.DocumentTypesMySQLRepository;
import com.vuelosglobales.documentTypes.application.DocumentTypesService;
import com.vuelosglobales.fares.adapters.in.FaresConsoleAdapter;
import com.vuelosglobales.fares.adapters.out.FaresMySQLRepository;
import com.vuelosglobales.fares.application.FaresService;
import com.vuelosglobales.planes.adapters.in.PlanesConsoleAdapter;
import com.vuelosglobales.planes.adapters.out.PlaneMySQLRepository;
import com.vuelosglobales.planes.application.PlaneService;
import com.vuelosglobales.trips.adapters.in.TripsConsoleAdapter;
import com.vuelosglobales.trips.adapters.out.TripsMySQLRepository;
import com.vuelosglobales.trips.application.TripsService;

public class Admin {
    public void start(String url, String user, String password) {

        TripsMySQLRepository tripsMySQLRepository = new TripsMySQLRepository(url, user, password);
        TripsService tripsService = new TripsService(tripsMySQLRepository);
        TripsConsoleAdapter tripsConsoleAdapter = new TripsConsoleAdapter(tripsService);

        AirportsMySQLRepository airportsMySQLRepository = new AirportsMySQLRepository(url, user, password);
        AirportsService airportsService = new AirportsService(airportsMySQLRepository);
        AirportsConsoleAdapter airportsConsoleAdapter = new AirportsConsoleAdapter(airportsService);

        PlaneMySQLRepository planeMySQLRepository = new PlaneMySQLRepository(url, user, password);
        PlaneService planeService = new PlaneService(planeMySQLRepository);
        PlanesConsoleAdapter planesConsoleAdapter = new PlanesConsoleAdapter(planeService);

        FaresMySQLRepository faresMySQLRepository = new FaresMySQLRepository(url, user, password);
        FaresService faresService = new FaresService(faresMySQLRepository);
        FaresConsoleAdapter faresConsoleAdapter = new FaresConsoleAdapter(faresService);

        DocumentTypesMySQLRepository documentTypesMySQLRepository = new DocumentTypesMySQLRepository(url, user, password);
        DocumentTypesService documentTypesService = new DocumentTypesService(documentTypesMySQLRepository);
        DocumentTypesConsoleAdapter documentTypesConsoleAdapter = new DocumentTypesConsoleAdapter(documentTypesService);

        String header = """
            ----------------------------
            | ADMINISTRADOR DE SISTEMA |
            ----------------------------
            """;
        String[] menu = {"Trayectos y Escalas","Aeropuertos","Aviones","Tarifas de Vuelo", "Tipos de Documentos","Salir"};
        
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
            switch (op) {
                case 1:
                    tripsConsoleAdapter.start();
                    break;
                case 2:
                    airportsConsoleAdapter.start();
                    break;
                case 3:
                    planesConsoleAdapter.start();
                    break;                   
                case 4:
                    faresConsoleAdapter.start();    
                    break;   
                case 5:
                    documentTypesConsoleAdapter.start();
                    break;    
                case 6:    
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
