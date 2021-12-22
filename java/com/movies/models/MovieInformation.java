package com.movies.models;

public class MovieInformation {

	private String overview;
	private String original_title;
	private float vote_average;
	private float vote_count;
	private String poster_path;
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getOriginal_title() {
		return original_title;
	}
	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
	public float getVote_average() {
		return vote_average;
	}
	public void setVote_average(float vote_average) {
		this.vote_average = vote_average;
	}
	public float getVote_count() {
		return vote_count;
	}
	public void setVote_count(float vote_count) {
		this.vote_count = vote_count;
	}
	public String getPoster_path() {
		return poster_path;
	}
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	

	

}
