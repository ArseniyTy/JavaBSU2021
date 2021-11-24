package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.StringHolder;
import by.arseniyty.lab04spring.entity.Question;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.service.QuestionsService;
import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    QuestionsService questionsService;

    @GetMapping
    String startPage(@RequestParam(defaultValue = "") String substr,
                     Model model) {
        model.addAttribute("questions", questionsService.findAllThatContainsSubstr(substr));
        model.addAttribute("newQuestion", new Question());
        model.addAttribute("questionSubstr", new StringHolder(substr));
        return "index";
    }

    @PostMapping("/addQuestion")
    String addQuestion(@ModelAttribute Question newQuestion) {
        questionsService.save(newQuestion);
        return "redirect:";
    }

    @GetMapping("/findQuestion")
    String findQuestion(@ModelAttribute StringHolder substr) {
        return "redirect:?substr=" + substr.getValue();
    }
}
