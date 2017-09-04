package hello;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by dbradley on 31/08/2017.
 */


public class Movie {
    @JsonProperty("movie_id")
    private final long movie_id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("producer")
    private String producer;
    @JsonProperty("available_in_3d")
    private Boolean available_in_3d;
    @JsonProperty("age_rating")
    private String age_rating;
    @JsonProperty("likes")
    private long likes;
    @JsonProperty("comments")
    private List<Comment> comments;

    public Movie(long movie_id, String title, String description, String producer,
                 Boolean available_in_3d, String age_rating, long likes, List<Comment> comments
                 ) {
        this.movie_id = movie_id;
        this.title = title;
        this.description = description;
        this.producer = producer;
        this.available_in_3d = available_in_3d;
        this.age_rating = age_rating;
        this.likes = likes;
        this.comments = comments;
    }
    public long getMovie_id() {
        return movie_id;
    }

    public long getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

}
