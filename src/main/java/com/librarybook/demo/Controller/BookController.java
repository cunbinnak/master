package com.librarybook.demo.Controller;

import com.librarybook.demo.Service.BookService;
import com.librarybook.demo.entity.Book;
import com.librarybook.demo.repository.BookRepo;
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
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
        model.addAttribute("listBook",books);
        return "index";
    }
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("book") Book book, @RequestParam ("fileData") MultipartFile fileData, HttpServletRequest request){
        String path = request.getServletContext().getRealPath("resources/image");
        File fileName = new File(path);
        File dest = new File(fileName.getAbsoluteFile()+"/"+fileData.getOriginalFilename());
        if (!dest.exists()) {
            try {
                byte[] dataName = fileData.getBytes();
                Files.write(dest.toPath(), dataName, StandardOpenOption.CREATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        book.setImage(fileData.getOriginalFilename());
        bookRepo.save(book);
        return "redirect:/index";
    }
    @RequestMapping("/add")
    public String addUser(Model model){
        Book book = new Book();
        model.addAttribute("book",book);
        return "add_user";
    }
    @GetMapping("delete/{id}")
    public String deleteByidUser(@PathVariable("id") int id){
        bookRepo.deleteById(id);
        return "redirect:index";
    }
    @GetMapping("editBook/{id}")
    public ModelAndView EditByidUser(@PathVariable("id") int id){
        ModelAndView mv = new ModelAndView("book_edit");
        Book book= bookRepo.findByIdBook(id);
        mv.addObject("book",book);
        return mv;
    }

}
