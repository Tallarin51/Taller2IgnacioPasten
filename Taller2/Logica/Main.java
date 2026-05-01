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
				} else {
					System.out.println("No existe una partida guardada");
				}
				break;
			 
			case "2":
				nuevaPartida();
				menuPrincipal();
				break;
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
			
			
			
			}
			
		} while (!respuesta.equals("8"));
		
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
	
}
