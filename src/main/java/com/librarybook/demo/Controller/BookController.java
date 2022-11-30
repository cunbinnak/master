package com.librarybook.demo.Controller;

import com.librarybook.demo.Service.BookService;
import com.librarybook.demo.entity.Book;
import com.librarybook.demo.entity.Category;
import com.librarybook.demo.repository.BookRepo;
import com.librarybook.demo.repository.CateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    public String index(Model model) {
        List<Book> books=bookService.getAll();
        model.addAttribute("listBook",books);
        return "index";
    }
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("book") Book book, @RequestParam ("fileData") MultipartFile fileData) throws IOException {

        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        book.setPath(absolutePath +"/src/main/resources/image/");
        byte [] bytes = fileData.getBytes();
        Path fileNameAndPath = Paths.get(book.getPath() +fileData.getOriginalFilename());
        Files.write(fileNameAndPath, fileData.getBytes());

        book.setImage(fileData.getOriginalFilename());
        bookRepo.save(book);
        return "redirect:/book/index";
    }
    @RequestMapping("/initaddbook")
    public String addUser(Model model){
        Book book = new Book();
        List<Category> listCate = cateRepo.findAll();
        model.addAttribute("book",book);
        model.addAttribute("listCate", listCate);
        return "AddEditBook";
    }
    @GetMapping("/delete/{id}")
    public String deleteByidUser(@PathVariable("id") int id){
        bookRepo.deleteById(id);
        return "redirect:index";
    }
    @GetMapping("/editBook/{id}")
    public ModelAndView EditByidUser(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("AddEditBook");
        Book book= bookRepo.findById(id);
        mv.addObject("book",book);
        List<Category> listCate = cateRepo.findAll();
        return mv;
    }

    @GetMapping("/image") public String displayUploadForm() {
        return "AddEditBook";
    }
}
