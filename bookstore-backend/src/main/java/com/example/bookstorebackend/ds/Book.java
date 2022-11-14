package com.example.bookstorebackend.ds;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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
}
