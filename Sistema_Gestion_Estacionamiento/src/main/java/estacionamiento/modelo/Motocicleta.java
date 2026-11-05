package estacionamiento.modelo;

/** Especialización de Vehiculo correspondiente a una motocicleta. */
public class Motocicleta extends Vehiculo {
    private static final Double PRIMERA_HORA = 0.50;
    private static final Double HORA_ADICIONAL = 0.25;

    public Motocicleta(String placa, String marca, String color) {
        super(placa, marca, color);
    }

    @Override
    public String getTipo() { return "Motocicleta"; }

    @Override
    protected Double getTarifaPrimeraHora() { return PRIMERA_HORA; }

    @Override
    protected Double getTarifaHoraAdicional() { return HORA_ADICIONAL; }

    @Override
    public Double calcularTarifa(long minutos) {
        return calcularTarifaPorHorasIniciadas(minutos);
    }
}
