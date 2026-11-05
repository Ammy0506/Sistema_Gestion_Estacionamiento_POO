package estacionamiento.menu;

import estacionamiento.gestor.GestorEspacios;
import estacionamiento.gestor.GestorEstancias;
import estacionamiento.modelo.EspacioEstacionamiento;
import estacionamiento.modelo.RegistroEstacionamiento;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/** Menú del módulo de consultas y reportes básicos. */
public class MenuConsultas {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    private final Scanner scanner;
    private final GestorEspacios gestorEspacios;
    private final GestorEstancias gestorEstancias;

    public MenuConsultas(Scanner scanner, GestorEspacios gestorEspacios,
                         GestorEstancias gestorEstancias) {
        this.scanner = scanner;
        this.gestorEspacios = gestorEspacios;
        this.gestorEstancias = gestorEstancias;
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
                    case "1": consultarEspacios(); break;
                    case "2": consultarEstanciasActivas(); break;
                    case "3": consultarHistorial(); break;
                    case "4": consultarRecaudacion(); break;
                    case "5": continuar = false; break;
                    default: System.out.println("Opción no válida. Seleccione una opción del 1 al 5.");
                }
            }
        } while (continuar);
    }

    private void mostrarMenu() {
        System.out.println("\n--- MÓDULO: CONSULTAS Y REPORTES ---");
        System.out.println("1. Consultar espacios libres y ocupados");
        System.out.println("2. Consultar estancias activas");
        System.out.println("3. Consultar historial de estancias finalizadas");
        System.out.println("4. Consultar resumen de recaudación");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    private void consultarEspacios() {
        System.out.println("\n--- ESTADO DE LOS ESPACIOS ---");
        EspacioEstacionamiento[][] matriz = gestorEspacios.obtenerMatrizZonas();
        for (int fila = 0; fila < matriz.length; fila++) {
            System.out.println("Zona " + (char) ('A' + fila) + ":");
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                System.out.println("  " + matriz[fila][columna]);
            }
        }
        System.out.println("Total disponibles: " + gestorEspacios.getCantidadEspaciosDisponibles());
        System.out.println("Total ocupados: " + gestorEspacios.getCantidadEspaciosOcupados());
        System.out.println("Capacidad total: " + gestorEspacios.getCapacidadTotal());
    }

    private void consultarEstanciasActivas() {
        System.out.println("\n--- ESTANCIAS ACTIVAS ---");
        ArrayList<RegistroEstacionamiento> activas = gestorEstancias.obtenerEstanciasActivas();
        if (activas.isEmpty()) {
            System.out.println("No existen vehículos dentro del estacionamiento.");
            return;
        }
        for (RegistroEstacionamiento registro : activas) {
            System.out.println(registro.getVehiculo().getTipo() + " | Placa: "
                    + registro.getVehiculo().getPlaca() + " | Espacio: "
                    + registro.getEspacio().getCodigo() + " | Entrada: "
                    + registro.getHoraEntrada().format(FORMATO));
        }
        System.out.println("Total de estancias activas: " + activas.size());
    }

    private void consultarHistorial() {
        System.out.println("\n--- HISTORIAL DE ESTANCIAS FINALIZADAS ---");
        ArrayList<RegistroEstacionamiento> historial = gestorEstancias.obtenerHistorial();
        if (historial.isEmpty()) {
            System.out.println("Todavía no existen estancias finalizadas.");
            return;
        }
        for (RegistroEstacionamiento registro : historial) {
            System.out.println("Placa: " + registro.getVehiculo().getPlaca()
                    + " | Tipo: " + registro.getVehiculo().getTipo()
                    + " | Entrada: " + registro.getHoraEntrada().format(FORMATO)
                    + " | Salida: " + registro.getHoraSalida().format(FORMATO)
                    + " | Minutos: " + registro.getMinutosPermanencia()
                    + " | Cobro: $" + String.format(Locale.US, "%.2f", registro.getCobroTotal()));
        }
        System.out.println("Total de estancias finalizadas: " + historial.size());
    }

    private void consultarRecaudacion() {
        System.out.println("\n--- RESUMEN DE RECAUDACIÓN ---");
        System.out.println("Estancias finalizadas: " + gestorEstancias.obtenerHistorial().size());
        System.out.println("Estancias activas: " + gestorEstancias.obtenerEstanciasActivas().size());
        System.out.printf(Locale.US, "Total recaudado en esta ejecución: $%.2f%n",
                gestorEstancias.getTotalRecaudado());
    }
}
