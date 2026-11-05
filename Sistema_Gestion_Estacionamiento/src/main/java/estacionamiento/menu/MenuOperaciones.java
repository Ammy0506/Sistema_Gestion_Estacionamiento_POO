package estacionamiento.menu;

import estacionamiento.gestor.GestorEstancias;
import estacionamiento.modelo.RegistroEstacionamiento;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

/** Menú del módulo de operaciones: ingresos y salidas del estacionamiento. */
public class MenuOperaciones {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    private final Scanner scanner;
    private final GestorEstancias gestorEstancias;

    public MenuOperaciones(Scanner scanner, GestorEstancias gestorEstancias) {
        this.scanner = scanner;
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
                    case "1": registrarEntrada(); break;
                    case "2": registrarSalida(); break;
                    case "3": continuar = false; break;
                    default: System.out.println("Opción no válida. Seleccione 1, 2 o 3.");
                }
            }
        } while (continuar);
    }

    private void mostrarMenu() {
        System.out.println("\n--- MÓDULO: OPERACIONES DEL ESTACIONAMIENTO ---");
        System.out.println("1. Registrar entrada de vehículo");
        System.out.println("2. Registrar salida y calcular tarifa");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    private void registrarEntrada() {
        System.out.print("Ingrese la placa del vehículo: ");
        if (!scanner.hasNextLine()) return;
        String placa = scanner.nextLine();
        RegistroEstacionamiento registro = gestorEstancias.registrarEntrada(placa);
        if (registro == null) {
            System.out.println("No se pudo registrar la entrada. Verifique que el vehículo exista, no tenga una estancia activa y haya espacios disponibles.");
            return;
        }
        System.out.println("Entrada registrada correctamente.");
        System.out.println("Vehículo: " + registro.getVehiculo().getTipo()
                + " | Placa: " + registro.getVehiculo().getPlaca());
        System.out.println("Espacio asignado: " + registro.getEspacio().getCodigo());
        System.out.println("Fecha y hora de entrada: " + registro.getHoraEntrada().format(FORMATO));
    }

    private void registrarSalida() {
        System.out.print("Ingrese la placa del vehículo: ");
        if (!scanner.hasNextLine()) return;
        String placa = scanner.nextLine();
        RegistroEstacionamiento registro = gestorEstancias.registrarSalida(placa);
        if (registro == null) {
            System.out.println("No se pudo registrar la salida. No existe una estancia activa o la operación no es válida.");
            return;
        }
        System.out.println("Salida registrada correctamente.");
        System.out.println("Vehículo: " + registro.getVehiculo().getTipo()
                + " | Placa: " + registro.getVehiculo().getPlaca());
        System.out.println("Espacio liberado: " + registro.getEspacio().getCodigo());
        System.out.println("Entrada: " + registro.getHoraEntrada().format(FORMATO));
        System.out.println("Salida: " + registro.getHoraSalida().format(FORMATO));
        System.out.println("Permanencia facturada: " + registro.getMinutosPermanencia() + " minuto(s)");
        System.out.printf(Locale.US, "Total a pagar: $%.2f%n", registro.getCobroTotal());
    }
}
