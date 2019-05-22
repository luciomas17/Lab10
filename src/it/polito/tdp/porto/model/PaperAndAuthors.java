package it.polito.tdp.porto.model;

public class PaperAndAuthors {
	
	public Paper paper;
	public Author author1;
	public Author author2;
	
	public PaperAndAuthors(Paper paper, Author author1, Author author2) {
		super();
		this.paper = paper;
		this.author1 = author1;
		this.author2 = author2;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Author getAuthor1() {
		return author1;
	}

	public void setAuthor1(Author author1) {
		this.author1 = author1;
	}

	public Author getAuthor2() {
		return author2;
	}

	public void setAuthor2(Author author2) {
		this.author2 = author2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author1 == null) ? 0 : author1.hashCode());
		result = prime * result + ((author2 == null) ? 0 : author2.hashCode());
		result = prime * result + ((paper == null) ? 0 : paper.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaperAndAuthors other = (PaperAndAuthors) obj;
		if (author1 == null) {
			if (other.author1 != null)
				return false;
		} else if (!author1.equals(other.author1))
			return false;
		if (author2 == null) {
			if (other.author2 != null)
				return false;
		} else if (!author2.equals(other.author2))
			return false;
		if (paper == null) {
			if (other.paper != null)
				return false;
		} else if (!paper.equals(other.paper))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Articolo: %s, Autori: %s, %s", paper, author1, author2);
	}
	
}
