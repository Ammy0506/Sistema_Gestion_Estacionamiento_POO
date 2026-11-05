package estacionamiento.modelo;

/** Especialización de Vehiculo correspondiente a un automóvil. */
public class Automovil extends Vehiculo {
    private static final Double PRIMERA_HORA = 1.00;
    private static final Double HORA_ADICIONAL = 0.50;

    public Automovil(String placa, String marca, String color) {
        super(placa, marca, color);
    }

    @Override
    public String getTipo() { return "Automóvil"; }

    @Override
    protected Double getTarifaPrimeraHora() { return PRIMERA_HORA; }

    @Override
    protected Double getTarifaHoraAdicional() { return HORA_ADICIONAL; }

    @Override
    public Double calcularTarifa(long minutos) {
        return calcularTarifaPorHorasIniciadas(minutos);
    }
}
