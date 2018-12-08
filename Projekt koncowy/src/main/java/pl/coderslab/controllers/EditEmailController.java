package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.beans.BCrypt;
import pl.coderslab.dtos.UserEmailEditDto;
import pl.coderslab.entity.User;
import pl.coderslab.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class EditEmailController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    @GetMapping("/editEmail")
    public String getEditEmail(Model model){
        if(session.getAttribute("user") == null) {
            return "redirect:/main";
        }
        model.addAttribute("userEmailEditDto", new UserEmailEditDto());
        return "editEmail";
    }

    @PostMapping("/editEmail")
    public String postEditEmail(@ModelAttribute @Valid UserEmailEditDto userEmailEditDto, BindingResult result, Model model){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }

        if(result.hasErrors()){
            return "editEmail";
        }

        User user = (User)session.getAttribute("user");
        String newEmail = userEmailEditDto.getEmail();
        String password = user.getPassword();
        String confirmPassword = userEmailEditDto.getPassword();

        if(!BCrypt.checkpw(confirmPassword, password)){
            model.addAttribute("password", true);
            return "editEmail";
        }

        String oldEmail = user.getEmail();
        user.setEmail(newEmail);
        String effect = userService.saveToDb(user);

        if(effect.equals("email")){
            model.addAttribute("email", true);
            user.setEmail(oldEmail);
            return "editEmail";
        }

        model.addAttribute("success", true);
        return "editEmail";
    }
}
