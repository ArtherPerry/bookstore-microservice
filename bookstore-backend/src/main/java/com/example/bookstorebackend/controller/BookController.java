package com.example.bookstorebackend.controller;

import com.example.bookstorebackend.dao.BookDao;
import com.example.bookstorebackend.ds.Book;
import com.example.bookstorebackend.ds.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookDao bookDao;

    @GetMapping("/creation")
    @Transactional
    public String  init(){
        Book book1 = new Book("A pale view of Hill","thomas", 32.5,"moron", LocalDate.of(1978,2,12), "novel","https://source.unsplash.com/random/?books");
        Book book2 = new Book("Green Wood tree","hardy", 30.5,"moron", LocalDate.of(1987,2,11), "novel","https://source.unsplash.com/random/?books");
        Book book3 = new Book("Over the Horizon","john", 32.5,"moron", LocalDate.of(1988,2,14), "novel","https://source.unsplash.com/random/?sea");
        Book book4 = new Book("one plus one = three","william", 32.5,"moron", LocalDate.of(1999,2,1), "novel","https://source.unsplash.com/random/?math");
        Book book5 = new Book("Sons and Lovers","richard", 32.5,"GreenBean", LocalDate.of(1989,2,22), "novel","https://source.unsplash.com/random/?girl");

        /*bookDao.save(book1);
        bookDao.save(book2);
        bookDao.save(book3);
        bookDao.save(book4);
        bookDao.save(book5);*/
        return "successfully created";
    }

    @GetMapping("/books")
    public Books listAllBooks(){
        return new Books(bookDao.findAll());
    }

    record BookDto(int id, String title,
                   String authorName, double prices,
                   String publisher, LocalDate yearPublished,
                   String genre, String imageURL){}

    @GetMapping("/books/{id}")
    public ResponseEntity findBookById(@PathVariable("id")int id){
        Optional<Book> bookOptional = bookDao
                .findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                            new BookDto(
                                    book.getId(),
                                    book.getTitle(),
                                    book.getAuthorName(),
                                    book.getPrices(),
                                    book.getPublisher(),
                                    book.getYearPublished(),
                                    book.getGenre(),
                                    book.getImageURL()
                            )
                    );
        }
        else {
           return ResponseEntity.notFound().build();
        }

    }
}
