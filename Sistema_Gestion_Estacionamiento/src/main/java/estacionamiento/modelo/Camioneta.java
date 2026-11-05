package estacionamiento.modelo;

/** Especialización de Vehiculo correspondiente a una camioneta. */
public class Camioneta extends Vehiculo {
    private static final Double PRIMERA_HORA = 1.50;
    private static final Double HORA_ADICIONAL = 0.75;

    public Camioneta(String placa, String marca, String color) {
        super(placa, marca, color);
    }

    @Override
    public String getTipo() { return "Camioneta"; }

    @Override
    protected Double getTarifaPrimeraHora() { return PRIMERA_HORA; }

    @Override
    protected Double getTarifaHoraAdicional() { return HORA_ADICIONAL; }

    @Override
    public Double calcularTarifa(long minutos) {
        return calcularTarifaPorHorasIniciadas(minutos);
    }
}
