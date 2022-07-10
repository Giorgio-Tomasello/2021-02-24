package it.polito.tdp.PremierLeague.model;

import java.util.Objects;

public class PlayerEff {
	
	Integer playerID;
	String name;
	Double efficienza;
	Integer t;
	public PlayerEff(Integer playerID, String name, Double efficienza, Integer t) {
		super();
		this.playerID = playerID;
		this.name = name;
		this.efficienza = efficienza;
		this.t = t;
	}
	public Integer getPlayerID() {
		return playerID;
	}
	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getEfficienza() {
		return efficienza;
	}
	public void setEfficienza(Double efficienza) {
		this.efficienza = efficienza;
	}
	@Override
	public int hashCode() {
		return Objects.hash(efficienza, name, playerID, t);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerEff other = (PlayerEff) obj;
		return Objects.equals(efficienza, other.efficienza) && Objects.equals(name, other.name)
				&& Objects.equals(playerID, other.playerID) && Objects.equals(t, other.t);
	}
	public Integer getT() {
		return t;
	}
	public void setT(Integer t) {
		this.t = t;
	}
	
	

}
