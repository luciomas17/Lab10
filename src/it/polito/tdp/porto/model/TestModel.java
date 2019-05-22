package it.polito.tdp.porto.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();

		model.createGraph();
		System.out.println("");
		
		Author a1 = model.getAuthorIdMap().get(18415);
		List<Author> neighbors = model.getNeighbors(a1);
		System.out.println("Co-autori di " + a1 + ":");
		for(Author a : neighbors)
			System.out.println(a);
		System.out.println("");
		
		Author a2 = model.getAuthorIdMap().get(2904);
		/*
		List<Author> shortestPath = model.findShortestPath(a1, a2);
		if(shortestPath == null)
			System.out.println("Sequenza tra " + a1 + " e " + a2 + "non trovata.");
		else {
			System.out.println("Cammino minimo tra " + a1 + " e " + a2 + ":");
			for(Author a : shortestPath)
				System.out.println(a);
		}
		System.out.println("");
		*/
		
		List<PaperAndAuthors> sequence = model.findSequence(a1, a2);
		if(sequence == null)
			System.out.println("Sequenza tra " + a1 + " e " + a2 + " non trovata.");
		else {
			System.out.println("Sequenza tra " + a1 + " e " + a2 + ":");
			for(PaperAndAuthors paa : sequence)
				System.out.println(paa);
		}
		
	}

}
