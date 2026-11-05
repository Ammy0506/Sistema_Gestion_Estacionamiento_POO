package estacionamiento.menu;

import estacionamiento.gestor.GestorVehiculos;
import estacionamiento.modelo.Automovil;
import estacionamiento.modelo.Camioneta;
import estacionamiento.modelo.Motocicleta;
import estacionamiento.modelo.Vehiculo;
import java.util.Scanner;

/** Menú del módulo de gestión de vehículos. */
public class MenuVehiculos {
    private final Scanner scanner;
    private final GestorVehiculos gestorVehiculos;

    public MenuVehiculos(Scanner scanner, GestorVehiculos gestorVehiculos) {
        this.scanner = scanner;
        this.gestorVehiculos = gestorVehiculos;
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
                    case "1": registrarVehiculo(); break;
                    case "2": listarVehiculos(); break;
                    case "3": continuar = false; break;
                    default: System.out.println("Opción no válida. Seleccione 1, 2 o 3.");
                }
            }
        } while (continuar);
    }

    private void mostrarMenu() {
        System.out.println("\n--- MÓDULO: GESTIÓN DE VEHÍCULOS ---");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Consultar vehículos registrados");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    private void registrarVehiculo() {
        System.out.println("\nTipo de vehículo:");
        System.out.println("1. Automóvil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta");
        System.out.print("Seleccione el tipo: ");
        if (!scanner.hasNextLine()) return;
        String tipo = scanner.nextLine().trim();

        System.out.print("Placa: ");
        if (!scanner.hasNextLine()) return;
        String placa = scanner.nextLine();
        System.out.print("Marca: ");
        if (!scanner.hasNextLine()) return;
        String marca = scanner.nextLine();
        System.out.print("Color: ");
        if (!scanner.hasNextLine()) return;
        String color = scanner.nextLine();

        Vehiculo vehiculo = null;
        switch (tipo) {
            case "1": vehiculo = new Automovil(placa, marca, color); break;
            case "2": vehiculo = new Motocicleta(placa, marca, color); break;
            case "3": vehiculo = new Camioneta(placa, marca, color); break;
            default: System.out.println("Tipo no válido. No se registró el vehículo.");
        }

        if (vehiculo != null) {
            if (gestorVehiculos.registrarVehiculo(vehiculo)) {
                System.out.println("Vehículo registrado correctamente: " + vehiculo);
            } else {
                System.out.println("No se pudo registrar. Revise que los datos estén completos, que la placa no se repita y que exista capacidad.");
            }
        }
    }

    private void listarVehiculos() {
        System.out.println("\n--- VEHÍCULOS REGISTRADOS ---");
        Vehiculo[] vehiculos = gestorVehiculos.obtenerVehiculosRegistrados();
        if (vehiculos.length == 0) {
            System.out.println("No existen vehículos registrados.");
            return;
        }
        for (int i = 0; i < vehiculos.length; i++) {
            System.out.println((i + 1) + ". " + vehiculos[i]);
        }
        System.out.println("Total registrados: " + vehiculos.length);
    }
}
