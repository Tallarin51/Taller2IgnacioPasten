# Taller 2 POO - Simulador Pokémon

## Descripción

Este proyecto corresponde al Taller 2 de Programación Orientada a Objetos. El sistema consiste en un juego interactivo por consola inspirado en Pokémon, donde el jugador puede iniciar una nueva partida, continuar una partida guardada, capturar Pokémon, revisar su equipo, usar el PC para cambiar el orden de sus Pokémon, retar gimnasios, desafiar al Alto Mando, curar Pokémon y guardar su progreso.

El programa trabaja con archivos `.txt` para cargar la información base del juego y para guardar el progreso del jugador.

---

## Autor

**Nombre:** Ignacio Antonio Pastén Durán  
**RUT:** 22.067.577-7  
**Github:** Tallarin51  

---

## Tecnologías utilizadas

- Java
- Programación Orientada a Objetos
- Manejo de archivos `.txt`
- `ArrayList`
- `Scanner`
- `BufferedWriter`
- `Random`

---

## Estructura del proyecto

El proyecto está organizado en el package:

```java
package logica;
```
Las clases principales son:

```text
Main.java
Pokemon.java
Gimnasio.java
AltoMando.java
TablaTipos.java
```

---

## Clases del sistema

### Main

Clase principal del programa. Contiene el flujo general del juego, los menús, la carga de archivos, la captura de Pokémon, el acceso al PC, los combates, el guardado y la carga de partidas.

### Pokemon

Representa a un Pokémon del juego. Contiene datos como nombre, hábitat, porcentaje de aparición, estadísticas, tipo y estado.

### Gimnasio

Representa un gimnasio Pokémon. Contiene número, líder, estado y la lista de Pokémon del gimnasio.

### AltoMando

Representa un miembro del Alto Mando. Contiene número, nombre y una lista de seis Pokémon.

### TablaTipos

Clase utilizada para calcular la efectividad entre tipos de Pokémon durante los combates.

---

## Archivos utilizados

### Pokedex.txt

Contiene la información base de todos los Pokémon disponibles en el juego.

Formato:

```text
pokemon;habitat;porcentajeAparicion;vida;ataque;defensa;ataqueEspecial;defensaEspecial;velocidad;tipo
```

Ejemplo:

```text
Mawile;Cueva;0.2;50;85;85;55;55;50;Acero
```

---

### Habitats.txt

Contiene las zonas disponibles para capturar Pokémon.

Ejemplo:

```text
Lago
Cueva
Montaña
Bosque
Prado
Mar
```

---

### Gimnasios.txt

Contiene la información de los gimnasios, sus líderes, estado y Pokémon.

Formato:

```text
numeroGimnasio;lider;estado;cantidadPokemons;pokemon1;pokemon2;...
```

Ejemplo:

```text
1;EmmaLaArdillaRabiosa;Sin derrotar;3;Minun;Plusle;Emolga
```

---

### Alto Mando.txt

Contiene la información de los miembros del Alto Mando y sus Pokémon.

Formato:

```text
numeroAltoMando;nombre;pokemon1;pokemon2;pokemon3;pokemon4;pokemon5;pokemon6
```

Ejemplo:

```text
1;MartinGalactico;Magikarp;Noivern;Excadrill;Steelix;Lucario;Scizor
```

---

### Registros.txt

Archivo donde se guarda la partida del jugador.

Formato:

```text
nombreJugador;ultimoLiderDerrotado
pokemon1;estado
pokemon2;estado
```

Ejemplo:

```text
Ignacio;none
Mawile;Vivo
Groudon;Debilitado
```

---

## Funcionalidades implementadas

- Menú inicial.
- Nueva partida.
- Continuar partida guardada.
- Guardado de progreso en `Registros.txt`.
- Carga de Pokémon desde `Pokedex.txt`.
- Carga de hábitats desde `Habitats.txt`.
- Carga de gimnasios desde `Gimnasios.txt`.
- Carga del Alto Mando desde `Alto Mando.txt`.
- Captura de Pokémon según hábitat.
- Generación aleatoria de Pokémon usando porcentaje de aparición.
- Validación para no capturar Pokémon repetidos.
- Revisión del equipo del jugador.
- Acceso al PC para intercambiar posiciones de Pokémon.
- Curación de Pokémon debilitados.
- Combate contra gimnasios.
- Validación del orden de gimnasios.
- Combate contra el Alto Mando.
- Validación para desafiar al Alto Mando solo con los 8 gimnasios derrotados.
- Aplicación de efectividad de tipos en combate.

---

## Cómo ejecutar el programa

1. Abrir el proyecto en Eclipse, VS Code o un IDE compatible con Java.
2. Verificar que los archivos `.txt` estén en la raíz del proyecto.
3. Ejecutar la clase `Main.java`.
4. Usar el menú por consola para jugar.

---

## Menú inicial

```text
1) Continuar.
2) Nueva Partida.
3) Salir.
```

---

## Menú principal

```text
1) Revisar equipo.
2) Salir a capturar.
3) Acceso al PC (cambiar Pokémon del equipo).
4) Retar un gimnasio.
5) Desafío al Alto Mando.
6) Curar Pokémon.
7) Guardar.
8) Guardar y Salir.
```

---

## Reglas principales del juego

- Los primeros seis Pokémon capturados corresponden al equipo activo del jugador.
- Los Pokémon debilitados no pueden combatir.
- El jugador no puede capturar Pokémon repetidos.
- Los gimnasios deben retarse en orden.
- El Alto Mando solo puede desafiarse luego de derrotar los 8 gimnasios.
- Si el jugador pierde o se rinde contra el Alto Mando, debe comenzar el desafío nuevamente.
- El combate se decide comparando las estadísticas totales de ambos Pokémon.
- La efectividad de tipos modifica el puntaje del Pokémon atacante.

---

## Modelo general del sistema

```text
Jugador
 └── tiene muchos Pokémon

Gimnasio
 └── tiene varios Pokémon

AltoMando
 └── tiene seis Pokémon

TablaTipos
 └── calcula la efectividad entre tipos durante el combate
```

---

## Consideraciones

El sistema fue desarrollado utilizando conceptos de Programación Orientada a Objetos, como clases, objetos, encapsulamiento, listas de objetos, métodos y separación de responsabilidades. La clase `Main` controla el flujo principal del programa, mientras que las demás clases representan entidades del dominio del juego.
