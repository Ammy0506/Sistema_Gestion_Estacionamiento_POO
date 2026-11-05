package estacionamiento.gestor;

import estacionamiento.modelo.EspacioEstacionamiento;

/** Administra la matriz de dos zonas con cinco espacios cada una. */
public class GestorEspacios {
    private static final int NUMERO_ZONAS = 2;
    private static final int ESPACIOS_POR_ZONA = 5;

    private final EspacioEstacionamiento[][] matrizZonas;

    public GestorEspacios() {
        matrizZonas = new EspacioEstacionamiento[NUMERO_ZONAS][ESPACIOS_POR_ZONA];
        for (int fila = 0; fila < matrizZonas.length; fila++) {
            char zona = (char) ('A' + fila);
            for (int columna = 0; columna < matrizZonas[fila].length; columna++) {
                String codigo = zona + "-" + (columna + 1);
                matrizZonas[fila][columna] = new EspacioEstacionamiento(codigo);
            }
        }
    }

    public EspacioEstacionamiento buscarEspacioDisponible() {
        for (int fila = 0; fila < matrizZonas.length; fila++) {
            for (int columna = 0; columna < matrizZonas[fila].length; columna++) {
                if (matrizZonas[fila][columna].isDisponible()) {
                    return matrizZonas[fila][columna];
                }
            }
        }
        return null;
    }

    /** Copia la estructura del arreglo; los objetos se consultan mediante sus métodos. */
    public EspacioEstacionamiento[][] obtenerMatrizZonas() {
        EspacioEstacionamiento[][] copia = new EspacioEstacionamiento[matrizZonas.length][];
        for (int i = 0; i < matrizZonas.length; i++) {
            copia[i] = matrizZonas[i].clone();
        }
        return copia;
    }

    public int getCantidadEspaciosDisponibles() {
        int disponibles = 0;
        for (int fila = 0; fila < matrizZonas.length; fila++) {
            for (int columna = 0; columna < matrizZonas[fila].length; columna++) {
                if (matrizZonas[fila][columna].isDisponible()) {
                    disponibles++;
                }
            }
        }
        return disponibles;
    }

    public int getCantidadEspaciosOcupados() {
        return NUMERO_ZONAS * ESPACIOS_POR_ZONA - getCantidadEspaciosDisponibles();
    }

    public int getCapacidadTotal() {
        return NUMERO_ZONAS * ESPACIOS_POR_ZONA;
    }
}
