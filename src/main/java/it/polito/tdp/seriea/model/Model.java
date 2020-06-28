package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private Graph<String, DefaultWeightedEdge> graph;
	private Simulator sim;
	
	public Model() {
		this.dao = new SerieADAO();
		this.sim = new Simulator();
	}
	
	public void generateGraph() {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.dao.listTeams());
		
		List<Adiacenza> adiacenze = this.getAdiacenze();
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.graph, a.getS1(), a.getS2(), a.getPeso());
		}
	}
	
	public List<Adiacenza> getAdiacenze() {
		List<Adiacenza> ad = this.dao.getAdiacenze();
		List<Adiacenza> adiacenze = this.dao.getAdiacenze();
		
		for(Adiacenza a : ad) {
			for(Adiacenza a1 : ad) {
				if(a.getS1().equals(a1.getS2()) && a.getS2().equals(a1.getS1())) {
					if(adiacenze.contains(a1)) {
						adiacenze.get(adiacenze.indexOf(a1)).setPeso(a1.getPeso()+a.getPeso());
						adiacenze.remove(adiacenze.indexOf(a));
					}
				}
			}
		}
		return adiacenze;
	}
	
	public Integer getNumVertici() {
		return this.graph.vertexSet().size();
	}
	
	public Integer getNumArchi() {
		return this.graph.edgeSet().size();
	}
	
	public List<String> getSquadre() {
		return this.dao.listTeams();
	}
	
	public List<SquadraNumero> getSquadreVicine(String squadra) {
		List<SquadraNumero> squadreVicine = new ArrayList<>();
		
		for(String s : Graphs.neighborListOf(this.graph, squadra)) {
			SquadraNumero sn = new SquadraNumero(s, (int) this.graph.getEdgeWeight(this.graph.getEdge(squadra, s)));
			squadreVicine.add(sn);
		}
		Collections.sort(squadreVicine);
		return squadreVicine;
	}
	
	public List<Season> getAllSeason() {
		return this.dao.listSeasons();
	}
	
	public void simula(Season stagione) {
		this.sim.init(this, stagione);
		this.sim.run();
	}
	
	public List<Match> getAllMatches(Season stagione) {
		return this.dao.getPartitePerStagione(stagione);
	}
	
	public List<String> getTeamsStagione(Season stagione) {
		return this.dao.getTeamStagione(stagione);
	}
	
	public List<SquadraNumero> getTifosi() {
		return this.sim.getTifosi();
	}
	
	public List<SquadraNumero> getPunti() {
		return this.sim.getPunti();
	}

}
