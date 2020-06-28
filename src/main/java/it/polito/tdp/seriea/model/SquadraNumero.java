package it.polito.tdp.seriea.model;

public class SquadraNumero implements Comparable<SquadraNumero>{
	
	private String squadra;
	private Integer numero;

	public SquadraNumero(String squadra, Integer numero) {
		this.squadra = squadra;
		this.numero = numero;
	}

	public String getSquadra() {
		return squadra;
	}

	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((squadra == null) ? 0 : squadra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SquadraNumero other = (SquadraNumero) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (squadra == null) {
			if (other.squadra != null)
				return false;
		} else if (!squadra.equals(other.squadra))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return squadra + ", " + numero;
	}

	@Override
	public int compareTo(SquadraNumero o) {
		return - (this.numero-o.getNumero());
	}
}
