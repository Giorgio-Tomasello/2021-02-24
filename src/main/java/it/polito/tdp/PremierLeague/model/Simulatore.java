package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Evento.EventType;

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
	private int goalAway;
	private int espulsiHome;
	private int espulsiAway;
	
	//stato del mondo
	private int nAzioni;
	
	public void init(int nAzioni, List<Team> teams) {
		
				this.nAzioni = nAzioni;
				this.home = teams.get(0);
				this.away = teams.get(1);
				
				home.setnGiocatori(11);
				away.setnGiocatori(11);
				
				home.setnGoal(0);
				away.setnGoal(0);
				
				this.queue = new PriorityQueue<Evento>();
				
				for(int i=0; i<nAzioni ; i++)
					{
					Evento e = new Evento(home,away);
					double probabilita = Math.random();
					
					if(probabilita<=0.5) {
		
						e.setTipo(EventType.GOAL);
						queue.add(e);
		
					}
					
					if(probabilita>0.5 && probabilita<=0.8) {
		
						e.setTipo(EventType.ESPULSIONE);
						queue.add(e);
		
					}
					
					if(probabilita>0.8) {
		
						e.setTipo(EventType.INFORTUNIO);
						queue.add(e);
						
						double p = Math.random();
						if(p<=0.5)
							{this.nAzioni = nAzioni + 2;}
						else {
							this.nAzioni = nAzioni + 3;
						}
						
		
					}
					
					
					
					}
				
		
		
		
	}
	
	
	
	
	public void run(int Best) {
		
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll(); //tolgo l'evento dalla coda
			
			processEvent(e, Best);
		}
		}

	private void processEvent(Evento e, int best) {
		
		switch(e.getTipo()) {
		
		case GOAL:
					if(giocatoriHome == giocatoriAway) {
						if(home.getTeamID() == best)
							{
							goalHome++;
							home.setnGoal(goalHome);}
						else {
							goalAway++;
							away.setnGoal(goalAway);
						}
						
					}
					
					else if(giocatoriHome > giocatoriAway)
						{goalHome ++;
						home.setnGoal(goalHome);}
							
							else {goalAway++;
							away.setnGoal(goalAway);}
			
			break;
			
		case ESPULSIONE:
					double prob = Math.random();
					
					if(prob<=0.6) {
						
						if(home.getTeamID() == best)
						{espulsiHome++;
						home.setEspulsi();
						}
					else {
						goalAway++;
						away.setEspulsi();
					}}
						else {
							if(away.getTeamID() == best)
							{espulsiAway++;
							away.setEspulsi();
							}
						else {
							goalHome++;
							home.setEspulsi();
						}
						}
						
					
			break;
			
		case INFORTUNIO:
			
			
			break;
		
		}
		
	}




	public int getGiocatoriHome() {
		return giocatoriHome;
	}

	public void setGiocatoriHome(int giocatoriHome) {
		this.giocatoriHome = giocatoriHome;
	}

	public int getGiocatoriAway() {
		return giocatoriAway;
	}

	public void setGiocatoriAway(int giocatoriAway) {
		this.giocatoriAway = giocatoriAway;
	}

	public int getGoalHome() {
		return goalHome;
	}

	public void setGoalHome(int goalHome) {
		this.goalHome = goalHome;
	}

	public int getGolaAway() {
		return goalAway;
	}

	public void setGolaAway(int golaAway) {
		this.goalAway = golaAway;
	}


	public int getEspulsiAway() {
		return espulsiAway;
	}

	public void setEspulsiAway(int espulsiAway) {
		this.espulsiAway = espulsiAway;
	}




	public PriorityQueue<Evento> getQueue() {
		return queue;
	}




	public void setQueue(PriorityQueue<Evento> queue) {
		this.queue = queue;
	}




	public Team getHome() {
		return home;
	}




	public void setHome(Team home) {
		this.home = home;
	}




	public Team getAway() {
		return away;
	}




	public void setAway(Team away) {
		this.away = away;
	}




	public int getGoalAway() {
		return goalAway;
	}




	public void setGoalAway(int goalAway) {
		this.goalAway = goalAway;
	}




	public int getEspulsiHome() {
		return espulsiHome;
	}




	public void setEspulsiHome(int espulsiHome) {
		this.espulsiHome = espulsiHome;
	}




	public int getnAzioni() {
		return nAzioni;
	}




	public void setnAzioni(int nAzioni) {
		this.nAzioni = nAzioni;
	}




	public List<Team> getSquadre() {
		return squadre;
	}
	
	

}
