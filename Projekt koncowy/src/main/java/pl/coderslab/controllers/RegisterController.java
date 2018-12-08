package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.dtos.UserRegisterDto;
import pl.coderslab.entity.User;
import pl.coderslab.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    @GetMapping("/register")
    public String getRegister(Model model){
        if(session.getAttribute("user") == null){
            model.addAttribute("userRegisterDto", new UserRegisterDto());
            return "register";
        }else{
            return "redirect:/main";
        }
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute @Valid UserRegisterDto userRegisterDto, BindingResult result, Model model){
        if(session.getAttribute("user") != null){
            return "redirect:/main";
        }

        String name = userRegisterDto.getName();
        String password = userRegisterDto.getPassword();
        String confirmPassword = userRegisterDto.getConfirmPassword();
        String email = userRegisterDto.getEmail();

        if(name == null || name.isEmpty() || password == null || password.isEmpty() || confirmPassword == null ||
                confirmPassword.isEmpty() || email == null || email.isEmpty()){
            model.addAttribute("emptyField", true);
            return "register";
        }

        if(result.hasErrors()){
            return "register";
        }

        if(!password.equals(confirmPassword)){
            model.addAttribute("differentPassword", true);
        }else{

            User user = userRegisterDto.getUser();
            user.hashPassword();
            String effect = userService.saveToDb(user);

            if(effect.equals("name")){
                model.addAttribute("nameNotUnique", true);
            }else if(effect.equals("email")){
                model.addAttribute("emailNotUnique", true);
            }else{//if(effect.equals("ok"))

                model.addAttribute("success", true);
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(60 * 15);
            }
        }
        return "register";
    }
}
