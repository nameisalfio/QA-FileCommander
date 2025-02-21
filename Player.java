package com.example.HelloWord;

public class Player {

	private int id;
	private String nome;
	private String cognome;
	private String squadra;
	
	public Player() {}

	public Player(int id, String nome, String cognome, String squadra) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.squadra = squadra;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSquadra() {
		return squadra;
	}

	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}
	
}
