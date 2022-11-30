package com.librarybook.demo.Controller;

import com.librarybook.demo.Service.BookService;
import com.librarybook.demo.entity.Book;
import com.librarybook.demo.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepo bookRepo;
    @Autowired
    BookService bookService;
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        List<Book> books=bookService.getAll();
        model.addAttribute("listProduct",books);
        return "index";
    }
}
