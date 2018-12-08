package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.beans.BCrypt;
import pl.coderslab.dtos.UserEditPasswordDto;
import pl.coderslab.entity.User;
import pl.coderslab.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class EditPasswordController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;


    @GetMapping("/editPassword")
    public String getEditPassword(Model model){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }
        model.addAttribute("userEditPasswordDto", new UserEditPasswordDto());
        return "editPassword";
    }

    @PostMapping("/editPassword")
    public String postEditPassword(@ModelAttribute @Valid UserEditPasswordDto userEditPasswordDto, BindingResult result, Model model){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }
        if(result.hasErrors()){
            return "editPassword";
        }

        String newPassword = userEditPasswordDto.getNewPassword();
        String confirmNewPassword = userEditPasswordDto.getConfirmNewPassword();
        if(!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("differentPassword", true);
            return "editPassword";
        }

        User user = (User) session.getAttribute("user");

        String oldPassword = userEditPasswordDto.getOldPassword();
        if(!BCrypt.checkpw(oldPassword, user.getPassword())){
            model.addAttribute("wrongPassword", true);
            return "editPassword";
        }

        user.setPassword(userEditPasswordDto.getNewPassword());
        user.hashPassword();
        userService.saveToDb(user);

        model.addAttribute("success", true);
        return "redirect:/main";
    }
}
