package logica;

import java.util.ArrayList;

public class Gimnasio {
	
	private int numero;
	private String lider;
	private String estado;
	private ArrayList<Pokemon> pokemonGimnasio;
	public Gimnasio(int numero, String lider, String estado) {
		super();
		this.numero = numero;
		this.lider = lider;
		this.estado = estado;
		this.pokemonGimnasio = new ArrayList<Pokemon>();
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getLider() {
		return lider;
	}
	public void setLider(String lider) {
		this.lider = lider;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public ArrayList<Pokemon> getPokemonGimnasio() {
		return pokemonGimnasio;
	}
	public void setPokemonGimnasio(ArrayList<Pokemon> pokemonGimnasio) {
		this.pokemonGimnasio = pokemonGimnasio;
	}
	
	public void addPokemon(Pokemon p) {
		pokemonGimnasio.add(p);
	}
	
}
