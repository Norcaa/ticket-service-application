package com.epam.training.ticketservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.ParseException;
import java.util.Objects;

@Component
@Entity
@Getter
@Setter
public class Projection {

    @Id
    @GeneratedValue
    private Long id;

    private String movieTitle;
    private String roomName;
    private String startDate;

    /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    String dateString = format.format(new Date());
    Date startDate = format.parse( "2021-12-05 18:00" );*/

    public Projection() {

    }

    public Projection(String movieTitle, String roomName, String startDate) throws ParseException {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.startDate = startDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projection)) {
            return false;
        }
        final Projection proj = (Projection) o;
        boolean titleResult = Objects.equals(movieTitle, proj.movieTitle);
        return titleResult && Objects.equals(roomName, proj.roomName) && proj.startDate.equals(startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieTitle, roomName, startDate);
    }

    @Override
    public String toString() {
        return "ProjectionImpl{" + "movie=" + movieTitle + ", room=" + roomName + ", date=" + startDate + '}';
    }
}
