package com.vuelosglobales.users;

import java.text.MessageFormat;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.bookings.adapters.in.BookingsConsoleAdapter;
import com.vuelosglobales.bookings.adapters.out.BookingsMySQLRepository;
import com.vuelosglobales.bookings.application.BookingsService;
import com.vuelosglobales.customers.adapters.in.CustomersConsoleAdapter;
import com.vuelosglobales.customers.adapters.out.CustomersMySQLRepository;
import com.vuelosglobales.customers.application.CustomersService;
import com.vuelosglobales.documentTypes.adapters.in.DocumentTypesConsoleAdapter;
import com.vuelosglobales.documentTypes.adapters.out.DocumentTypesMySQLRepository;
import com.vuelosglobales.documentTypes.application.DocumentTypesService;
import com.vuelosglobales.fares.adapters.in.FaresConsoleAdapter;
import com.vuelosglobales.fares.adapters.out.FaresMySQLRepository;
import com.vuelosglobales.fares.application.FaresService;
import com.vuelosglobales.trips.adapters.in.TripsConsoleAdapter;
import com.vuelosglobales.trips.adapters.out.TripsMySQLRepository;
import com.vuelosglobales.trips.application.TripsService;

public class Agent {
    public void start(String url, String user, String password) {

        BookingsMySQLRepository bookingsMySQLRepository = new BookingsMySQLRepository(url, user, password);
        BookingsService bookingsService = new BookingsService(bookingsMySQLRepository);
        BookingsConsoleAdapter bookingsConsoleAdapter = new BookingsConsoleAdapter(bookingsService);
        
        CustomersMySQLRepository customersMySQLRepository = new CustomersMySQLRepository(url, user, password);
        CustomersService customersService = new CustomersService(customersMySQLRepository);
        CustomersConsoleAdapter customersConsoleAdapter = new CustomersConsoleAdapter(customersService);
        
        DocumentTypesMySQLRepository documentTypesMySQLRepository = new DocumentTypesMySQLRepository(url, user, password);
        DocumentTypesService documentTypesService = new DocumentTypesService(documentTypesMySQLRepository);
        DocumentTypesConsoleAdapter documentTypesConsoleAdapter = new DocumentTypesConsoleAdapter(documentTypesService);
        
        TripsMySQLRepository tripsMySQLRepository = new TripsMySQLRepository(url, user, password);
        TripsService tripsService = new TripsService(tripsMySQLRepository);
        TripsConsoleAdapter tripsConsoleAdapter = new TripsConsoleAdapter(tripsService);
        
        FaresMySQLRepository faresMySQLRepository = new FaresMySQLRepository(url, user, password);
        FaresService faresService = new FaresService(faresMySQLRepository);
        FaresConsoleAdapter faresConsoleAdapter = new FaresConsoleAdapter(faresService);

            String header = """
            ----------------------------
            | ADMINISTRADOR DE SISTEMA |
            ----------------------------
            """;
        String[] menu = {"Registrar reservas","Consultar reservas","Eliminar reservas","Consultar informacion de vuelo","Registrar clientes","Actualizar clientes","Consultar clientes","Consultar tipo de documento", "Consultar Tarifa de Vuelo","Consultar Asignación de Tripulación","Consultar escalas de un trayecto","Salir"};
        
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
                    bookingsConsoleAdapter.registrarReserva();
                    break;
                case 2:
                    bookingsConsoleAdapter.consultarReserva();
                    break;
                case 3:
                    bookingsConsoleAdapter.eliminarReserva();
                    break;                   
                case 4:
                    bookingsConsoleAdapter.consultarVuelo();
                    break;   
                case 5:
                    customersConsoleAdapter.registrarCliente();
                    break;    
                case 6:
                    customersConsoleAdapter.actualizarCliente();
                    break; 
                case 7:
                    customersConsoleAdapter.consultarCliente();
                    break; 
                case 8:
                    documentTypesConsoleAdapter.consultarTipoDocumento();
                    break;
                case 9:
                    faresConsoleAdapter.consultarTarifa();
                    break;
                case 10:
                    tripsConsoleAdapter.consultarAsignacion();
                    break;
                case 11:
                    tripsConsoleAdapter.consultarEscala();
                    break;
                case 12:
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