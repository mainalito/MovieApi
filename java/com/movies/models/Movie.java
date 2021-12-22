package com.movies.models;

import java.util.List;

public class Movie {
	private int page;
	private List<MovieInformation> results;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<MovieInformation> getResults() {
		return results;
	}

	public void setResults(List<MovieInformation> results) {
		this.results = results;
	}

}
