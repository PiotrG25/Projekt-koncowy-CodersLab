package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.beans.BCrypt;
import pl.coderslab.dtos.UserLoginDto;
import pl.coderslab.entity.User;
import pl.coderslab.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    @RequestMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String getLogin(Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("userLoginDto", new UserLoginDto());
            return "login";
        }else{
            return "redirect:/main";
        }
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute @Valid UserLoginDto userLoginDto, BindingResult result, Model model){
        if(session.getAttribute("user") != null){
            return "redirect:/main";
        }

        String name = userLoginDto.getName();
        String password = userLoginDto.getPassword();

        if(name == null || name.isEmpty() || password == null || password.isEmpty()){
            model.addAttribute("emptyField", true);
            return "login";
        }

        if(result.hasErrors()){
            model.addAttribute("wrongData", true);
            return "login";
        }

        User user = userService.findUserByName(name);

        if(user == null || !BCrypt.checkpw(password, user.getPassword())){
            model.addAttribute("wrongData", true);
        }else{
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(60 * 15);
            model.addAttribute("success", true);
        }
        return "login";
    }
}
