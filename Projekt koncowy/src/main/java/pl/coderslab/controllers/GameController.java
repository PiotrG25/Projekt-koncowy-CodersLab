package pl.coderslab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.beans.RandomMachine;
import pl.coderslab.entity.Game;
import pl.coderslab.entity.User;
import pl.coderslab.repository.GameRepository;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class GameController {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    HttpSession session;


    @GetMapping("/game")
    public String getGame(Model model, @RequestParam(required = false) String level){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }

        if(level == null || level.isEmpty()){
            return "redirect:/main";
        }

        Pattern pattern = Pattern.compile("[1-3]");
        Matcher matcher = pattern.matcher(level);
        if(!matcher.matches()){
            return "redirect:/main";
        }
        int levelInt = Integer.parseInt(level);

        session.setAttribute("level", levelInt);

        int [][] tab = RandomMachine.getTab(3 * 3);
        model.addAttribute("tab", tab);
        return "game";
    }

    @PostMapping("/game")
    public String postGame(@RequestParam(required = false) String moves, @RequestParam(required = false) String time){
        if(session.getAttribute("user") == null){
            return "redirect:/main";
        }

        Integer level = (Integer)session.getAttribute("level");

        if(moves == null || moves.isEmpty() || time == null || time.isEmpty()){
            return "redirect:/main";
        }

        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher movesMatcher = pattern.matcher(moves);
        Matcher timeMatcher = pattern.matcher(time);

        if(!movesMatcher.matches() || !timeMatcher.matches()){
            return "redirect:/main";
        }

        long movesLong = Long.parseLong(moves);
        long timeLong = Long.parseLong(time);

        User user = (User)session.getAttribute("user");
        System.out.println(user.getId());
        Game game = new Game(level, movesLong, timeLong, user);

        gameRepository.save(game);

        return "redirect:/main";
    }
}
