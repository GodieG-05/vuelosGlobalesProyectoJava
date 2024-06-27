package com.vuelosglobales.users;

import java.sql.Date;
import java.time.LocalDate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.bookings.adapters.in.BookingsConsoleAdapter;
import com.vuelosglobales.bookings.adapters.out.BookingsMySQLRepository;
import com.vuelosglobales.bookings.application.BookingsService;
import com.vuelosglobales.trips.adapters.out.TripsMySQLRepository;


public class Customer {

    String header = """
        ----------------------------
        | ADMINISTRADOR DE SISTEMA |
        ----------------------------
        """;
    String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";
    TripsMySQLRepository tripsMySQLRepository;
    BookingsMySQLRepository bookingsMySQLRepository;
    BookingsService bookingsService;
    BookingsConsoleAdapter bookingsConsoleAdapter;

    public void printAllValues(String tableName){
        List<String> valuesList = tripsMySQLRepository.getTableValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public Object existsId(String txt, String errMessage2, Boolean returnInt, String tableName){
        List<Object> IDsLsit = tripsMySQLRepository.getIDs(tableName);
        printAllValues(tableName);
        if (returnInt) {
            int fId;
            do {
                fId = Main.validInt(sc, errMessage, txt);
                if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
            } while (!IDsLsit.contains(fId));
            return fId;
        } else {
            String fId;
            do {
                System.out.print(txt);
                fId = sc.nextLine();
                if (!IDsLsit.contains(fId)) { System.out.println(errMessage2); }
            } while (!IDsLsit.contains(fId));
            return fId;
        }
    }

    public boolean buscarVuelos() {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("\nAeropuertos:\n");
            Object idOrg = existsId("\nIngrese el id del aeropuerto de origen: ", "Aeropuerto no encontrado, Intente de nuevo", false, "airports");
            System.out.println("\nAeropuertos:\n");
            Object idDes = existsId("\nIngrese el id del aeropuerto de destino: ", "Aeropuerto no encontrado, Intente de nuevo", false, "airports");
            Optional<ArrayList<String>> flightsList = tripsMySQLRepository.findByIdFromPlace(String.valueOf(idOrg), String.valueOf(idDes));
            if (flightsList.isPresent()) {
                System.out.println("\nVuelo encontrado:");
                for (String flight : flightsList.get()) { System.out.println(flight); }
            } else { System.out.println("Este vuelo no se encuentra disponible actualmente, Intente de nuevo"); }
            System.out.println("\nDesea buscar otro vuelo?  si/ENTER ");
            rta = sc.nextLine();
        }
        return true;
    }

    public void seleccionarVuelos() {
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            LocalDate curDate = LocalDate.now();
            Date date = Date.valueOf(curDate);
            System.out.println("\nClientes:\n");
            Object idCus = existsId("\nIngrese su id como cliente: ", "Cliente no encontrado, Intente de nuevo", false, "customers");
            System.out.println("\nVuelos:\n");
            Object idFli = existsId("\nIngrese el id del vuelo a seleccionar: ", "Vuelo no encontrado, Intente de nuevo", true, "flight_connections");
            System.out.println("\nTarifas:\n");
            Object idFar = existsId("\nIngrese el id de la tarifa deseada: ", "Tarifa no encontrado, Intente de nuevo", true, "flight_fares");
            tripsMySQLRepository.selectFlight(String.valueOf(idCus), (Integer)idFli, (Integer)idFar, date);
            System.out.println("\nDesea buscar otro vuelo?  si/ENTER ");
            rta = sc.nextLine();
        }
    }

    public void start(String url, String user, String password){
        tripsMySQLRepository = new TripsMySQLRepository(url, user, password);
        bookingsMySQLRepository = new BookingsMySQLRepository(url, user, password);
        bookingsService = new BookingsService(bookingsMySQLRepository);
        bookingsConsoleAdapter = new BookingsConsoleAdapter(bookingsService);
        String[] menu = {"Buscar vuelos","Seleccionar vuelo","Consultar reserva de vuelo","Cancelar reserva de vuelo", "Modificar reservas de vuelo", "Añadir pasajeros",  "Seleccionar asientos", "Realizar pago","Salir"};
        boolean buscado = false;
        boolean isActive = true;
        mainLoop:
        while (isActive) {
            rta = " ";
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
                    buscado = buscarVuelos();
                    break;
                case 2: 
                    if (buscado) {
                        seleccionarVuelos();
                    } else {
                        System.out.print("Para seleccionar un vuelo primero debes buscar uno, Intentalo de nuevo ");
                        sc.nextLine();
                    }
                    break;
                case 3:
                    bookingsConsoleAdapter.consultarReserva();
                    break;                   
                case 4:
                    bookingsConsoleAdapter.eliminarReserva();
                    break;   
                case 5:
                    bookingsConsoleAdapter.actualizarReserva();
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