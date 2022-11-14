package com.example.bookstoreui.ds;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInfo {

    private String name;
    private  double totalAmount;
    private  String accountNumber;


    public AccountInfo() {
    }
}
