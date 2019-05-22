package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private Graph<String, DefaultEdge> grafo;
	private WordDAO dao = new WordDAO();
	private int numeroLettere;

	public List<String> createGraph(int numeroLettere) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		// mi salvo numeroLettere fuori dal metodo perchè mi serve per altri metodi del model
		this.numeroLettere = numeroLettere;
		
		// Lista di parole da aggiungere al grafo (parole di lunghezza scelta)
		List<String> parole = dao.getAllWordsFixedLength(numeroLettere);
		
		// Aggiungo i vertici al grafo
		for(String parola : parole)
			grafo.addVertex(parola);
		
		// Collego (EDGE) le parole che differiscono di una sola lettera
		for(String parola : parole) {
			
			// Alternativa 1 : uso il Database -- LENTO
			// List<String> paroleSimili = dao.getParoleSimili(parola, numeroLettere);
			
			// Alternativa 2 -- faccio un algoritmo java
			List<String> paroleSimili = Tools.getParoleSimili(parole, parola, numeroLettere);
			
			// FAI DOPO
			
			for(String parolaSimile : paroleSimili)
				grafo.addEdge(parola, parolaSimile);
		}
		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		if(numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita non è di "+numeroLettere+" caratteri");
		
		List<String> vicini = new ArrayList<String>();
		vicini.addAll(Graphs.neighborListOf(grafo, parolaInserita));
		return vicini;
	}

	public String findMaxDegree() {
		int gradoMax = 0;
    	String vertice = "";
    	StringBuilder sb = new StringBuilder();
    	
    	for(String v : grafo.vertexSet()) {
    		if(grafo.degreeOf(v) > gradoMax) {
    			vertice = v;
    			gradoMax = grafo.degreeOf(v);
    		}
    	}
    	
    	if(vertice == null)
    		throw new RuntimeException("Non trovato!");
    	else {
    		sb = sb.append(String.format("Grado massimo: %d del vertice: %s\nVicini:\n", gradoMax, vertice));
    		
    		for(String v : Graphs.neighborListOf(grafo, vertice)) {
    			sb.append("- "+v+"\n");
    		}
    		return sb.toString();
    	}
	}
}
