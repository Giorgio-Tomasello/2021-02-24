package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Coppia;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
				result.add(team);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID "
				+ "ORDER BY m.MatchID ASC";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public List<Player> listPlayersMatch(Integer i){
		String sql = "SELECT DISTINCT p.* "
				+ "	FROM Players p, Actions a "
				+ "	WHERE p.PlayerID = a.PlayerID "
				+ "	AND a.MatchID = ? ";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, i);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Coppia> getArchi(Integer i){
		String sql = "SELECT p1.*, p2.*, (((a.TotalSuccessfulPassesAll+a.Assists)/a.TimePlayed) - ((a2.TotalSuccessfulPassesAll+a2.Assists)/a2.TimePlayed)) as delta "
				+ "FROM Players p1, Players p2, Actions a, Actions a2 "
				+ "WHERE a.PlayerID = p1.PlayerID AND a2.PlayerID = p2.PlayerID "
				+ "AND a.TeamID <> a2.TeamID "
				+ "AND a.MatchID = ? AND a2.MatchID = ? "
				+ "AND p1.PlayerID > p2.PlayerID";
		List<Coppia> result = new ArrayList<Coppia>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, i);
			st.setInt(2, i);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player1 = new Player(res.getInt("p1.PlayerID"), res.getString("p1.Name"));
				Player player2 = new Player(res.getInt("p2.PlayerID"), res.getString("p2.Name"));
				if(res.getDouble("delta")>0)
					{Coppia c = new Coppia (player1, player2, res.getDouble("delta"));
						result.add(c);
					}
				else {
					Coppia c = new Coppia (player2, player1, res.getDouble("delta"));
					result.add(c);
				}
				
				
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public String topPlayer(Integer i){
		String sql = "SELECT p1.*, SUM(((a.TotalSuccessfulPassesAll+a.Assists)/a.TimePlayed) - ((a2.TotalSuccessfulPassesAll+a2.Assists)/a2.TimePlayed)) as delta, a.TeamID "
				+ "FROM Players p1, Players p2, Actions a, Actions a2 "
				+ "WHERE p1.PlayerID = a.PlayerID  "
				+ "AND p2.PlayerID = a2.PlayerID "
				+ "AND a.MatchID = ? "
				+ "AND a2.MatchID = a.MatchID "
				+ "AND a.TeamID <> a2.TeamID "
				+ "GROUP BY p1.Name, p1.PlayerID "
				+ "ORDER BY delta DESC";
		;
		Connection conn = DBConnect.getConnection();
		String output="";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, i);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				output= res.getInt("PlayerID") + " - " + res.getString("Name") + " delta efficienza: " + res.getDouble("delta");
				
				
				conn.close();
				return output;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public int topPlayerTeam(Integer i){
		String sql = "SELECT p1.*, SUM(((a.TotalSuccessfulPassesAll+a.Assists)/a.TimePlayed) - ((a2.TotalSuccessfulPassesAll+a2.Assists)/a2.TimePlayed)) as delta, a.TeamID "
				+ "FROM Players p1, Players p2, Actions a, Actions a2 "
				+ "WHERE p1.PlayerID = a.PlayerID  "
				+ "AND p2.PlayerID = a2.PlayerID "
				+ "AND a.MatchID = ? "
				+ "AND a2.MatchID = a.MatchID "
				+ "AND a.TeamID <> a2.TeamID "
				+ "GROUP BY p1.Name, p1.PlayerID "
				+ "ORDER BY delta DESC";
		;
		Connection conn = DBConnect.getConnection();
		String output="";
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, i);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				

				conn.close();
				return res.getInt("a.TeamID");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	
	
	public List<Team> listTeamsMatch(int match){
		String sql = "SELECT t1.*, t2.* "
				+ "FROM Teams t1, Teams t2, Matches m "
				+ "WHERE t1.TeamID = m.TeamHomeID "
				+ "AND t2.TeamID = m.TeamAwayID "
				+ "AND m.MatchID = ? ";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, match);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team team1 = new Team(res.getInt("t1.TeamID"), res.getString("t1.Name"));
				Team team2 = new Team(res.getInt("t2.TeamID"), res.getString("t2.Name"));
				result.add(team1);
				result.add(team2);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
