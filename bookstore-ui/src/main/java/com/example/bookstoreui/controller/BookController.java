package com.example.bookstoreui.controller;

import com.example.bookstoreui.ds.AccountInfo;
import com.example.bookstoreui.ds.Book;
import com.example.bookstoreui.ds.Cart;
import com.example.bookstoreui.ds.TransportInfo;
import com.example.bookstoreui.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;
import java.util.Set;

@Controller
@RequestMapping("/bookstore")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private Cart cart;

    @Value("${payment.url}")
    private String paymentUrl;

    @Value("${transport.url}")
    private String transportUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @ResponseBody
    @GetMapping("/test")
    public String test() {
        bookService.findAllBooks().forEach(System.out::println);
        return "success";
    }

    @GetMapping("/book/{id}")
    public String detailBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "book-detail";
    }


    @GetMapping({"/", "/home", "/index"})
    public String index(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("success",model.containsAttribute("success"));
        return "home";
    }

    @ModelAttribute("cartSize")
    public int cartSize() {
        return cart.cartSize();
    }

    @GetMapping("/book/cart/{id}")
    public String addToCart(@PathVariable("id") int id) {
        cart.addToCart(bookService.findBookById(id));

        return "redirect:/bookstore/book/" + id;
    }

    @GetMapping("/book/cart/view")
    public String viewCart(Model model, @ModelAttribute Book book) {
        model.addAttribute("books", cart.getBookSet());


        return "cart-view";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBookFromCart(@PathVariable("id") int id) {
        cart.removeBookFromCart(bookService.findBookById(id));
        return "redirect:/bookstore/book/cart/view";
    }

    @GetMapping("/book/cart/clear")
    public String clearCart() {
        cart.clearCart();
        return "redirect:/bookstore/book/cart/view";

    }

    @PostMapping("/book/checkout")
    public String handleCheckOut(@ModelAttribute Book book) {
        int i = 0;
        Set<Book> bookSet = cart.getBookSet();
        for (Book book1 : bookSet) {
            book1.setItemCount(book.getQuantity().get(i));
            i++;
        }
        cart.setBookSet(bookSet);
       /* ResponseEntity response = restTemplate.postForEntity(paymentUrl+ "payment/checkout",String.valueOf(getTotalPrice()),String.class);

        System.out.println(response.getStatusCode());*/
        return "redirect:/bookstore/account-info";
    }

    @GetMapping("/account-info")
    public String paymentInfoForm(Model model) {
        model.addAttribute("accountInfo", new AccountInfo());
        return "accountInfoForm";
    }


    @PostMapping("/account-info")
    private String processAccountInfo(AccountInfo accountInfo, BindingResult bindingResult, @ModelAttribute("books") Set<Book> books, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // return "accountInfoForm";
        }
        AccountInfo account = new AccountInfo();
        account.setAccountNumber(accountInfo.getAccountNumber());
        account.setName(accountInfo.getName());
        account.setTotalAmount(getTotalPrice());
        ResponseEntity response = restTemplate.postForEntity(paymentUrl + "payment/checkout", account, String.class);
        if (response.getStatusCode().equals(HttpStatus.CREATED)) {
            TransportInfo transportInfo = new TransportInfo(account.getName(), generateOrderId(), books, getTotalPrice());
            ResponseEntity transportResponse = restTemplate.postForEntity(transportUrl + "transport/create", transportInfo, String.class);
            if(transportResponse.getStatusCode().equals(HttpStatus.CREATED)){
                redirectAttributes.addFlashAttribute("success",true);
            }
        }
        else {
            throw new IllegalArgumentException("CheckOut Error!");
        }
        //System.out.println("=====" + response.getStatusCode());
        return "redirect:/bookstore/home";
    }

    //record TransportInfo(String name,String orderId,Set<Book> books){}

    private String generateOrderId() {
        return "AMZ000" + (new Random().nextInt(10000) + 10000);
    }

    private double getTotalPrice() {
        double totalPrice = cart.getBookSet().stream()
                .map(b -> b.getItemCount() * b.getPrices())
                .mapToDouble(d -> d).sum();
        return totalPrice;
    }

    @ModelAttribute("books")
    public Set<Book> getAllBooksFromCart() {
        return cart.getBookSet();
    }


}
