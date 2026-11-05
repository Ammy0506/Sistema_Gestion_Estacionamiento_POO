package estacionamiento.interfaces;

/** Contrato para los objetos que calculan el valor de una estancia. */
public interface Tarificable {
    /**
     * Calcula el valor según los minutos facturables de permanencia.
     * Una permanencia positiva se cobra por horas iniciadas.
     */
    Double calcularTarifa(long minutos);
}
