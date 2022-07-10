package it.polito.tdp.PremierLeague.model;

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
	private List<PlayerEff> listaGiocatori;
	private Graph<PlayerEff, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		
		dao = new PremierLeagueDAO();
	}


	public List<Match> getListaPartite() {
		
		listaPartite = new LinkedList<Match>(dao.listAllMatches());
		
		
		return listaPartite;
	}


	public String creaGrafo(Match m) {
		
		
		listaGiocatori = new LinkedList<PlayerEff>(dao.listPlayersMatch(m.getMatchID()));
		
		this.grafo = new SimpleDirectedWeightedGraph<PlayerEff,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, listaGiocatori);
		
		double eff=0.0;
		for(PlayerEff p : listaGiocatori)
			{for(PlayerEff p2 : listaGiocatori)
				{if(!p.equals(p2) && p.getT()!=p2.getT())
					{if(p.getEfficienza()>p2.getEfficienza())
						{eff = (p.getEfficienza()-p2.getEfficienza());
							Graphs.addEdge(this.grafo, p, p2, eff);}}
					}
					}

		
		String output = "GRAFO CREATO" + "\n" + "Numero vertici: " + this.grafo.vertexSet().size() + 
				"\nNumero Archi: " + this.grafo.edgeSet().size();

		return output;
		
	}
	
	public String topPlayer(Match m) {
		
		PlayerEff p = dao.topPlayer(m.getMatchID());
		
		return p.getPlayerID() + " - " + p.getName() + ", delta efficienza: " + p.getEfficienza();
	}
		
		
		
	
	
	
	
	
	
	
}
