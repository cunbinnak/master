package com.librarybook.demo.Controller;

import com.librarybook.demo.Service.BookService;
import com.librarybook.demo.entity.Book;
import com.librarybook.demo.entity.Category;
import com.librarybook.demo.repository.BookRepo;
import com.librarybook.demo.repository.CateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    BookService bookService;

    @Autowired
    CateRepo cateRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/image";

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
        List<Book> books=bookService.getAll();
        model.addAttribute("listBook",books);
        model.addAttribute("userName", session.getAttribute("userName"));
        System.out.println(session.getAttribute("userName"));
        return "index";
    }
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book, @RequestParam ("fileData") MultipartFile fileData) throws IOException {

        if (!fileData.getOriginalFilename().isEmpty()){
            Path currentPath = Paths.get(".");
            Path absolutePath = currentPath.toAbsolutePath();
            book.setPath(absolutePath +"/src/main/resources/static/images/");
            byte [] bytes = fileData.getBytes();
            Path fileNameAndPath = Paths.get(book.getPath() +fileData.getOriginalFilename());
            Files.write(fileNameAndPath, fileData.getBytes());

            book.setImage(fileData.getOriginalFilename());
        }

        bookRepo.save(book);
        return "redirect:/book/index";
    }
    @RequestMapping("/initaddbook")
    public String addBook(Model model){
        Book book = new Book();
        List<Category> listCate = cateRepo.findAll();
        model.addAttribute("book",book);
        model.addAttribute("listCate", listCate);
        return "AddBook";
    }

    @GetMapping("/initeditBook/{id}")
    public ModelAndView initEditBook(@PathVariable("id") int id, Model editbook){
        ModelAndView mv = new ModelAndView("EditBook");
        Book book= bookRepo.findById(id);
        mv.addObject("editbook",book);
        List<Category> listCate = cateRepo.findAll();
        editbook.addAttribute("listCate", listCate);
        return mv;
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("editbook") Book editbook, @RequestParam ("fileData") MultipartFile fileData) throws IOException {
        System.out.println(fileData.getOriginalFilename());
        if (!fileData.getOriginalFilename().isEmpty()){
            Path currentPath = Paths.get(".");
            Path absolutePath = currentPath.toAbsolutePath();
            editbook.setPath(absolutePath +"/src/main/resources/static/images/");
            byte [] bytes = fileData.getBytes();
            Path fileNameAndPath = Paths.get(editbook.getPath() +fileData.getOriginalFilename());
            Files.write(fileNameAndPath, fileData.getBytes());

            editbook.setImage(fileData.getOriginalFilename());
        }else{
            Book oldBook = bookRepo.findById(editbook.getId());

            String oldImage = oldBook.getImage();
            editbook.setImage(oldImage);
        }

        bookRepo.save(editbook);
        return "redirect:/book/index";
    }

    @GetMapping("/viewBook/{id}")
    public ModelAndView viewBook(@PathVariable("id") int id, Model viewBook){
        ModelAndView mv = new ModelAndView("viewBook");
        Book book= bookRepo.findById(id);
        mv.addObject("viewbook",book);
        List<Category> listCate = cateRepo.findAll();
        viewBook.addAttribute("listCate", listCate);
        return mv;
    }

    @GetMapping("/delete/{id}")
    public String deleteByidUser(@PathVariable("id") int id){
        bookRepo.deleteById(id);
        return "redirect:/book/index";
    }
}
