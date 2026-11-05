# Guion de sustentación - Sistema de Gestión y Control de Estacionamiento

Duración sugerida: 6 a 8 minutos. Los tres integrantes deben permanecer visibles y con audio claro durante toda la grabación.

## 1. Presentación y problema - Ammy Peralta Luna (1 minuto)

Buenos días. Nuestro proyecto se llama Sistema de Gestión y Control de Estacionamiento. Su propósito es organizar el registro de vehículos, las entradas y salidas, la disponibilidad de espacios y el cálculo de tarifas. La primera versión se ejecuta por consola y mantiene los datos en memoria; no utiliza base de datos ni archivos de persistencia.

El sistema contempla un encargado que opera la aplicación. Los vehículos pueden ser automóviles, motocicletas o camionetas. La capacidad es de diez espacios, distribuidos en dos zonas de cinco espacios: A-1 a A-5 y B-1 a B-5.

## 2. Demostración del funcionamiento - Israel Anastacio (2 a 3 minutos)

1. Abrir el menú principal y entrar a Gestión de vehículos.
2. Consultar los vehículos precargados: ABC-123 (automóvil) y XYZ-987 (motocicleta).
3. Registrar un vehículo nuevo, indicando tipo, placa, marca y color.
4. Volver al menú principal y entrar a Operaciones del estacionamiento.
5. Registrar la entrada de ABC-123 y mostrar el espacio asignado (el primero disponible).
6. Volver al menú principal y consultar los espacios y las estancias activas.
7. Registrar la salida del vehículo y mostrar la tarifa, el espacio liberado y la fecha/hora.
8. Consultar el historial y el resumen de recaudación.

Durante la demostración, explicar que una placa duplicada, un vehículo desconocido, una segunda estancia activa o un estacionamiento lleno impiden la entrada.

## 3. Diseño orientado a objetos - José Calderón (2 minutos)

El modelo utiliza una clase abstracta Vehiculo que implementa la interfaz Tarificable. Automovil, Motocicleta y Camioneta heredan de Vehiculo y sobrescriben el cálculo de tarifa. El uso de la referencia de tipo Vehiculo permite aplicar polimorfismo cuando se cierra una estancia.

EspacioEstacionamiento mantiene una referencia al vehículo que ocupa el espacio. RegistroEstacionamiento relaciona al vehículo con el espacio y conserva las horas y el cobro. Los atributos están encapsulados y la placa es inmutable para proteger la identificación del vehículo.

El proyecto emplea tres estructuras: un arreglo Vehiculo[100] para el catálogo, una matriz EspacioEstacionamiento[2][5] para las zonas y un ArrayList<RegistroEstacionamiento> para mantener las estancias activas y finalizadas durante la ejecución.

## 4. Arquitectura y pruebas - José Calderón (1 a 2 minutos)

El sistema se divide en tres módulos. Gestión de vehículos utiliza GestorVehiculos y MenuVehiculos. Operaciones utiliza GestorEstancias y MenuOperaciones, que coordina el catálogo y los espacios. Consultas y reportes utiliza MenuConsultas para mostrar disponibilidad, estancias activas, historial y recaudación.

Main crea una sola instancia de cada gestor y menú, y entrega el control a MenuPrincipal. Los submenús tienen la opción Volver al menú principal; no contienen reglas de negocio.

El proyecto fue compilado con Java 17 y se ejecutaron 58 comprobaciones con Java puro, sin JUnit: 58 aprobadas y cero fallidas. Las pruebas incluyen tarifas, placas duplicadas, entrada y salida, disponibilidad, historial, recaudación y límite de capacidad.

## 5. Cierre - Todos los integrantes (20 a 30 segundos)

El sistema cumple el alcance de la primera fase: aplicación de consola, orientación a objetos y datos en memoria. No incorpora funcionalidades externas. Gracias.

## Recomendaciones de grabación

- Mantener la cámara encendida y mostrar a los tres integrantes.
- Compartir la ventana donde se ejecuta la aplicación, no solamente el código.
- Hablar de forma clara y demostrar las opciones en el orden descrito.
- Usar datos ficticios; no mostrar información personal.
- Publicar el video en YouTube con acceso no restringido y copiar la URL al informe.
