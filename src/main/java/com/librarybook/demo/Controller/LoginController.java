package com.librarybook.demo.Controller;

import com.librarybook.demo.entity.User;
import com.librarybook.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/register")
    public String Registor(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return"Register";
    }
    @PostMapping("/save")
    public String SaveRegistor(Model model, @ModelAttribute("user") User user) {
        User checkUser = userRepo.findByName(user.getName());
        if(checkUser==null) {
            user.setPassword(user.getPassword());
            userRepo.save(user);
        }
        else {
            String mess="Tài khoản đã tồn tại";
            model.addAttribute("mess",mess);
            return"Register";
        }
        return"redirect:/login/dangnhap";
    }

    @GetMapping("/dangnhap")
    public String initLogin(Model user){
        return "Login";
    }

    @PostMapping("/dang-nhap")
    public String Login(Model model, @RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) {
        User user = userRepo.findUserByNameAndPassword(userName,password);
        if(user == null){
            String mess="Tài khoản hoặc mật khẩu không chính xác";
            model.addAttribute("mess",mess);
            return"Login";
        }
        session.setAttribute("userName", user.getName());
        return"redirect:/book/index";
    }
}
