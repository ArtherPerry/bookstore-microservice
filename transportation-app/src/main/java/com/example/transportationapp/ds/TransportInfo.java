package com.example.transportationapp.ds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class TransportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String orderId;

    @OneToMany(mappedBy = "transportInfo")
    private List<Book> books =new ArrayList<>();
    private double total;


    public TransportInfo() {
    }

    public void  addBook(Book book){
        book.setTransportInfo(this);
        books.add(book);
    }

    public TransportInfo(String name, String orderId, double total) {
        this.name = name;
        this.orderId = orderId;
        this.total = total;
    }
}
