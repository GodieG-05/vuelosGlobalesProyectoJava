package com.vuelosglobales.planes.adapters.in;

import java.text.MessageFormat;
import java.util.Scanner;
 
import com.vuelosglobales.Main;

public class PlanesConsoleAdapter {

    public void start() {
        
        String header = """
            -----------
            | AVIONES |
            -----------
            """;
        String[] menu = {"Registrar Avion","Actualizar Avion","Consultar Avion","Eliminar Avion","Salir"};
        
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
                    System.out.println("Ingrese la matrícula del Avión");
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