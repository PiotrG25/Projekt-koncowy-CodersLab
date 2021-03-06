package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    HttpSession session;

    @RequestMapping("/main")
    public String mainAction(){
        if(session.getAttribute("user") == null){
            return "redirect:/login";
        }
        return "main";
    }
}
