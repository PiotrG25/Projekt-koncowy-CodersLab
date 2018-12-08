package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.beans.BCrypt;
import pl.coderslab.dtos.UserDeleteDto;
import pl.coderslab.entity.User;
import pl.coderslab.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class DeleteUserController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    @GetMapping("/deleteUser")
    public String getDeleteUser(Model model){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }
        model.addAttribute("userDeleteDto", new UserDeleteDto());
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String postDeleteUser(@ModelAttribute @Valid UserDeleteDto userDeleteDto, BindingResult result, Model model){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }

        if(result.hasErrors()){
            return "deleteUser";
        }

        User user = (User)session.getAttribute("user");

        if(!BCrypt.checkpw(userDeleteDto.getPassword(), user.getPassword())){
            model.addAttribute("password", true);
            return "deleteUser";
        }

        user.setEnabled(false);
        userService.saveToDb(user);
        return "redirect:/logout";
    }
}
