package estacionamiento.menu;

import java.util.Scanner;

/** Integra los menús de los módulos y permite volver al menú principal. */
public class MenuPrincipal {
    private final Scanner scanner;
    private final MenuVehiculos menuVehiculos;
    private final MenuOperaciones menuOperaciones;
    private final MenuConsultas menuConsultas;

    public MenuPrincipal(Scanner scanner, MenuVehiculos menuVehiculos,
                         MenuOperaciones menuOperaciones, MenuConsultas menuConsultas) {
        this.scanner = scanner;
        this.menuVehiculos = menuVehiculos;
        this.menuOperaciones = menuOperaciones;
        this.menuConsultas = menuConsultas;
    }

    public void iniciar() {
        boolean continuar = true;
        do {
            mostrarMenu();
            if (!scanner.hasNextLine()) {
                continuar = false;
            } else {
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1": abrirMenuVehiculos(); break;
                    case "2": abrirMenuOperaciones(); break;
                    case "3": abrirMenuConsultas(); break;
                    case "4": continuar = false; despedir(); break;
                    default: System.out.println("Opción no válida. Seleccione una opción del 1 al 4.");
                }
            }
        } while (continuar);
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n====================================================");
        System.out.println(" SISTEMA DE GESTIÓN Y CONTROL DE ESTACIONAMIENTO");
        System.out.println("====================================================");
        System.out.println("1. Gestión de vehículos");
        System.out.println("2. Operaciones del estacionamiento");
        System.out.println("3. Consultas y reportes");
        System.out.println("4. Salir del sistema");
        System.out.print("Seleccione un módulo: ");
    }

    private void abrirMenuVehiculos() { menuVehiculos.iniciar(); }
    private void abrirMenuOperaciones() { menuOperaciones.iniciar(); }
    private void abrirMenuConsultas() { menuConsultas.iniciar(); }

    private void despedir() {
        System.out.println("Gracias por utilizar el Sistema de Gestión y Control de Estacionamiento.");
        System.out.println("Los datos se eliminarán al cerrar el programa, porque se mantienen solo en memoria.");
    }
}
