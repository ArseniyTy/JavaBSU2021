package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Question;
import by.arseniyty.lab04spring.service.CommentsService;
import by.arseniyty.lab04spring.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    QuestionsService service;

    @Autowired
    CommentsService commentsService;

    @GetMapping("/{id}")
    String getQuestion(Model model, @PathVariable Long id) {
        model.addAttribute("question", service.getById(id));
        model.addAttribute("commentsService", commentsService);
        model.addAttribute("newComment", new Comment());
        return "question";
    }

    @GetMapping("/back")
    String goBack() {
        return "redirect:../";
    }

    @PostMapping("/{id}/addComment")
    String addComment(@ModelAttribute Comment newComment, @PathVariable Long id) {
        // By default: newComment.id == id (because associate <form> with questions id).
        // If we want to pass newComment somewhere else we should change id.
        commentsService.createComment(newComment.getText(), id);
        return "redirect:/questions/" + id;
    }

    @PostMapping("/{id}/provideAnswer")
    String provideAnswer(@ModelAttribute Question editedQuestion, @PathVariable Long id) {
        service.provideAnswer(id, editedQuestion.getAnswer());
        return "redirect:/questions/" + id;
    }

    // If it was RequestMapping, the user could manage to delete by entering this link in the browser. Not quite safe.
    @PostMapping("/{id}/delete")
    String deleteQuestion(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/";
    }
}
