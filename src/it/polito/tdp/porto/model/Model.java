package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author, DefaultEdge> graph;
	private PortoDAO dao;
	private Map<Integer, Author> authorIdMap;
	
	public Model() {
		this.dao = new PortoDAO();
		this.authorIdMap = dao.loadAllAuthors();
	}
	
	public void createGraph() {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.graph, this.authorIdMap.values());
		
		for(CoAuthor ca : dao.getCoAuthors(this.authorIdMap)) {
			Author a1 = ca.getAuthor1();
			Author a2 = ca.getAuthor2();
			this.graph.addEdge(a1, a2);
		}
		
		System.out.println(String.format("Grafo creato! %d vertici e %d archi.", this.graph.vertexSet().size(), this.graph.edgeSet().size()));
	}

	public List<Author> getNeighbors(Author a) {
		List<Author> neighbors = Graphs.neighborListOf(this.graph, a);
		Collections.sort(neighbors);
		
		if(neighbors.size() == 0)
			return null;
		else
			return neighbors;
	}

	public List<Author> getAuthors() {
		List<Author> authors = new ArrayList<>(authorIdMap.values());
		Collections.sort(authors);
		
		return authors;
	}
}
