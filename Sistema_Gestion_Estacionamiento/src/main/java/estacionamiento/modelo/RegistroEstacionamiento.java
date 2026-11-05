package estacionamiento.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** Representa una estancia, desde la entrada hasta su cierre y cobro. */
public class RegistroEstacionamiento {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
    private static final ZoneId ZONA_ECUADOR = ZoneId.of("America/Guayaquil");

    private final Vehiculo vehiculo;
    private final EspacioEstacionamiento espacio;
    private final LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Double cobroTotal;

    public RegistroEstacionamiento(Vehiculo vehiculo, EspacioEstacionamiento espacio,
                                   LocalDateTime horaEntrada) {
        this.vehiculo = vehiculo;
        this.espacio = espacio;
        this.horaEntrada = horaEntrada;
        this.horaSalida = null;
        this.cobroTotal = 0.0;
    }

    public Vehiculo getVehiculo() { return vehiculo; }
    public EspacioEstacionamiento getEspacio() { return espacio; }
    public LocalDateTime getHoraEntrada() { return horaEntrada; }
    public LocalDateTime getHoraSalida() { return horaSalida; }
    public Double getCobroTotal() { return cobroTotal; }

    public boolean isActiva() {
        return horaSalida == null;
    }

    public long getMinutosPermanencia() {
        LocalDateTime fin = isActiva()
                ? LocalDateTime.now(ZONA_ECUADOR)
                : horaSalida;
        if (fin == null || !fin.isAfter(horaEntrada)) {
            return 0;
        }
        Duration duracion = Duration.between(horaEntrada, fin);
        long segundos = duracion.getSeconds();
        if (duracion.getNano() > 0) {
            segundos++;
        }
        long minutos = segundos / 60;
        if (segundos % 60 != 0) {
            minutos++;
        }
        return Math.max(1, minutos);
    }

    /** Cierra la estancia, calcula el cobro y libera el espacio en una sola operación. */
    public boolean finalizarEstancia(LocalDateTime salida) {
        if (!isActiva() || salida == null || !salida.isAfter(horaEntrada)
                || espacio == null || espacio.getVehiculoActual() != vehiculo) {
            return false;
        }
        Double tarifaCalculada = vehiculo.calcularTarifa(horaEntrada, salida);
        if (!espacio.liberar(vehiculo)) {
            return false;
        }
        horaSalida = salida;
        cobroTotal = tarifaCalculada;
        return true;
    }

    @Override
    public String toString() {
        String entrada = horaEntrada == null ? "N/D" : horaEntrada.format(FORMATO);
        if (isActiva()) {
            return "Estancia ACTIVA | " + vehiculo.getPlaca() + " | "
                    + espacio.getCodigo() + " | Entrada: " + entrada;
        }
        return "Estancia FINALIZADA | " + vehiculo.getPlaca() + " | "
                + espacio.getCodigo() + " | Entrada: " + entrada
                + " | Salida: " + horaSalida.format(FORMATO)
                + " | Cobro: $" + String.format(java.util.Locale.US, "%.2f", cobroTotal);
    }
}
