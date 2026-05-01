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
	private static int medallas = 0;
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		cargarArchivos();
		menuInicial();
		
		
	}

	private static void menuInicial() {
		String respuesta;
		
		do {
			System.out.println("1) Continuar.\r\n"
					+ "2) Nueva Partida.\r\n"
					+ "3) Salir.\r\n"
					+ "> ");
			respuesta = s.nextLine();
			
			
			
		} while (!respuesta.equalsIgnoreCase("3"));
		
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
	
}
