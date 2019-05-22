package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

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

	public List<Author> getAuthorsWithoutNeighbors(Author author) {
		List<Author> authors = new ArrayList<>(authorIdMap.values());
		authors.remove(author);
		List<Author> neighbors = Graphs.neighborListOf(this.graph, author);
		List<Author> authorsWithoutNeighbors = new ArrayList<>();
		
		for(Author a : authors) {
			if(!neighbors.contains(a))
				authorsWithoutNeighbors.add(a);
		}
		Collections.sort(authorsWithoutNeighbors);
		
		return authorsWithoutNeighbors;
	}
	
	public List<PaperAndAuthors> findSequence(Author a1, Author a2) {
		if(!testConnection(a1, a2))
			return null;
		
		List<PaperAndAuthors> sequence = new ArrayList<>();
		
		List<Author> shortestPath = findShortestPath(a1, a2);
		for(int i = 0; i < shortestPath.size() - 1; i++) {
			Paper paper = dao.getPaperFromAuthors(shortestPath.get(i), shortestPath.get(i+1));
			PaperAndAuthors paa = new PaperAndAuthors(paper, shortestPath.get(i), shortestPath.get(i+1));
			sequence.add(paa);
		}
		
		return sequence;
	}
	
	public List<Author> findShortestPath(Author a1, Author a2) {
		DijkstraShortestPath<Author, DefaultEdge> dijkstra = new DijkstraShortestPath<>(this.graph);
		GraphPath<Author, DefaultEdge> path = dijkstra.getPath(a1, a2);
		
		return path.getVertexList();
	}
	
	public Boolean testConnection(Author a1, Author a2) {
		Set<Author> visited = new HashSet<>();
		
		BreadthFirstIterator<Author, DefaultEdge> it = new BreadthFirstIterator<>(this.graph, a1);
		while(it.hasNext())
			visited.add(it.next());
		
		if(visited.contains(a2))
			return true;
		else
			return false;
	}
	
	public Map<Integer, Author> getAuthorIdMap() {
		return this.authorIdMap;
	}
}
