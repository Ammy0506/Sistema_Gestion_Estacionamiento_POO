package estacionamiento.modelo;

import estacionamiento.interfaces.Tarificable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

/** Clase abstracta que reúne los datos y comportamientos comunes de los vehículos. */
public abstract class Vehiculo implements Tarificable {
    private final String placa;
    private final String marca;
    private final String color;

    protected Vehiculo(String placa, String marca, String color) {
        this.placa = placa == null ? "" : placa.trim().toUpperCase(Locale.ROOT);
        this.marca = marca == null ? "" : marca.trim();
        this.color = color == null ? "" : color.trim();
    }

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }

    public abstract String getTipo();
    protected abstract Double getTarifaPrimeraHora();
    protected abstract Double getTarifaHoraAdicional();

    /**
     * Reutiliza el cálculo de horas iniciadas sin duplicar la fórmula en las subclases.
     * El método público calcularTarifa(long) sigue siendo sobrescrito por cada tipo.
     */
    protected final Double calcularTarifaPorHorasIniciadas(long minutos) {
        if (minutos <= 0) {
            return 0.0;
        }
        long horasFacturables = minutos / 60;
        if (minutos % 60 != 0) {
            horasFacturables++;
        }
        return getTarifaPrimeraHora()
                + ((horasFacturables - 1) * getTarifaHoraAdicional());
    }

    /** Sobrecarga: calcula el cobro a partir de las fechas y horas de entrada y salida. */
    public final Double calcularTarifa(LocalDateTime entrada, LocalDateTime salida) {
        if (entrada == null || salida == null || !salida.isAfter(entrada)) {
            return 0.0;
        }
        Duration duracion = Duration.between(entrada, salida);
        long segundos = duracion.getSeconds();
        if (duracion.getNano() > 0) {
            segundos++;
        }
        long minutos = segundos / 60;
        if (segundos % 60 != 0) {
            minutos++;
        }
        if (minutos < 1) {
            minutos = 1;
        }
        return calcularTarifa(minutos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Vehiculo)) return false;
        Vehiculo otro = (Vehiculo) objeto;
        return placa.equals(otro.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }

    @Override
    public String toString() {
        return "Tipo: " + getTipo() + " | Placa: " + placa
                + " | Marca: " + marca + " | Color: " + color;
    }
}
