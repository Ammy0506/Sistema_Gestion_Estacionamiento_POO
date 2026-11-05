# Sistema de Gestión y Control de Estacionamiento

Proyecto académico de Programación Orientada a Objetos (CCPG1052), desarrollado en Java 17 o superior y ejecutado por consola.

## Funcionalidades

- Registro y listado de vehículos (automóvil, motocicleta y camioneta).
- Catálogo con arreglo estático de hasta 100 vehículos.
- Dos zonas de estacionamiento con cinco espacios cada una, administradas mediante matriz bidimensional.
- Registro de entradas, asignación del primer espacio disponible, registro de salidas y liberación de espacios.
- Cálculo de tarifas por hora iniciada.
- Consultas de espacios, estancias activas, historial y recaudación de la ejecución.
- Datos de demostración precargados: automóvil ABC-123 y motocicleta XYZ-987.

## Tarifas

| Tipo | Primera hora o fracción | Cada hora adicional iniciada |
|---|---:|---:|
| Automóvil | USD 1,00 | USD 0,50 |
| Motocicleta | USD 0,50 | USD 0,25 |
| Camioneta | USD 1,50 | USD 0,75 |

Una permanencia positiva de hasta 60 minutos se cobra como primera hora. Cada fracción que exceda una hora inicia una nueva hora facturable. La fecha y hora de operación se obtiene usando `America/Guayaquil`.

## Arquitectura

- `estacionamiento.interfaces`: contrato `Tarificable`.
- `estacionamiento.modelo`: `Vehiculo`, sus especializaciones, `EspacioEstacionamiento` y `RegistroEstacionamiento`.
- `estacionamiento.gestor`: `GestorVehiculos`, `GestorEspacios` y `GestorEstancias`.
- `estacionamiento.menu`: un menú independiente por módulo y el menú principal integrador.
- `estacionamiento.main`: `Main`, que inicializa los objetos compartidos y arranca el menú principal.

## Ejecutar

### NetBeans

1. Abra NetBeans.
2. Seleccione **File > Open Project**.
3. Seleccione la carpeta que contiene `pom.xml`.
4. Ejecute `estacionamiento.main.Main`.

### Windows (sin Maven)

Ejecute `scripts\\ejecutar.bat` desde el Explorador o la terminal. Se requiere JDK 17 o superior.

### Linux/macOS (sin Maven)

```bash
./scripts/ejecutar.sh
```

## Ejecutar las pruebas sin frameworks

Las pruebas son una clase Java independiente y no utilizan JUnit ni bibliotecas externas.

- Windows: `scripts\\probar.bat`
- Linux/macOS: `./scripts/probar.sh`

## Alcance y limitaciones

Los datos se almacenan en memoria y se pierden al cerrar el programa. No se utilizan bases de datos, archivos de persistencia, interfaces gráficas, sensores, barreras automáticas, servicios externos ni APIs.

## Documentación del proyecto

- `docs/diagrama_clases.png`: modelo de clases y relaciones.
- `docs/captura_01.png` a `docs/captura_04.png`: evidencias de ejecución por consola.
- `docs/guion_sustentacion.md`: guion sugerido para la exposición grupal.
- `docs/02-Informe_del_Proyecto_Sistema_Gestion_Estacionamiento.docx`: informe editable del proyecto.
- `pruebas.log`: salida de las 58 comprobaciones automatizadas.
- `demo_consola.txt`: recorrido de demostración del menú modular.

La arquitectura y los módulos aplican las etapas de la guía de prompts del docente adaptadas al estacionamiento. Los nombres VideoClub Xelula que aparecen en la guía son solo ejemplos y no forman parte de este sistema.
