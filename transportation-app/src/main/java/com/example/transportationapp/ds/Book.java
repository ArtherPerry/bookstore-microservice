package com.example.transportationapp.ds;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter


public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String authorName;
    private double prices;
    private String publisher;


    private LocalDate yearPublished;
    private String genre;
    private String imageURL;

    private Integer itemCount;

    @ManyToOne
    private TransportInfo transportInfo;

    public Book() {
    }

    public Book(String title, String authorName, double prices, String publisher, LocalDate yearPublished, String genre, String imageURL) {
        this.title = title;
        this.authorName = authorName;
        this.prices = prices;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genre = genre;
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
