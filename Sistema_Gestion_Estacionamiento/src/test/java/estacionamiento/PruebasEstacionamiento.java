package estacionamiento;

import estacionamiento.gestor.GestorEspacios;
import estacionamiento.gestor.GestorEstancias;
import estacionamiento.gestor.GestorVehiculos;
import estacionamiento.modelo.Automovil;
import estacionamiento.modelo.Camioneta;
import estacionamiento.modelo.EspacioEstacionamiento;
import estacionamiento.modelo.Motocicleta;
import estacionamiento.modelo.RegistroEstacionamiento;
import estacionamiento.modelo.Vehiculo;
import java.time.LocalDateTime;

/** Pruebas manuales automatizadas mediante Java puro, sin JUnit ni otras bibliotecas. */
public class PruebasEstacionamiento {
    private static int aprobadas;
    private static int fallidas;

    public static void main(String[] args) {
        probarTarifas();
        probarVehiculos();
        probarEspaciosYEstancias();
        probarCapacidad();

        System.out.println("\n============================================");
        System.out.println("RESULTADO: " + aprobadas + " aprobadas | " + fallidas + " fallidas");
        System.out.println("============================================");
        if (fallidas > 0) {
            throw new AssertionError("Hay pruebas fallidas. Revisar los resultados anteriores.");
        }
    }

    private static void probarTarifas() {
        Automovil auto = new Automovil("T-A", "Toyota", "Rojo");
        Motocicleta moto = new Motocicleta("T-M", "Yamaha", "Negro");
        Camioneta camioneta = new Camioneta("T-C", "Chevrolet", "Blanco");

        comprobar("Automóvil: 60 minutos = $1.00", casiIgual(auto.calcularTarifa(60), 1.00));
        comprobar("Automóvil: 61 minutos = $1.50", casiIgual(auto.calcularTarifa(61), 1.50));
        comprobar("Automóvil: 121 minutos = $2.00", casiIgual(auto.calcularTarifa(121), 2.00));
        comprobar("Motocicleta: 60 minutos = $0.50", casiIgual(moto.calcularTarifa(60), 0.50));
        comprobar("Motocicleta: 61 minutos = $0.75", casiIgual(moto.calcularTarifa(61), 0.75));
        comprobar("Motocicleta: 121 minutos = $1.00", casiIgual(moto.calcularTarifa(121), 1.00));
        comprobar("Camioneta: 60 minutos = $1.50", casiIgual(camioneta.calcularTarifa(60), 1.50));
        comprobar("Camioneta: 61 minutos = $2.25", casiIgual(camioneta.calcularTarifa(61), 2.25));
        comprobar("Camioneta: 121 minutos = $3.00", casiIgual(camioneta.calcularTarifa(121), 3.00));
        comprobar("Cero minutos no genera cobro", casiIgual(auto.calcularTarifa(0), 0.0));
        comprobar("Minutos negativos no generan cobro", casiIgual(auto.calcularTarifa(-5), 0.0));

        LocalDateTime inicio = LocalDateTime.of(2026, 10, 30, 8, 0, 0);
        comprobar("Una permanencia positiva de un segundo cobra hora mínima",
                casiIgual(auto.calcularTarifa(inicio, inicio.plusSeconds(1)), 1.00));
        comprobar("Una permanencia de 60 minutos y un segundo cobra dos horas",
                casiIgual(auto.calcularTarifa(inicio, inicio.plusSeconds(3601)), 1.50));
        comprobar("Fecha nula no genera cobro", casiIgual(auto.calcularTarifa(null, inicio), 0.0));
        comprobar("Salida igual a entrada no genera cobro", casiIgual(auto.calcularTarifa(inicio, inicio), 0.0));
    }

    private static void probarVehiculos() {
        GestorVehiculos gestor = new GestorVehiculos();
        comprobar("Se cargan dos vehículos demostrativos", gestor.getCantidadVehiculos() == 2);
        comprobar("La placa se normaliza a mayúsculas y sin espacios",
                "ABC-123".equals(gestor.buscarVehiculo(" abc-123 ").getPlaca()));
        comprobar("No se permite una placa duplicada aunque cambie el tipo",
                !gestor.registrarVehiculo(new Motocicleta("abc-123", "Otra", "Azul")));
        comprobar("No se permite placa vacía", !gestor.registrarVehiculo(new Automovil("   ", "Marca", "Color")));
        comprobar("No se permite marca vacía", !gestor.registrarVehiculo(new Automovil("VAC-001", " ", "Color")));
        comprobar("No se permite color vacío", !gestor.registrarVehiculo(new Automovil("VAC-002", "Marca", " ")));
        Vehiculo auto = new Automovil("EQ-001", "Toyota", "Gris");
        Vehiculo moto = new Motocicleta("EQ-001", "Yamaha", "Gris");
        comprobar("equals compara vehículos por placa entre subclases", auto.equals(moto));
        comprobar("hashCode es coherente con equals", auto.hashCode() == moto.hashCode());
    }

