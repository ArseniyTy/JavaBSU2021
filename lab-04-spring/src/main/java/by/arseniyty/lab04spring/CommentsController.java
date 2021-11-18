package by.arseniyty.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsRepository repository;

    @PostMapping
    ModelAndView add(@ModelAttribute Comment newComment) {
        System.out.println("\n\n\nCOMMENTS---------------------------------------------------------------\n\n\n");
        System.out.println(newComment.getText());
        System.out.println(newComment.getId());
        System.out.println(newComment.getQuestion());

//        repository.save(newComment);
        return new ModelAndView("redirect:/questions/" + newComment.getQuestion().getId());
    }
}
