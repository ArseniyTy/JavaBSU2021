package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.StringHolder;
import by.arseniyty.lab04spring.entity.Question;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    QuestionsRepository questionsRepository;

    @GetMapping
    String startPage(Model model,
                     @RequestParam(defaultValue = "") String substr) {
        model.addAttribute("questions", questionsRepository.findAllByTextContainsIgnoreCase(substr));
        model.addAttribute("newQuestion", new Question());
        model.addAttribute("questionSubstr", new StringHolder(substr));
        return "index";
    }

    @GetMapping("/login")
    String login() {
        return "redirect:/login";  // redirect to default login page, provided by SecurityConfig
    }

    @PostMapping
    String addQuestion(@ModelAttribute Question newQuestion) {
        questionsRepository.save(newQuestion);
        return "redirect:";
    }

    @RequestMapping("/findQuestion")
    String findQuestion(@ModelAttribute StringHolder substr) {
        return "redirect:?substr=" + substr.getValue();
    }
}