    private static void probarEspaciosYEstancias() {
        GestorVehiculos vehiculos = new GestorVehiculos();
        GestorEspacios espacios = new GestorEspacios();
        GestorEstancias estancias = new GestorEstancias(vehiculos, espacios);
        LocalDateTime entrada = LocalDateTime.of(2026, 10, 30, 8, 0, 0);

        comprobar("La matriz contiene dos zonas y cinco espacios por zona",
                espacios.obtenerMatrizZonas().length == 2
                && espacios.obtenerMatrizZonas()[0].length == 5
                && espacios.obtenerMatrizZonas()[1].length == 5);
        comprobar("La capacidad total es de diez espacios", espacios.getCapacidadTotal() == 10);
        comprobar("Los diez espacios inician libres", espacios.getCantidadEspaciosDisponibles() == 10);
        comprobar("No se registra entrada de una placa desconocida",
                estancias.registrarEntrada("NO-EXISTE", entrada) == null);

        RegistroEstacionamiento registro = estancias.registrarEntrada("ABC-123", entrada);
        comprobar("La entrada asigna el primer espacio A-1",
                registro != null && "A-1".equals(registro.getEspacio().getCodigo()));
        comprobar("Al ingresar un vehículo el espacio queda ocupado",
                espacios.getCantidadEspaciosOcupados() == 1);
        comprobar("La estancia aparece en la consulta de activas",
                estancias.obtenerEstanciasActivas().size() == 1);
        comprobar("No se admite una segunda estancia activa para la misma placa",
                estancias.registrarEntrada("ABC-123", entrada.plusMinutes(2)) == null);
        comprobar("No se admite salida antes de la entrada",
                estancias.registrarSalida("ABC-123", entrada.minusMinutes(1)) == null);
        comprobar("No se admite salida con la misma hora de entrada",
                estancias.registrarSalida("ABC-123", entrada) == null);
        comprobar("Las salidas inválidas no cierran la estancia", registro.isActiva());

        RegistroEstacionamiento cerrado = estancias.registrarSalida("ABC-123", entrada.plusMinutes(61));
        comprobar("La salida válida devuelve la estancia finalizada", cerrado == registro && !registro.isActiva());
        comprobar("La estancia de 61 minutos cobra dos horas de automóvil",
                casiIgual(registro.getCobroTotal(), 1.50));
        comprobar("La salida libera el espacio asignado", espacios.getCantidadEspaciosDisponibles() == 10);
        comprobar("La estancia finalizada pasa al historial", estancias.obtenerHistorial().size() == 1);
        comprobar("La estancia cerrada ya no aparece como activa", estancias.obtenerEstanciasActivas().isEmpty());
        comprobar("La recaudación acumulada coincide con el cobro", casiIgual(estancias.getTotalRecaudado(), 1.50));
        comprobar("No se puede cerrar dos veces una misma estancia",
                estancias.registrarSalida("ABC-123", entrada.plusHours(2)) == null);

        RegistroEstacionamiento reingreso = estancias.registrarEntrada("ABC-123", entrada.plusHours(3));
        comprobar("Un vehículo puede ingresar de nuevo después de cerrar su estancia", reingreso != null);
        comprobar("El reingreso queda en el siguiente espacio libre disponible",
                reingreso != null && "A-1".equals(reingreso.getEspacio().getCodigo()));
    }

    private static void probarCapacidad() {
        GestorVehiculos vehiculos = new GestorVehiculos();
        GestorEspacios espacios = new GestorEspacios();
        GestorEstancias estancias = new GestorEstancias(vehiculos, espacios);
        LocalDateTime base = LocalDateTime.of(2026, 10, 30, 9, 0, 0);

        for (int i = 1; i <= 10; i++) {
            vehiculos.registrarVehiculo(new Automovil("FULL-" + i, "Marca", "Plata"));
        }
        for (int i = 1; i <= 10; i++) {
            RegistroEstacionamiento r = estancias.registrarEntrada("FULL-" + i, base.plusMinutes(i));
            comprobar("Ingreso número " + i + " ocupa un espacio", r != null);
        }
        comprobar("Los diez espacios quedan ocupados", espacios.getCantidadEspaciosOcupados() == 10);
        vehiculos.registrarVehiculo(new Automovil("FULL-11", "Marca", "Plata"));
        comprobar("El sistema rechaza el ingreso cuando la matriz está llena",
                estancias.registrarEntrada("FULL-11", base.plusHours(1)) == null);
        comprobar("El número de espacios ocupados no supera diez",
                espacios.getCantidadEspaciosOcupados() == espacios.getCapacidadTotal());

        GestorVehiculos catalogo = new GestorVehiculos();
        for (int i = 1; i <= GestorVehiculos.CAPACIDAD_REGISTRO - 2; i++) {
            catalogo.registrarVehiculo(new Automovil("CAT-" + i, "Marca", "Blanco"));
        }
        comprobar("El catálogo admite hasta cien vehículos incluyendo los dos precargados",
                catalogo.getCantidadVehiculos() == 100);
        comprobar("El catálogo rechaza un vehículo cuando alcanza cien registros",
                !catalogo.registrarVehiculo(new Automovil("CAT-EXTRA", "Marca", "Blanco")));
    }

    private static boolean casiIgual(Double actual, double esperado) {
        return actual != null && Math.abs(actual - esperado) < 0.0001;
    }

    private static void comprobar(String nombre, boolean resultado) {
        if (resultado) {
            aprobadas++;
            System.out.println("[OK] " + nombre);
        } else {
            fallidas++;
            System.out.println("[FALLO] " + nombre);
        }
    }
}
