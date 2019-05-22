package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

public class Tools {

	public static List<String> getParoleSimili(List<String> parole, String parola, int numeroLettere) {

		List<String> paroleSimili = new ArrayList<String>();
		
		for(String p : parole) {
			if(oneDistance(parola, p)) {
				paroleSimili.add(p);
			}
		}
		return paroleSimili;
	}

	private static boolean oneDistance(String parola1, String parola2) {
		if(parola1.length() != parola2.length())
			throw new RuntimeException("Le due parole non hanno la stessa lunghezza!");
		
		int distanza = 1;
		for(int i=0; i<parola1.length(); i++) {
			if(parola1.charAt(i) != parola2.charAt(i))
				distanza --;
		}
		
		if(distanza == 0)
			return true;
		else
			return false;
	}

}
