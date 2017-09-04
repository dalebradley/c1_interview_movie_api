package hello;

import org.json.simple.parser.JSONParser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by dbradley on 03/09/2017.
 */

@RestController
public class MovieController {
    List<Movie> movies;

    // This is a init method for the controller. This is where the json file is read and the data converted
    // from JSON to POJO
    @PostConstruct
    public void initData() throws IOException{
        this.movies = new ArrayList<Movie>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(
                    "movies.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray movieList = (JSONArray) jsonObject.get("movies");

            for (int i = 0; i < movieList.size(); i++) {
                JSONObject row = (JSONObject)movieList.get(i);
                String title = (String)row.get("title");
                long movie_id = (long)row.get("movie_id");
                String description = (String)row.get("description");
                String producer = (String)row.get("producer");
                String age_rating = (String)row.get("age_rating");
                long likes = (long)row.get("likes");
                Boolean available_in_3d = Boolean.valueOf((String)row.get("age_rating"));
                JSONArray comments = (JSONArray)row.get("comments");
                List<Comment> commentsList = new ArrayList<>();
                for (int j = 0; j < comments.size(); j++) {
                    JSONObject comment = (JSONObject)comments.get(j);
                    Comment com = new Comment((String)comment.get("user"), (String)comment.get("message"), (String)comment.get("dateCreated"),
                            (long)comment.get("like"));
                    commentsList.add(com);
                }
                Movie mov = new Movie(movie_id, title, description, producer, available_in_3d, age_rating, likes, commentsList);
                movies.add(mov);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/movies/{id}", method=GET)
    public Movie movies(@PathVariable("id") long id, Model model) {
        Movie foundMovie = null;
        for (Movie movie: movies) {
            if (movie.getMovie_id() == id) {
                foundMovie = movie;
            }
        }
        return foundMovie;
    }

    @RequestMapping(value="/users/most_comments", method=GET)
    public Map.Entry<String, Long> mostCommented() {
        Map<String, Long> userComments = new HashMap<>();
        for (Movie movie: movies) {
            List<Comment> comments = movie.getComments();
            for (Comment comment: comments){

                String user = comment.getUser();
                if(userComments.containsKey(user)){
                    long value = userComments.get(user);
                    userComments.put(user, value+1l);
                } else {
                    userComments.put(user, 1l);
                }
            }
        }
        // Find the highest value in the hashmap
        Map.Entry<String, Long> maxEntry = null;
        for (Map.Entry<String, Long> entry : userComments.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    @RequestMapping(value="/movies/most_liked", method=GET)
    public Movie mostLiked() {
        Map<Long, Long> movieLikes = new HashMap<>();
        for (Movie movie: movies) {
            movieLikes.put(movie.getMovie_id(), movie.getLikes());
            List<Comment> comments = movie.getComments();
            for (Comment comment: comments){
                long commentLikes = comment.getLike();
                movieLikes.put(movie.getMovie_id(), movieLikes.get(movie.getMovie_id()) + commentLikes);
            }
        }
        // Find the highest value in the hashmap and return corresponding movie object
        Map.Entry<Long, Long> maxEntry = null;
        for (Map.Entry<Long, Long> entry : movieLikes.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return movies.get(maxEntry.getKey().intValue());
    }
}
