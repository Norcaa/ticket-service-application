package com.epam.training.ticketservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Component
@Entity
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String genre;
    private Integer length;

    public Movie() {

    }

    public Movie(String title, String genre, Integer length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    /*public Movie(final SimpleMovieBuilder simpleMovieBuilder) {
        this.title = simpleMovieBuilder.title;
        this.genre = simpleMovieBuilder.genre;
        this.length = simpleMovieBuilder.length;
    }*/

    /*public static SimpleMovieBuilder builder() {
        return new SimpleMovieBuilder();
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie that = (Movie) o;
        return that.length.equals(length) && Objects.equals(that.genre, genre) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, length);
    }

    @Override
    public String toString() {
        return "Movie{" + "title='" + title + '\'' + ", genre=" + genre + ", length=" + length + '}';
    }

    /*public static final class SimpleMovieBuilder {
        private String title;
        private String genre;
        private Integer length;

        private SimpleMovieBuilder() {
        }

        public SimpleMovieBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public SimpleMovieBuilder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public SimpleMovieBuilder withLength(Integer length) {
            this.length = length;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }*/
}
