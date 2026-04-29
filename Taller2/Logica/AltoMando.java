package logica;

import java.util.ArrayList;

public class AltoMando {
	
	private int numero;
	private String nombre;
	private ArrayList<Pokemon> pokemonsAltoMando;
	public AltoMando(int numero, String nombre) {
		super();
		this.numero = numero;
		this.nombre = nombre;
		this.pokemonsAltoMando = new ArrayList<Pokemon>();
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Pokemon> getPokemonsAltoMando() {
		return pokemonsAltoMando;
	}
	public void setPokemonsAltoMando(ArrayList<Pokemon> pokemonsAltoMando) {
		this.pokemonsAltoMando = pokemonsAltoMando;
	}
	
	public void addPokemon(Pokemon p) {
		pokemonsAltoMando.add(p);
	}
	
		
	
	
}
