package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsRepository repository;

    @PostMapping("/{id}/like")
    String likeComment(@ModelAttribute Comment likedComment,
                       @PathVariable Long id) {
        Comment comment = repository.findById(id).orElseThrow();
        comment.setLikesCount(comment.getLikesCount() + 1);
        repository.save(comment);
        return "redirect:/questions/" + comment.getQuestion().getId();
    }

    @PostMapping("/{id}/dislike")
    String dislikeComment(@ModelAttribute Comment likedComment,
                          @PathVariable Long id) {
        Comment comment = repository.findById(id).orElseThrow();
        comment.setDislikesCount(comment.getDislikesCount() + 1);
        repository.save(comment);
        return "redirect:/questions/" + comment.getQuestion().getId();
    }

    @RequestMapping("/{id}/delete")
    String deleteComment(@PathVariable Long id) {
        var questionId = repository.findById(id).orElseThrow().getQuestion().getId();
        repository.deleteById(id);
        return "redirect:/questions/" + questionId;
    }
}
