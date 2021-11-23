package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsService service;

    @PostMapping("/{id}/like")
    String likeComment(@PathVariable Long id) {
        service.reactToCommentByButtonClick(id, true);
        return RedirectBackToQuestion(service.getQuestionsId(id));
    }

    @PostMapping("/{id}/dislike")
    String dislikeComment(@PathVariable Long id) {
        service.reactToCommentByButtonClick(id, false);
        return RedirectBackToQuestion(service.getQuestionsId(id));

    }

    @RequestMapping("/{id}/delete")  // Not DeleteMapping, because the <form> can work only with POST/GET
    String deleteComment(@PathVariable Long id) {
        var questionId = service.getQuestionsId(id);
        service.deleteById(id);
        return RedirectBackToQuestion(questionId);
    }

    private String RedirectBackToQuestion(Long questionId) {
        return "redirect:/questions/" + questionId;
    }
}
