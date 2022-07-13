package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private List<Match> listaPartite;
	private List<Player> listaGiocatori;
	private Graph<Player, DefaultWeightedEdge> grafo;
	List<Team> listaSquadre;
	Team home;
	Team away;
	int goalHome;
	int goalAway;
	
	
	public Model() {
		
		dao = new PremierLeagueDAO();
	}


	public List<Match> getListaPartite() {
		
		listaPartite = new LinkedList<Match>(dao.listAllMatches());
		
		
		return listaPartite;
	}


	public String creaGrafo(Match m) {
		
		
		listaGiocatori = new LinkedList<Player>(dao.listPlayersMatch(m.getMatchID()));
		
		this.grafo = new SimpleDirectedWeightedGraph<Player,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, listaGiocatori);
		
		for(Coppia c : dao.getArchi(m.getMatchID()))
			Graphs.addEdge(this.grafo, c.getP1(), c.getP2(), c.getDelta());
			
					
					

		
		String output = "GRAFO CREATO" + "\n" + "Numero vertici: " + this.grafo.vertexSet().size() + 
				"\nNumero Archi: " + this.grafo.edgeSet().size();

		return output;
		
	}
	
	public String topPlayer(Match m) {
		
		return dao.topPlayer(m.getMatchID());
		
		
	}
	
	public String Simula(int n, Match m) {
		
		
		
		Simulatore sim = new Simulatore();
		listaSquadre = new ArrayList<>(dao.listTeamsMatch(m.getMatchID()));
		
		sim.init(n, listaSquadre);
		sim.run(dao.topPlayerTeam(m.getMatchID()));
		
		String output = "Il risultato finale della partita è: " + sim.getHome().getName()+ " " + sim.getGoalHome() + " --- " + 
						sim.getAway().getName() + " " + sim.getGoalAway() + "\n" + 
						"Espulsi --> " + sim.getHome().getName() + " " + sim.getEspulsiHome() + " " + 
						sim.getAway().getName() + " " + sim.getEspulsiAway();
		
		return output;
		
	}


	
		
		
	
	
	
	
	
	
	
}
