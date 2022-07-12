package it.polito.tdp.PremierLeague.model;

import java.util.Objects;

public class Coppia {
	
	Player p1;
	Player p2;
	double delta;
	public Coppia(Player p1, Player p2, double delta) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.delta = delta;
	}
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public double getDelta() {
		return delta;
	}
	public void setDelta(double delta) {
		this.delta = delta;
	}
	@Override
	public int hashCode() {
		return Objects.hash(delta, p1, p2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coppia other = (Coppia) obj;
		return Double.doubleToLongBits(delta) == Double.doubleToLongBits(other.delta) && Objects.equals(p1, other.p1)
				&& Objects.equals(p2, other.p2);
	}
	
	

}
