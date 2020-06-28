package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class Simulator {
	
	private PriorityQueue<Match> queue;
	private List<Match> partite;
	private List<String> squadre;
	private List<SquadraNumero> tifosi;
	private List<SquadraNumero> punti;
	private Model model;
	
	public void init(Model model, Season stagione) {
		this.queue = new PriorityQueue<>();
		this.model = model;
		this.partite = this.model.getAllMatches(stagione);
		this.squadre = this.model.getTeamsStagione(stagione);
		this.tifosi = new ArrayList<>();
		this.punti = new ArrayList<>();
		
		for(String s : squadre) {
			SquadraNumero sn = new SquadraNumero(s, 1000);
			SquadraNumero sn1 = new SquadraNumero(s, 0);
			this.tifosi.add(sn);
			this.punti.add(sn1);
		}
		
		for(Match m : this.partite) {
			this.queue.add(m);
		}		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Match m = this.queue.poll();
			processEvent(m);
		}		
	}
	
	public void processEvent(Match m) {
		Random random = new Random();
		Integer p = 10;
		
		// caso in cui squadra A ha meno tifosi della squadra B
		Integer goalH = 0;
		Integer goalA = 0;
		if(this.getNumTifosi(m.getHomeTeam())<this.getNumTifosi(m.getAwayTeam())) { // home ha meno tifosi
			float prob = 1-((float) m.getFthg()/(float) m.getFtag());
			Integer r = random.nextInt(100)+1;
			if(r<=prob) {
				goalH = m.getFthg()-1;
			}
			else
				goalH = m.getFthg();
		}
		if(this.getNumTifosi(m.getAwayTeam())<this.getNumTifosi(m.getHomeTeam())) { // away ha meno tifosi
			float prob = 1-((float) m.getFtag()/(float) m.getFthg());
			Integer r = random.nextInt(100)+1;
			if(r<=prob) {
				goalA = m.getFtag()-1;
			}
			else
				goalA = m.getFtag();
		}
		
		// spostamento tifosi
		if(goalA>goalH) { // away vince
			Integer percentuale = (goalA-goalH)*p;
			Integer tifosiH = this.getNumTifosi(m.getHomeTeam()) - (this.getNumTifosi(m.getHomeTeam())*percentuale/100);
			Integer tifosiA = this.getNumTifosi(m.getAwayTeam()) + (this.getNumTifosi(m.getHomeTeam())*percentuale/100);
			
			this.setNumTifosi(tifosiH, m.getHomeTeam());
			this.setNumTifosi(tifosiA, m.getAwayTeam());
			
			// aumento i punti +3
			Integer punti = this.getPunti(m.getAwayTeam());
			this.setPunti(punti+3, m.getAwayTeam());
		}
		if(goalH>goalA) { // home vince
			Integer percentuale = (goalA-goalH)*p;
			Integer tifosiA = this.getNumTifosi(m.getAwayTeam()) - (this.getNumTifosi(m.getAwayTeam())*percentuale/100);
			Integer tifosiH = this.getNumTifosi(m.getHomeTeam()) + (this.getNumTifosi(m.getAwayTeam())*percentuale/100);
			
			this.setNumTifosi(tifosiA, m.getAwayTeam());
			this.setNumTifosi(tifosiH, m.getHomeTeam());
			
			// aumento i punti +3
			Integer punti = this.getPunti(m.getHomeTeam());
			this.setPunti(punti+3, m.getHomeTeam());
		}
		if(goalH==goalA) { // pareggio
			// entrambi +1
			Integer puntiH = this.getPunti(m.getHomeTeam());
			Integer puntiA = this.getPunti(m.getAwayTeam());
			this.setPunti(puntiH+1, m.getHomeTeam());
			this.setPunti(puntiA+1, m.getAwayTeam());
		}
	}
	
	public Integer getNumTifosi(String squadra) {
		for(SquadraNumero sn : this.tifosi) {
			if(sn.getSquadra().equals(squadra)) {
				return sn.getNumero();
			}
		}
		return null;
	}
	
	public void setNumTifosi(Integer tifosi, String squadra) {
		for(SquadraNumero sn : this.tifosi) {
			if(sn.getSquadra().equals(squadra)) {
				sn.setNumero(tifosi);
			}
		}
	}
	
	public Integer getPunti(String squadra) {
		for(SquadraNumero sn : this.punti) {
			if(sn.getSquadra().equals(squadra)) {
				return sn.getNumero();
			}
		}
		return null;
	}
	
	public void setPunti(Integer punti, String squadra) {
		for(SquadraNumero sn : this.punti) {
			if(sn.getSquadra().equals(squadra)) {
				sn.setNumero(punti);
			}
		}
	}
	
	public List<SquadraNumero> getTifosi() {
		return this.tifosi;
	}
	
	public List<SquadraNumero> getPunti() {
		return this.punti;
	}

}
