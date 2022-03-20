package com.movies.models;

import java.util.List;

public class Trailer {
	private String id;
	private List<MovieDetails> results;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<MovieDetails> getResults() {
		return results;
	}
	public void setResults(List<MovieDetails> results) {
		this.results = results;
	}
	@Override
	public String toString() {
		return "Trailer [id=" + id + ", results=" + results + "]";
	}
	
}
