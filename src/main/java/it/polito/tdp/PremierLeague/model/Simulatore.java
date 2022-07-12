package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulatore {
	
	List <Team> squadre = new ArrayList<>();
	public void setSquadre(List <Team> squadre) {
		this.squadre = squadre;
	}
	
	//coda degli eventi
	private PriorityQueue<Evento> queue;
	
	
	//dati in input
	private Team home;
	private Team away;
	private int giocatoriHome;
	private int giocatoriAway;
	
	
	//dati in output
	private int goalHome;
	private int golaAway;
	private int esplusiHome;
	private int espulsiAway;
	
	//stato del mondo
	private int nAzioni;
	
	public void init(int nAzioni) {
		
		this.nAzioni = nAzioni;
		
		home = squadre.get(0);
		away = squadre.get(1);
		
		home.setnGiocatori(11);
		away.setnGiocatori(11);
		
		
		
		
	}

}
