package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.Adiacenza;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<String> listTeams() {
		String sql = "SELECT team FROM teams";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("team"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getAdiacenze() {
		String sql = "SELECT m.HomeTeam AS ht, m.AwayTeam AS at, COUNT(m.match_id) AS peso " + 
				"FROM matches AS m " + 
				"GROUP BY m.HomeTeam, m.AwayTeam";
		
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Adiacenza(res.getString("ht"), res.getString("at"), res.getInt("peso")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> getPartitePerStagione(Season stagione) {
		String sql = "SELECT m.match_id, m.Season, m.Date, m.HomeTeam, m.AwayTeam, m.FTHG, m.FTAG, m.FTR " + 
				"FROM matches AS m " + 
				"WHERE m.Season = ?";
		
		List<Match> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, stagione.getSeason());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Match(res.getInt("match_id"), stagione, res.getTimestamp("Date").toLocalDateTime().toLocalDate(), res.getString("HomeTeam"), res.getString("AwayTeam"), res.getInt("FTHG"), res.getInt("FTAG"), res.getString("FTR")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getTeamStagione(Season stagione) {
		String sql = "SELECT DISTINCT (m.HomeTeam) " + 
				"FROM matches AS m " + 
				"WHERE m.Season = ?";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, stagione.getSeason());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("HomeTeam"));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

