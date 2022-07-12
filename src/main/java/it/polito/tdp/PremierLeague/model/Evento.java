package it.polito.tdp.PremierLeague.model;

public class Evento implements Comparable<Evento>{

	public enum EventType{
		
		GOAL,
		ESPULSIONE,
		INFORTUNIO
		
	}
	
	private Team squadraA;
	private Team squadraB;
	double probabilita;
	
	private EventType tipo;
	
	
	
	public Evento(Team squadraA, Team squadraB) {
		super();
		this.squadraA = squadraA;
		this.squadraB = squadraB;
	}



	public Team getSquadraA() {
		return squadraA;
	}



	public void setSquadraA(Team squadraA) {
		this.squadraA = squadraA;
	}



	public Team getSquadraB() {
		return squadraB;
	}



	public void setSquadraB(Team squadraB) {
		this.squadraB = squadraB;
	}



	public double getProbabilita() {
		return probabilita;
	}



	public void setProbabilita(double probabilita) {
		this.probabilita = probabilita;
	}



	public EventType getTipo() {
		return tipo;
	}



	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}



	@Override
	public int compareTo(Evento o) {
		
		if(this.probabilita > o.probabilita)
			return 1;
		if(this.probabilita < o.probabilita)
			return -1;
		return 0;
		
	}
	
	

}
