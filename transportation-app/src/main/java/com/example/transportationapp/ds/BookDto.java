package com.example.transportationapp.ds;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookDto {

    private Integer id;

    private String title;
    private String authorName;
    private double prices;
    private String publisher;


    private LocalDate yearPublished;
    private String genre;
    private String imageURL;

    private Integer itemCount;

    public BookDto() {
    }

    public BookDto(String title, String authorName, double prices, String publisher, LocalDate yearPublished, String genre, String imageURL, Integer itemCount) {
        this.title = title;
        this.authorName = authorName;
        this.prices = prices;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.genre = genre;
        this.imageURL = imageURL;
        this.itemCount = itemCount;
    }
}
