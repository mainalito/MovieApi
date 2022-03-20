package com.movies.models;

public class OneMovie {

	private String homepage;
	private String id;
	private String original_title;
	private String overview;
	private String backdrop_path;
	private int runtime;
	private float vote_average;
	private String poster_path;
	private  collection belongs_to_collection ;
	

	public OneMovie() {
	}
	public String getPoster_path(){
		return this.poster_path;
	}
	public void setPoster_path(String poster_path){
		this.poster_path=poster_path;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginal_title() {
		return this.original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getBackdrop_path() {
		return this.backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public int getRuntime() {
		return this.runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public float getVote_average() {
		return this.vote_average;
	}

	public void setVote_average(float vote_average) {
		this.vote_average = vote_average;
	}

	public collection getBelongs_to_collection() {
		return this.belongs_to_collection;
	}

	public void setBelongs_to_collection(collection belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public OneMovie homepage(String homepage) {
		setHomepage(homepage);
		return this;
	}

	public OneMovie id(String id) {
		setId(id);
		return this;
	}

	public OneMovie original_title(String original_title) {
		setOriginal_title(original_title);
		return this;
	}

	public OneMovie overview(String overview) {
		setOverview(overview);
		return this;
	}

	public OneMovie backdrop_path(String backdrop_path) {
		setBackdrop_path(backdrop_path);
		return this;
	}

	public OneMovie runtime(int runtime) {
		setRuntime(runtime);
		return this;
	}

	public OneMovie vote_average(float vote_average) {
		setVote_average(vote_average);
		return this;
	}

	public OneMovie belongs_to_collection(collection belongs_to_collection) {
		setBelongs_to_collection(belongs_to_collection);
		return this;
	}



	@Override
	public String toString() {
		return "{" +
			" homepage='" + getHomepage() + "'" +
			", id='" + getId() + "'" +
			", original_title='" + getOriginal_title() + "'" +
			", overview='" + getOverview() + "'" +
			", backdrop_path='" + getBackdrop_path() + "'" +
			", runtime='" + getRuntime() + "'" +
			", vote_average='" + getVote_average() + "'" +
			", belongs_to_collection='" + getBelongs_to_collection() + "'" +
			"}";
	}

}
