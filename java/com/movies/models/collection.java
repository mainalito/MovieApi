package com.movies.models;

public class collection {
    private String id;
    private String poster_path;
    

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * @param poster_path the poster_path to set
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", poster_path='" + getPoster_path() + "'" +
            "}";
    }


}
