package by.arseniyty.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    QuestionsRepository questionsRepository;

    @GetMapping
    String startPage(Model model) {
        model.addAttribute("questions", questionsRepository.findAll());
        model.addAttribute("newQuestion", new Question());
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
}
