package estacionamiento.gestor;

import estacionamiento.modelo.EspacioEstacionamiento;
import estacionamiento.modelo.RegistroEstacionamiento;
import estacionamiento.modelo.Vehiculo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/** Coordina entradas, salidas, estancias activas, historial y recaudación. */
public class GestorEstancias {
    private static final ZoneId ZONA_ECUADOR = ZoneId.of("America/Guayaquil");

    private final GestorVehiculos gestorVehiculos;
    private final GestorEspacios gestorEspacios;
    private final ArrayList<RegistroEstacionamiento> registrosEstacionamiento;

    public GestorEstancias(GestorVehiculos gestorVehiculos, GestorEspacios gestorEspacios) {
        this.gestorVehiculos = gestorVehiculos;
        this.gestorEspacios = gestorEspacios;
        this.registrosEstacionamiento = new ArrayList<>();
    }

    public RegistroEstacionamiento registrarEntrada(String placa) {
        return registrarEntrada(placa, LocalDateTime.now(ZONA_ECUADOR));
    }

    /** Variante con fecha explícita para repetir pruebas con resultados controlados. */
    public RegistroEstacionamiento registrarEntrada(String placa, LocalDateTime horaEntrada) {
        Vehiculo vehiculo = gestorVehiculos.buscarVehiculo(placa);
        if (vehiculo == null || horaEntrada == null || buscarEstanciaActiva(placa) != null) {
            return null;
        }
        EspacioEstacionamiento espacio = gestorEspacios.buscarEspacioDisponible();
        if (espacio == null || !espacio.ocupar(vehiculo)) {
            return null;
        }
        RegistroEstacionamiento registro = new RegistroEstacionamiento(vehiculo, espacio, horaEntrada);
        registrosEstacionamiento.add(registro);
        return registro;
    }

    public RegistroEstacionamiento registrarSalida(String placa) {
        return registrarSalida(placa, LocalDateTime.now(ZONA_ECUADOR));
    }

    /** Devuelve el registro cerrado o null cuando la operación no cumple las reglas. */
    public RegistroEstacionamiento registrarSalida(String placa, LocalDateTime horaSalida) {
        RegistroEstacionamiento registro = buscarEstanciaActiva(placa);
        if (registro == null || horaSalida == null || !horaSalida.isAfter(registro.getHoraEntrada())) {
            return null;
        }
        if (!registro.finalizarEstancia(horaSalida)) {
            return null;
        }
        return registro;
    }

    private RegistroEstacionamiento buscarEstanciaActiva(String placa) {
        if (placa == null || placa.isBlank()) {
            return null;
        }
        String placaNormalizada = placa.trim();
        for (RegistroEstacionamiento registro : registrosEstacionamiento) {
            if (registro.isActiva()
                    && registro.getVehiculo().getPlaca().equalsIgnoreCase(placaNormalizada)) {
                return registro;
            }
        }
        return null;
    }

    public ArrayList<RegistroEstacionamiento> obtenerEstanciasActivas() {
        ArrayList<RegistroEstacionamiento> activas = new ArrayList<>();
        for (RegistroEstacionamiento registro : registrosEstacionamiento) {
            if (registro.isActiva()) {
                activas.add(registro);
            }
        }
        return activas;
    }

    public ArrayList<RegistroEstacionamiento> obtenerHistorial() {
        ArrayList<RegistroEstacionamiento> historial = new ArrayList<>();
        for (RegistroEstacionamiento registro : registrosEstacionamiento) {
            if (!registro.isActiva()) {
                historial.add(registro);
            }
        }
        return historial;
    }

    public Double getTotalRecaudado() {
        Double total = 0.0;
        for (RegistroEstacionamiento registro : registrosEstacionamiento) {
            if (!registro.isActiva()) {
                total += registro.getCobroTotal();
            }
        }
        return total;
    }

}
