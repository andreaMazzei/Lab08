package it.polito.tdp.dizionariograph.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {
	
	/*
	 * Ritorna TUTTE le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		List<String> parole = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				parole.add(res.getString("nome"));
			}

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}
	/*
	 *  Ritorna le parole che differiscono per una sola lettera ad un'altra
	 */
	public List<String> getParoleSimili(String parola, int numeroLettere) {
		
		String sql = "SELECT nome\n" + 
					"FROM parola\n" + 
					"WHERE nome LIKE ?\n" + 
					"AND LENGTH(nome) = ? ;";
		List<String> paroleSimili = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			char parolaOriginale[] = parola.toCharArray();
			
			for(int i = 0; i < parola.length(); i++) {
				// Tolgo il carattere i-esimo e ci metto '_' e salvo in una nuova stringa
				// poi ci rimetto il carattere tolto per le ricerche successive
				char temp = parolaOriginale[i];
				
				parolaOriginale[i] = '_';
				String parolaDaCercare = String.copyValueOf(parolaOriginale);
				
				parolaOriginale[i] = temp;
				System.out.println(parolaDaCercare);
				

				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, parolaDaCercare);
				st.setInt(2, numeroLettere);
				ResultSet res = st.executeQuery();

				while (res.next()) {
					// paroleSimili.add(res.getString("nome"));
					String nextWord = res.getString("nome");

					if (parola.compareToIgnoreCase(nextWord) != 0)

						paroleSimili.add(nextWord);
				}
				
			}
			
			return paroleSimili;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error Connection Database");
		}
	}

}
