package estacionamiento.main;

import estacionamiento.gestor.GestorEspacios;
import estacionamiento.gestor.GestorEstancias;
import estacionamiento.gestor.GestorVehiculos;
import estacionamiento.menu.MenuConsultas;
import estacionamiento.menu.MenuOperaciones;
import estacionamiento.menu.MenuPrincipal;
import estacionamiento.menu.MenuVehiculos;
import java.util.Scanner;

/** Punto de entrada: inicializa los objetos compartidos y arranca el menú principal. */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GestorVehiculos gestorVehiculos = new GestorVehiculos();
        GestorEspacios gestorEspacios = new GestorEspacios();
        GestorEstancias gestorEstancias = new GestorEstancias(gestorVehiculos, gestorEspacios);

        MenuVehiculos menuVehiculos = new MenuVehiculos(scanner, gestorVehiculos);
        MenuOperaciones menuOperaciones = new MenuOperaciones(scanner, gestorEstancias);
        MenuConsultas menuConsultas = new MenuConsultas(scanner, gestorEspacios, gestorEstancias);

        MenuPrincipal menuPrincipal = new MenuPrincipal(
                scanner, menuVehiculos, menuOperaciones, menuConsultas);
        menuPrincipal.iniciar();
    }
}
