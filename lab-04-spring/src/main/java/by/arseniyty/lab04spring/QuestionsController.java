package by.arseniyty.lab04spring;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    QuestionsRepository repository;

    @Autowired
    CommentsRepository commentsRepository;

    @GetMapping("/{id}")
    String getQuestion(Model model, @PathVariable Long id) {
        model.addAttribute("question", repository.findById(id).orElseThrow());
        model.addAttribute("newComment", new Comment());
        return "question";
    }

    @PutMapping("/{id}")


    @PostMapping("/addComment/{id}")
//    @PostMapping("/{id}")
    String addComment(@ModelAttribute Comment newComment, @PathVariable Long id) {
        newComment.setId(0L);  // by default: newComment.id == id (magic)
        newComment.setQuestion(repository.findById(id).orElseThrow());
        commentsRepository.save(newComment);
        return "redirect:/questions/" + id;
    }

}
