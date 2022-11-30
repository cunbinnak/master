package com.librarybook.demo.Controller;

import com.librarybook.demo.entity.User;
import com.librarybook.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return"register";
    }
    @PostMapping("/save")
    public String SaveRegistor(Model model, @ModelAttribute("user") User user, @Param("password")String password) {
        User checkUser = userRepo.findByName(user.getName());
        if(checkUser==null) {
            user.setPassword(password);
            userRepo.save(user);
        }
        else {
            String mess="tai khoan da ton tai";
            model.addAttribute("mess",mess);
            return"redirect:/register";
        }
        return"redirect:/login";
    }
    @GetMapping("/login")
    public String Login(Model model, @ModelAttribute("userName") String userName, @Param("password")String password, HttpServletRequest req, HttpServletResponse resp) {
        User user = userRepo.findUserByNameAndPassword(userName,password);
        if(user == null){
            String mess="tai khoan mat khau khong chinh xac";
            model.addAttribute("mess",mess);
            return"redirect:/login";
        }
        HttpSession session =req.getSession();
        model.addAttribute("user", user);
        return"index";
    }
}
