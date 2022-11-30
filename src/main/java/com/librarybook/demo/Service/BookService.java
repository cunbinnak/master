package com.librarybook.demo.Service;

import com.librarybook.demo.entity.Book;
import com.librarybook.demo.repository.BookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Service
@Slf4j
public class BookService {
    @Autowired
    BookRepo bookRepo;
    public List<Book> getAll(){
        try{
            return bookRepo.findAll();
        }catch (Exception e){
            log.error("no content");
            throw new RuntimeException();
        }
    }

}
