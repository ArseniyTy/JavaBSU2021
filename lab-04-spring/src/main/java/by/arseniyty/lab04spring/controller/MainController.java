package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.StringHolder;
import by.arseniyty.lab04spring.entity.Question;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    QuestionsRepository questionsRepository;

    @Autowired
    UsersService usersService;

    @GetMapping
    String startPage(Model model,
                     @RequestParam(defaultValue = "") String substr) {
        model.addAttribute("questions", questionsRepository.findAllByTextContainsIgnoreCase(substr));
        model.addAttribute("newQuestion", new Question());
        model.addAttribute("questionSubstr", new StringHolder(substr));
        return "index";
    }

    // WebSecurityConfig has |.formLogin().successForwardUrl("/")|. But it forwards to Post. So we must have this.
    @PostMapping("/afterLogin")
    String afterLogin() {
        return "redirect:";
    }

    @PostMapping("/addQuestion")
    String addQuestion(@ModelAttribute Question newQuestion) {
        newQuestion.setUser(usersService.getCurrentAuthenticatedUser());
        questionsRepository.save(newQuestion);
        return "redirect:";
    }

    @GetMapping("/findQuestion")
    String findQuestion(@ModelAttribute StringHolder substr) {
        return "redirect:?substr=" + substr.getValue();
    }
}
