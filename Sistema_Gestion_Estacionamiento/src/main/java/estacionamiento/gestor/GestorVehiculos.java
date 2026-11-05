package estacionamiento.gestor;

import estacionamiento.modelo.Automovil;
import estacionamiento.modelo.Motocicleta;
import estacionamiento.modelo.Vehiculo;
import java.util.Locale;

/** Administra el catálogo fijo de vehículos registrados. */
public class GestorVehiculos {
    public static final int CAPACIDAD_REGISTRO = 100;

    private final Vehiculo[] vehiculosRegistrados;
    private int contadorVehiculos;

    public GestorVehiculos() {
        vehiculosRegistrados = new Vehiculo[CAPACIDAD_REGISTRO];
        contadorVehiculos = 0;
        cargarVehiculosDemostracion();
    }

    /** Incluye dos registros ficticios para que el sistema no inicie vacío. */
    private void cargarVehiculosDemostracion() {
        registrarVehiculo(new Automovil("ABC-123", "Toyota", "Rojo"));
        registrarVehiculo(new Motocicleta("XYZ-987", "Yamaha", "Negro"));
    }

    public boolean registrarVehiculo(Vehiculo nuevoVehiculo) {
        if (nuevoVehiculo == null || nuevoVehiculo.getPlaca().isBlank()
                || nuevoVehiculo.getMarca().isBlank() || nuevoVehiculo.getColor().isBlank()) {
            return false;
        }
        if (buscarVehiculo(nuevoVehiculo.getPlaca()) != null
                || contadorVehiculos >= vehiculosRegistrados.length) {
            return false;
        }
        vehiculosRegistrados[contadorVehiculos] = nuevoVehiculo;
        contadorVehiculos++;
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null || placa.isBlank()) {
            return null;
        }
        String placaNormalizada = placa.trim().toUpperCase(Locale.ROOT);
        for (int i = 0; i < contadorVehiculos; i++) {
            if (vehiculosRegistrados[i].getPlaca().equals(placaNormalizada)) {
                return vehiculosRegistrados[i];
            }
        }
        return null;
    }

    /** Devuelve una copia del arreglo para impedir cambios externos al catálogo. */
    public Vehiculo[] obtenerVehiculosRegistrados() {
        Vehiculo[] copia = new Vehiculo[contadorVehiculos];
        for (int i = 0; i < contadorVehiculos; i++) {
            copia[i] = vehiculosRegistrados[i];
        }
        return copia;
    }

    public int getCantidadVehiculos() {
        return contadorVehiculos;
    }
}
