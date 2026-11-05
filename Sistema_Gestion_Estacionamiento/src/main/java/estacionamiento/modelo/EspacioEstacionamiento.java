package estacionamiento.modelo;

/** Representa un espacio y mantiene la referencia al vehículo que lo ocupa. */
public class EspacioEstacionamiento {
    private final String codigo;
    private Vehiculo vehiculoActual;

    public EspacioEstacionamiento(String codigo) {
        this.codigo = codigo == null ? "" : codigo.trim();
        this.vehiculoActual = null;
    }

    public String getCodigo() { return codigo; }
    public Vehiculo getVehiculoActual() { return vehiculoActual; }

    public boolean isDisponible() {
        return vehiculoActual == null;
    }

    public boolean ocupar(Vehiculo vehiculo) {
        if (!isDisponible() || vehiculo == null) {
            return false;
        }
        vehiculoActual = vehiculo;
        return true;
    }

    /** Libera el espacio solo cuando el vehículo indicado es quien lo ocupa. */
    public boolean liberar(Vehiculo vehiculo) {
        if (vehiculoActual == null || vehiculo == null || vehiculoActual != vehiculo) {
            return false;
        }
        vehiculoActual = null;
        return true;
    }

    @Override
    public String toString() {
        if (isDisponible()) {
            return "Espacio " + codigo + " [LIBRE]";
        }
        return "Espacio " + codigo + " [OCUPADO por " + vehiculoActual.getPlaca() + "]";
    }
}
