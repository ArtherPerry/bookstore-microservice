package com.example.bookstoreui.service;

import com.example.bookstoreui.ds.Book;
import com.example.bookstoreui.ds.Books;
import com.example.bookstoreui.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {

    private RestTemplate template = new RestTemplate();

    @Value("${api.url}")
    private String backendUrl;

    private List<Book> books = new LinkedList<>();

    @PostConstruct
    public void postConstruct(){
        showAllBooks();
    }

    public List<Book> findAllBooks(){
        return books;
    }

    public Book findBookById(int id){
        return books.stream().filter(b -> b.getId() == id).findFirst().orElseThrow( () ->new  NotFoundException(id + "not Found"));
    }


    public void showAllBooks(){
        this.books = template.getForEntity(backendUrl+"api/books", Books.class).getBody().getBooks();

    }


}
