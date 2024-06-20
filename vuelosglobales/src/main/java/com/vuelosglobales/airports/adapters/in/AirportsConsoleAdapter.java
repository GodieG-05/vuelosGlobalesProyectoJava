package com.vuelosglobales.airports.adapters.in;

import java.text.MessageFormat;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.airports.application.AirportsService;
import com.vuelosglobales.airports.domain.models.Airports;
public class AirportsConsoleAdapter {

    private final AirportsService airportService;

    public AirportsConsoleAdapter(AirportsService airportService){
        this.airportService = airportService;
    }
    public void start() {
        
        String header = """
            ---------------
            | AEROPUERTOS |
            ---------------
            """;
        String[] menu = {"Registrar Aeropuerto","Actualizar Aeropuerto","Consultar Aeropuerto","Eliminar Aeropuerto","Salir"};
        
        String errMessage = "Error: El dato ingresado es incorrecto, intentelo de nuevo ";
        
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
                    System.out.print("Ingrese el nombre del aeropuerto: ");
                    String name = sc.nextLine();
                    System.out.print("Ingrese el id de la ciudad: ");
                    String id_city = sc.nextLine();
                    Airports newAirport = new Airports(1, name, id_city);
                    airportService.createAirports(newAirport);
                    break;
                case 2:
                    break;
                case 3:
                    break;                   
                case 4:
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