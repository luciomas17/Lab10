package it.polito.tdp.porto.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();

		model.createGraph();
		
		List<Author> neighbors = model.getNeighbors(new Author(85, "Belforte", "Gustavo"));
		System.out.println(neighbors);
	}

}
