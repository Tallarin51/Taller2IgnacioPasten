package logica;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class Main {
	
	private static Scanner s = new Scanner(System.in);
	
	private static ArrayList<Pokemon> listaPokedex = new ArrayList<Pokemon>();
	private static ArrayList<Pokemon> pokemonsJugador = new ArrayList<Pokemon>();
	private static ArrayList<Gimnasio> listaGimnasios = new ArrayList<Gimnasio>();
	private static ArrayList<AltoMando>  listaAltoMando = new ArrayList<AltoMando>();
	private static ArrayList<String> listaHabitats = new ArrayList<String>();
	
	private static String nombreJugador = "";
	private static String ultimoLiderDerrotado = "none";
	
	
	public static void main(String[] args) throws IOException {
		
		cargarArchivos();
		menuInicial();
		
		
	}

	private static void menuInicial() throws IOException {
		String respuesta;
		
		do {
			System.out.println("1) Continuar.\r\n"
					+ "2) Nueva Partida.\r\n"
					+ "3) Salir.\r\n"
					+ "> ");
			respuesta = s.nextLine();
			
			switch (respuesta) {
			
			case "1":
				if (existePartidaGuardada()) {
					cargarRegistros();
					menuPrincipal();
					return;
				} else {
					System.out.println("No existe una partida guardada");
				}
				break;
			 
			case "2":
				nuevaPartida();
				menuPrincipal();
				return;
				
			case "3":
				System.out.println("Saliendo...");
				break;
				
			default:
				System.out.println("Opcion inválida.");
			}
			
		} while (!respuesta.equalsIgnoreCase("3"));
		
	}

	private static void nuevaPartida() throws IOException {
		
		pokemonsJugador.clear();
		ultimoLiderDerrotado = "none";
		actualizarEstadosGimnasios();
		
		System.out.println("Ingrese apodo: ");
		nombreJugador = s.nextLine();
		
		guardarRegistros();
		
		System.out.println("Bienvenido " + nombreJugador + "!!");
	}

	private static void menuPrincipal() throws IOException {
		
		String respuesta;
		do {
			System.out.println("1) Revisar equipo.\r\n"
					+ "2) Salir a capturar.\r\n"
					+ "3) Acceso al PC (cambiar Pokémon del equipo).\r\n"
					+ "4) Retar un gimnasio.\r\n"
					+ "5) Desafío al Alto Mando.\r\n"
					+ "6) Curar Pokémon.\r\n"
					+ "7) Guardar.\r\n"
					+ "8) Guardar y Salir.\r\n"
					+ "> ");
			respuesta = s.nextLine();
			
			switch (respuesta) {
			
			case "1":
				revisarEquipo();
				break;
				
			case "2":
				salirACapturar();
				break;
				
			case "3":
				accesoPC();
				break;
				
			case "4":
				retarGimnasio();
				break;
				
			case "5":
				desafioAltoMando();
				break;
				
			case "6":
				curarPokemon();
				break;
				
			case "7":
				guardarRegistros();
				System.out.println("Partida guardada.");
				break;
				
			case "8":
				guardarRegistros();
				System.out.println("Nos vemos entrenador...");
				break;
				
			default:
				System.out.println("Opción inválida");
				break;
				
			}
			
		} while (!respuesta.equals("8"));
		
	}

	private static void desafioAltoMando() {
		
		if (obtenerUltimoGimnasioDerrotado() < 8) {
			System.out.println("No puedes desafiar al Alto Mando sin tener los 8 gimnasios derrotados");
			return;
		}
		if (pokemonsJugador.size() == 0) {
			System.out.println("No tienes Pokémon para combatir.");
			return;
		}
		if (!tienePokemonVivoEnEquipo()) {
			System.out.println("No tienes Pokémon vivos en tu equipo.");
			return;
		}
		reiniciarAltoMando();
		
		System.out.println("Has iniciado el Desafío al Alto Mando!");
		System.out.println("No podrás volver al menu hasta ganar, perder o rendirte");
		
		for (AltoMando miembro : listaAltoMando) {
			
			System.out.println();
			System.out.println("Desafiando a " + miembro.getNombre() + "!!");
			
			boolean gano = combatirContraAltoMando(miembro);
			
			if (!gano) {
				System.out.println("Has perdido o te has rendido");
				System.out.println("Volviendo al menú...");
				return;
			}
			System.out.println("Has derrotado a " + miembro.getNombre() + "!");	
		}
		System.out.println();
		System.out.println("Felicidades " + nombreJugador + "!! Te has coronado como campeón Pokémon!");
	}
	
	private static boolean combatirContraAltoMando(AltoMando miembro) {
		
		Pokemon pokemonJugador = obtenerPrimerPokemonVivo();
		
		if (pokemonJugador == null) {
			System.out.println("No tienes Pokémon vivos.");
			return false;
		}
		
		ArrayList<Pokemon> pokemonsRival = miembro.getPokemonsAltoMando();
		
		for (Pokemon pokemonRival : pokemonsRival) {
			
			System.out.println(miembro.getNombre() + " saca a " + pokemonRival.getNombre() + "!");
			System.out.println(nombreJugador + " saca a " + pokemonJugador.getNombre() + "!");
			
			while (pokemonRival.getEstado().equalsIgnoreCase("Vivo")) {
				
				if (!tienePokemonVivoEnEquipo()) {
					System.out.println("Te has quedado sin pokemons en tu equipo");
					return false;
				}
				if (!pokemonJugador.getEstado().equalsIgnoreCase("Vivo")) {
					pokemonJugador = obtenerPrimerPokemonVivo();
					
					if (pokemonJugador == null) {
						System.out.println("Te has quedado sin pokemons en tu equipo!");
						return false;
					}
					System.out.println(nombreJugador + " saca a " + pokemonJugador.getNombre() + "!");
				}
				
				 System.out.println("Que deseas hacer?");
		         System.out.println("1) Atacar");
		         System.out.println("2) Cambiar de pokemon");
		         System.out.println("3) Rendirse");
		         System.out.print("> ");
		         
		         String respuesta = s.nextLine();  
		           
		         switch (respuesta) {
		         
		         case "1":
		        	 boolean ganoCombate = combatirPokemon(pokemonJugador, pokemonRival);
		        	 
		        	 if (!ganoCombate && !tienePokemonVivoEnEquipo()) {
		        		 System.out.println("Te has quedado sin pokemons en tu equipo!");
		        		 return false;
		        	 }
		        	 break;
		        	 
		         case "2":
		        	 Pokemon nuevo = elegirPokemonVivo();
		        	 
		        	 if (nuevo != null) {
		        		 pokemonJugador = nuevo;
		        		 System.out.println(nombreJugador + " cambia a " + pokemonJugador.getNombre() + "!");
		        	 }
		        	 
		        	 break;
		        	 
		         case "3":
		        	 System.out.println("Te has rendido.");
		        	 return false;
		        	 
		        default: 
		        	System.out.println("Opción inválida.");
		        	break;
		         }    
			}	
		}
		return true;
	}
	
	private static void reiniciarAltoMando() {
		
		for (AltoMando miembro : listaAltoMando) {
			for (Pokemon p : miembro.getPokemonsAltoMando()) {
				p.setEstado("Vivo");
			}
		}
		
	}
	
	private static void retarGimnasio() {
		
		if (pokemonsJugador.size() == 0) {
			System.out.println("No tienes Pokémon para combatir");
			return;
		}
		
		if (!tienePokemonVivoEnEquipo()) {
			System.out.println("No tienes Pokémon vivos en tu equipo.");
			return;
		}
		
		System.out.println("A cual Lider deseas retar??");
		System.out.println();
		
		for (int i = 0; i < listaGimnasios.size(); i++) {
			Gimnasio g = listaGimnasios.get(i);
			System.out.println((i + 1) + ") " + g.getLider() + " - Estado: " + g.getEstado());
		}
		
		System.out.println((listaGimnasios.size() + 1) + ") Volver al menu.");
		System.out.println("> ");
		
		try {
			
			int respuesta = Integer.parseInt(s.nextLine());
			
			if (respuesta == listaGimnasios.size() + 1) {
				System.out.println("Volviendo al menú...");
				return;
			}
			
			if (respuesta < 1 || respuesta > listaGimnasios.size()) {
				System.out.println("Opción inválida");
				return;
			}
			
			Gimnasio gimnasio = listaGimnasios.get(respuesta - 1);
			
			if (gimnasio.getEstado().equalsIgnoreCase("Derrotado")) {
				System.out.println("Este gimnasio ya fue derrotado.");
				return;
			}
			
			if (!puedeRetarGimnasio(gimnasio)) {
				System.out.println("Calmado Entrenador!!! No puedes retar a " 
				+ gimnasio.getLider() + " sin haber derrotado a los lideres anteriores!!");
				return;
			}
			
			System.out.println("Desafiando a " + gimnasio.getLider() + "!!");
			
			reiniciarGimnasio(gimnasio);
			boolean gano = combatirContraGimnasio(gimnasio);
			
			if (gano) {
				gimnasio.setEstado("Derrotado");
				ultimoLiderDerrotado = gimnasio.getLider();
				actualizarEstadosGimnasios();
				
				System.out.println("Has derrotado a " + gimnasio.getLider() + "!");
			} else {
				gimnasio.setEstado("Sin derrotar");
				System.out.println("Volviendo al menu...");
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Debe ingresar un número válido");
		}
		
	}
	
	private static boolean tienePokemonVivoEnEquipo() {
		
		for (int i = 0; i < pokemonsJugador.size() && i < 6; i++) {
			Pokemon p = pokemonsJugador.get(i);
		
			if (p.getEstado().equalsIgnoreCase("Vivo")) {
				return true;
			}
			
		}
		
		
		return false;
	}
	
	private static Pokemon obtenerPrimerPokemonVivo() {
		
		for (int i = 0; i < pokemonsJugador.size() && i < 6; i++) {
			Pokemon p = pokemonsJugador.get(i);
			
			if (p.getEstado().equalsIgnoreCase("Vivo")) {
				return p;
			}
		}
		
		return null;
	}
	
	private static Pokemon elegirPokemonVivo() {
		
		System.out.println("Elige un Pokémon vivo de tu equipo: ");
		
		for (int i = 0; i < pokemonsJugador.size() && i < 6; i++) {
			Pokemon p = pokemonsJugador.get(i);
			System.out.println((i + 1) + ")" + p.getNombre() + " - " + p.getEstado());
		}
		
		System.out.println("> ");
		
		try {
			int respuesta = Integer.parseInt(s.nextLine());
			
			if (respuesta < 1 || respuesta > pokemonsJugador.size() || respuesta > 6) {
				System.out.println("Opción inválida.");
				return null;
			}
			
			Pokemon elegido = pokemonsJugador.get(respuesta - 1);
			
			if (!elegido.getEstado().equalsIgnoreCase("Vivo")) {
				System.out.println("Ese Pokémon está debilitado.");
				return null;
			}
			
			return elegido;
			
			
		} catch (NumberFormatException e) {
			System.out.println("Debe ingresar un número válido.");
			return null;
		}
		
	}
	
	private static boolean combatirPokemon(Pokemon pokemonJugador, Pokemon pokemonRival) {
		
		int statsJugador = pokemonJugador.getStatsTotales();
		int statsRival = pokemonRival.getStatsTotales();
		
		System.out.println(pokemonJugador.getNombre() + " -> " + statsJugador + " puntos");
		System.out.println(pokemonRival.getNombre() + " -> " + statsRival + " puntos");
		
		double efectividad = TablaTipos.multiplicador(pokemonJugador.getTipo(), pokemonRival.getTipo());
		
		if (efectividad == 2.0) {
			System.out.println(pokemonJugador.getNombre() + " es efectivo contra " + pokemonRival.getNombre() + "!");
		}
		else if (efectividad == 0.5) {
			System.out.println(pokemonJugador.getNombre() + " no es efectivo contra " + pokemonRival.getNombre() + "!");
		} else if (efectividad == 0.0){
			System.out.println(pokemonJugador.getNombre() + " no afecta a " + pokemonRival.getNombre() + "!");
		}
		
		statsJugador = (int) (statsJugador * efectividad);
		
		System.out.println("Nuevo puntaje");
		System.out.println(pokemonJugador.getNombre() + " -> " + statsJugador + " puntos");
		System.out.println(pokemonRival.getNombre() + " -> " + statsRival + " puntos");
		
		if (statsJugador > statsRival) {
			pokemonRival.setEstado("Debilitado");
			System.out.println("Ha ganado " + pokemonJugador.getNombre() + "!");
			return true;
		} else {
			pokemonJugador.setEstado("Debilitado");
			System.out.println("Ha ganado " + pokemonRival.getNombre() + "! " 
			+ pokemonJugador.getNombre() + " ha sido derrotado..." );
			return false;
		}
	}
	
	private static int obtenerUltimoGimnasioDerrotado() {
		
		if (ultimoLiderDerrotado.equalsIgnoreCase("none")) {
			return 0;
		}
		
		for (Gimnasio g : listaGimnasios) {
			if (g.getLider().equalsIgnoreCase(ultimoLiderDerrotado)) {
				return g.getNumero();
			}
		}
		
		return 0;
	}
	
	private static boolean puedeRetarGimnasio(Gimnasio gimnasio) {
		
		int numeroGimnasio = gimnasio.getNumero();
		
		if (numeroGimnasio == 1) {
			return true;
		}
		
		int ultimoDerrotado = obtenerUltimoGimnasioDerrotado();
		
		return numeroGimnasio == ultimoDerrotado + 1;
		
	}
	
	private static boolean combatirContraGimnasio(Gimnasio gimnasio) {
		
		Pokemon pokemonJugador = obtenerPrimerPokemonVivo();
		
		if (pokemonJugador == null) {
			System.out.println("No tienes Pokémon vivos.");
			return false;
		}
		
		ArrayList<Pokemon> pokemonsRival = gimnasio.getPokemonGimnasio();
		
		for (Pokemon pokemonrival : pokemonsRival) {
			
			System.out.println(gimnasio.getLider() + " saca a " + pokemonrival.getNombre() + "!");
			System.out.println(nombreJugador + " saca a " + pokemonJugador.getNombre() + "!");
			
			while (pokemonrival.getEstado().equalsIgnoreCase("Vivo")) {
				
				if (!tienePokemonVivoEnEquipo()) {
					System.out.println("Te has quedado sin pokemons en tu equipo!");
					return false;
				} 
				
				if (!pokemonJugador.getEstado().equalsIgnoreCase("Vivo")) {
					pokemonJugador = obtenerPrimerPokemonVivo();
					
					if (pokemonJugador == null) {
						System.out.println("Te has quedado sin pokemons en tu equipo!");
						return false;
					}
					System.out.println(nombreJugador + " saca a " + pokemonJugador.getNombre() + "!");
				}
				
				System.out.println("Que deseas hacer?");
		        System.out.println("1) Atacar");
		        System.out.println("2) Cambiar de pokemon");
		        System.out.println("3) Rendirse");
		        System.out.print("> ");
		        
		        String respuesta = s.nextLine();
		        
		        switch (respuesta) {
		        
		        case "1":
		        	boolean ganoCombate = combatirPokemon(pokemonJugador, pokemonrival);
		        	
		        	if (!ganoCombate && !tienePokemonVivoEnEquipo()) {
		        		System.out.println("Te has quedado sin pokemons en tu equipo!");
		        		return false;
		        	}
		        	break;
		        
		        case "2":
		        	Pokemon nuevo = elegirPokemonVivo();
		        	
		        	if (nuevo != null) {
		        		pokemonJugador = nuevo;
		        		System.out.println(nombreJugador + " cambia a " + pokemonJugador.getNombre() + "!");
		        	}
		        	break;
		        	
		        case "3":
		        	System.out.println("Te has rendido.");
		        	return false;
		        	
		        default:
		        	System.out.println("Opción inválida");
		        	break;
		        }
				
			}
		}
		return true;
	}
	
	private static void accesoPC() {
		
		if (pokemonsJugador.size() == 0) {
			System.out.println("No tienes Pokémon capturados.");
			return;
		}
		
		System.out.println("Pokémon capturados:");
		
		for (int i = 0; i < pokemonsJugador.size(); i++) {
			
			Pokemon p = pokemonsJugador.get(i);
			System.out.println((i + 1) + ")" + p.getNombre() + " - " + p.getEstado());
		}
		
		System.out.println();
		System.out.println("1) Cambiar Pokémon.");
		System.out.println("2) Salir.");
		System.out.println("> ");
		
		String respuesta = s.nextLine();
		
		switch (respuesta) {
		
		case "1":
			cambiarPokemon();
			break;
			
		case "2":
			System.out.println("Volviendo al menú...");
			break;
			
		default:
			System.out.println("Opción inválida");
			break;
		}
	}

	private static void cambiarPokemon() {
		
		try {
			
			System.out.println("Ingrese el número del primer Pokémon: ");
			int posicion1 = Integer.parseInt(s.nextLine());
			
			System.out.println("Ingrese el número del segundo Pokémon: ");
			int posicion2 = Integer.parseInt(s.nextLine());
			
			if (posicion1 < 1 || posicion1 > pokemonsJugador.size() || posicion2 < 1 || posicion2 > pokemonsJugador.size() ) {
				System.out.println("Posición inválida");
				return;
			}
			Pokemon aux = pokemonsJugador.get(posicion1 - 1);
			pokemonsJugador.set(posicion1 - 1, pokemonsJugador.get(posicion2 - 1));
			pokemonsJugador.set(posicion2 - 1, aux);
			
			System.out.println("Pokémon intercambiados correctamente.");
		} catch (NumberFormatException e) {
			System.out.println("Debe ingresar números válidos");
		}
		
		
	}

	private static void salirACapturar() throws IOException {
		
		System.out.println("Donde deseas ir a explorar?");
		System.out.println();
		System.out.println("Zonas disponibles:");
		
		for (int i = 0; i < listaHabitats.size(); i++) {
			System.out.println((i + 1) + ")" + listaHabitats.get(i));
		}
		
		System.out.println((listaHabitats.size() + 1) + ") Volver al menu." );
		System.out.println("Ingrese zona: ");
		
		try {
			int opcionZona = Integer.parseInt(s.nextLine());
		
		if (opcionZona == listaHabitats.size() + 1) {
			return;
		}
		if (opcionZona < 1 || opcionZona > listaHabitats.size() + 1) {
			System.out.println("Zona no válida");
			return;
		}
		String habitatElegido = listaHabitats.get(opcionZona - 1);
		
		ArrayList<Pokemon> pokemonsZona = obtenerPokemonHabitat(habitatElegido);
		
		if (pokemonsZona.size() == 0) {
			System.out.println("No hay Pokémon disponibles en esta zona");
			return;
		}
		
		Pokemon pokemonBase = generarPokemonAleatorio(pokemonsZona);
		
		System.out.println("Oh!! Ha aparecido un increíble " + pokemonBase.getNombre() + "!!");
		System.out.println();
		System.out.println("Que deseas hacer?\r\n"
				+ "\r\n"
				+ "1) Capturar\r\n"
				+ "2) Huir\r\n" 
				+ "> ");
		String respuesta = s.nextLine();
		
		switch (respuesta) {
		
		case "1":
			if (jugadorTienePokemon(pokemonBase.getNombre())) {
				System.out.println("Ya tienes a " + pokemonBase.getNombre() + " No puedes capturarlo de nuevo.");
			} else {
				Pokemon capturado = copiarPokemon(pokemonBase);
				pokemonsJugador.add(capturado);
				
				System.out.println(pokemonBase.getNombre() + " capturado con éxito!!");
				
				if (pokemonsJugador.size() <= 6) {
					System.out.println(pokemonBase.getNombre() + " ha sido agregado a tu equipo!");
				} else {
					System.out.println(pokemonBase.getNombre() + " ha sido enviado al PC!");
				}
			}
			break;
			
		case "2":
			System.out.println("Has huido.");
			break;
			
		default:
			System.out.println("Opción inválida");
			break;
		}
		
		} catch (NumberFormatException e) {
			System.out.println("Debe ingresar un número válido");
		}
		
		
		
	}

	private static void cargarRegistros() throws FileNotFoundException {
		
		pokemonsJugador.clear();
		File f = new File("Registros.txt");
		Scanner sc = new Scanner(f);
		
		
		if (sc.hasNextLine()) {
			String primeraLinea = sc.nextLine();
			String[] datosJugador = primeraLinea.split(";");
			
			nombreJugador = datosJugador[0];
			ultimoLiderDerrotado = datosJugador[1];
			actualizarEstadosGimnasios();
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			String[] partes = linea.split(";");
			
			String nombrePokemon = partes[0];
			String estado = partes[1];
			
			Pokemon pokemonBase = buscarPokemonPorNombre(nombrePokemon);
			if (pokemonBase != null) {
				Pokemon copia = copiarPokemon(pokemonBase);
				copia.setEstado(estado);
				pokemonsJugador.add(copia);
				}
			}
		System.out.println("Bienvenido " + nombreJugador + "!!");
		}
		
		sc.close();
	}

	private static boolean existePartidaGuardada() throws FileNotFoundException {
		
		File f = new File("Registros.txt");
		Scanner sc = new Scanner(f);
		
		if (sc.hasNextLine()) {
			sc.close();
			return true;
		}
		
		sc.close();
		return false;
	}

	private static void cargarArchivos() throws FileNotFoundException {
		
		cargarPokedex();
		cargarHabitats();
		cargarGimnasios();
		cargarAltoMando();
		
	}

	private static void cargarAltoMando() throws FileNotFoundException {
		
		File f = new File("Alto Mando.txt");
		Scanner sc = new Scanner(f);
		
		while (sc.hasNextLine()) {
			
			String linea = sc.nextLine();
			String[] partes = linea.split(";");
			
			int numAltoMando = Integer.parseInt(partes[0]);
			String nombre = partes[1];
			AltoMando altoMando = new AltoMando(numAltoMando, nombre);
			listaAltoMando.add(altoMando);
			for (int i = 0; i < 6; i++) {
				String nombrePokemon = partes[2 + i];
				Pokemon pokemonAltoMando = buscarPokemonPorNombre(nombrePokemon);
				if (pokemonAltoMando != null) {
					altoMando.addPokemon(copiarPokemon(pokemonAltoMando));
				}
			}
			
		}
		
	}

	private static void cargarGimnasios() throws FileNotFoundException {

		File f = new File("Gimnasios.txt");
		Scanner sc = new Scanner(f);
		
		while (sc.hasNextLine()) {
			
			String linea = sc.nextLine();
			String[] partes = linea.split(";");
			
			int numGimnasio = Integer.parseInt(partes[0]);
			String liderGimnasio = partes[1];
			String estado = partes[2];
			Gimnasio gimnasio = new Gimnasio(numGimnasio, liderGimnasio, estado);
			listaGimnasios.add(gimnasio);

			int cantPokemons = Integer.parseInt(partes[3]);
			for (int i = 0; i < cantPokemons; i++) {
				String nombrePokemon = partes[4 + i];
				Pokemon pokemonGimnasio = buscarPokemonPorNombre(nombrePokemon);
				if (pokemonGimnasio != null) {
					gimnasio.addPokemon(copiarPokemon(pokemonGimnasio));
				}
				
			}
			
		}
		
	}

	private static void cargarPokedex() throws FileNotFoundException {
		
		File f = new File("Pokedex.txt");
		Scanner sc = new Scanner(f);
		
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			String[] partes = linea.split(";");
			
			String nombrePokemon = partes[0];
			String habitat = partes[1];
			double porcentajeAparicion = Double.parseDouble(partes[2]);
			int vida = Integer.parseInt(partes[3]);
			int ataque = Integer.parseInt(partes[4]);
			int defensa = Integer.parseInt(partes[5]);
			int ataqueEspecial = Integer.parseInt(partes[6]);
			int defensaEspecial = Integer.parseInt(partes[7]);
			int velocidad = Integer.parseInt(partes[8]);
			String tipo = partes[9];
			Pokemon pokemon = new Pokemon(nombrePokemon, habitat, porcentajeAparicion, vida, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, tipo);
			listaPokedex.add(pokemon);
		}
		
	}

	private static void cargarHabitats() throws FileNotFoundException {
		
		File f = new File("Habitats.txt");
		Scanner sc = new Scanner(f);
		
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			
			
			String nombreHabitat = linea;
			listaHabitats.add(nombreHabitat);
		}
		
	}

	private static Pokemon buscarPokemonPorNombre(String nombre) {
		
		for (Pokemon p : listaPokedex) {
			if (p.getNombre().equalsIgnoreCase(nombre)) {
				return p;
			}
		}
		
		return null;
	}
	
	private static Pokemon copiarPokemon(Pokemon p) {
		return new Pokemon(p.getNombre(), p.getHabitat(), p.getPorcentajeAparicion(), p.getVida(), p.getAtaque(), p.getDefensa(), 
				p.getAtaqueEspecial(),p.getDefensaEspecial(),p.getVelocidad(),p.getTipo());
	}
	private static void curarPokemon() throws IOException{
		for (Pokemon p : pokemonsJugador) {
			p.setEstado("Vivo");
		}
		System.out.println("Tu equipo se ha recuperado!");
	}
	private static void guardarRegistros() throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt"));
		
		bw.write(nombreJugador + ";" + ultimoLiderDerrotado);
		bw.newLine();
		
		for (Pokemon p : pokemonsJugador) {
			bw.write(p.getNombre() + ";" + p.getEstado());
			bw.newLine();
		}
		bw.close();
	}
	
	private static void revisarEquipo() {
		
		System.out.println("Equipo actual:");
		
		if (pokemonsJugador.size() == 0) {
			System.out.println("No tienes pokemons en tu equipo");
			return;
		}
		
		for (int i = 0; i < pokemonsJugador.size() && i < 6; i++) {
			Pokemon p = pokemonsJugador.get(i);
			
			System.out.println((i + 1) + ")" + p.getNombre()
            + "|" + p.getTipo()
            + "|Stats totales: " + p.getStatsTotales()
            + "|Estado: " + p.getEstado());
		}
	}
	
	private static ArrayList<Pokemon> obtenerPokemonHabitat(String habitat){
		
		ArrayList<Pokemon> encontrados = new ArrayList<Pokemon>();
		
		for (Pokemon p : listaPokedex) {
			if (p.getHabitat().equalsIgnoreCase(habitat)) {
				encontrados.add(p);
			}
		}
		
		return encontrados;
	}
	private static Pokemon generarPokemonAleatorio(ArrayList<Pokemon> pokemonsZona) {
		
		Random r = new Random();
		double numero = r.nextDouble();
		double acumulado = 0;
		
		for (Pokemon p : pokemonsZona) {
			acumulado += p.getPorcentajeAparicion();
			
			if (numero <= acumulado) {
				return p;
			}
		}
		
		return pokemonsZona.get(pokemonsZona.size() - 1);
	}
	
	private static boolean jugadorTienePokemon(String nombrePokemon) {
		
		for (Pokemon p : pokemonsJugador) {
			if (p.getNombre().equalsIgnoreCase(nombrePokemon)) {
				return true;
			}
		}
		
		return false;
	}
	private static void actualizarEstadosGimnasios() {
		int ultimo = obtenerUltimoGimnasioDerrotado();
		
		for (Gimnasio g: listaGimnasios) {
			if (g.getNumero() <= ultimo) {
				g.setEstado("Derrotado");
			} else {
				g.setEstado("Sin derrotar");
			}
		}
	}
	
	private static void reiniciarGimnasio(Gimnasio gimnasio) {
		for (Pokemon p : gimnasio.getPokemonGimnasio()) {
			p.setEstado("Vivo");
		}
	}
	
}
