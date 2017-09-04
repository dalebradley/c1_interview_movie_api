package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by dbradley on 03/09/2017.
 */
public class Comment {
    @JsonProperty("user")
    private String user;
    @JsonProperty("message")
    private String message;
    @JsonProperty("dateCreated")
    private String dateCreated;
    @JsonProperty("like")
    private long like;

    public Comment(String user, String message, String dateCreated, long like) {
        this.user = user;
        this.message = message;
        this.dateCreated = dateCreated;
        this.like = like;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getDateCreated() {
        Date date = new Date(Long.parseLong(this.dateCreated) * 1000L);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        return formatted;
    }

    public long getLike() {
        return like;
    }
}
