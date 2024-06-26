package com.vuelosglobales.bookings.adapters.in;

import java.sql.Date;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.vuelosglobales.Main;
import com.vuelosglobales.bookings.application.BookingsService;
import com.vuelosglobales.bookings.domain.models.Bookings;

public class BookingsConsoleAdapter {
    private BookingsService bookingsService;

    public BookingsConsoleAdapter(BookingsService bookingsService){
        this.bookingsService = bookingsService;
    }

    String header = """
            ------------
            | RESERVAS |
            ------------
            """;
    Scanner sc = new Scanner(System.in);
    String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
    String rta = " ";

    public void printAllValues(String tableName){
        List<String> valuesList = bookingsService.getAllValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public int existsId(String txt, String errMessage1, String errMessage2, Scanner sc, String tableName){
        List<Integer> IDsLsit = bookingsService.getIDs(tableName);
        printAllValues(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage1, txt);
            if (!IDsLsit.contains(fId)) {
                System.out.println(errMessage2);
            }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarReserva(){
        while (!rta.isEmpty()) {            
            Main.clearScreen();
            System.out.println(header);
            int id = bookingsService.getLastId() + 1;
            Date date = Main.validDate(sc, errMessage, "Ingrese la fecha de la reserva en formato YYYY-MM-DD: ");
            System.out.println("\nTrayectos:\n");
            int id_trip = existsId("\nIngrese el id del trayecto de su reserva: ", errMessage, "\nTrayecto no encontrado, Intente de nuevo", sc, "trips");
            Bookings booking = new Bookings(id, date, id_trip);
            bookingsService.createBookings(booking);
            Optional<Bookings> createdBooking = bookingsService.getBookingById(id);
            createdBooking.ifPresentOrElse(b -> System.out.println("\nEl avion: " + b.toString() + " fue registrado correctamente."), 
            () -> System.out.println("El avion no fue registrado correctamente"));
            System.out.println("\nDesea ingresar otra reserva? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarReserva(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Reservas:\n");
            int id = existsId("\nIngrese el id de la reserva a consultar: ", errMessage, "\nReserva no encontrada, Intente de nuevo", sc, "trip_booking");             
            Optional<Bookings> selectedBooking = bookingsService.getBookingById(id);
            if (selectedBooking.isPresent()) { System.out.println(selectedBooking.get().toString()); }
            System.out.println("\nDesea consultar otra reserva? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void eliminarReserva(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Reservas:\n");
            int id = existsId("\nIngrese el id de la reserva a eliminar: ", errMessage, "\nReserva no encontrada, Intente de nuevo", sc, "trip_booking");
            Optional<Bookings> booking = bookingsService.getBookingById(id);
            if(booking.isPresent()){
                System.out.println(MessageFormat.format("\nLa reserva {0} será eliminada", booking.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if(cnf.isEmpty()){
                    bookingsService.deleteBookings(id);
                } else {
                    System.out.println("La reserva no ha sido eliminada");
                }
            }
            Optional<Bookings> deletedBooking = bookingsService.getBookingById(id);
            if (deletedBooking.isEmpty()) { System.out.println("Reserva eliminada exitosamente"); }
            System.out.println("Desea eliminar otra reserva? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarVuelo(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Vuelos:\n");
            int id = existsId("\nIngrese el id del vuelo a consultar: ", errMessage, "\nVuelo no encontrado, Intente de nuevo", sc, "flight_connections");             
            Optional<Bookings> selectedBooking = bookingsService.getBookingById(id);
            if (selectedBooking.isPresent()) { System.out.println(selectedBooking.get().toString()); }
            System.out.println("\nDesea consultar otro vuelo? si/ENTER");
            rta = sc.nextLine();
        }
    }
}