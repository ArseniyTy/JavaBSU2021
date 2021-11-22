package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Reaction;
import by.arseniyty.lab04spring.repository.CommentsRepository;
import by.arseniyty.lab04spring.repository.ReactionsRepository;
import by.arseniyty.lab04spring.service.CommentsService;
import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentsRepository repository;

    @Autowired
    CommentsService service;

    @Autowired
    ReactionsRepository reactionsRepository;

    @Autowired
    UsersService usersService;

    @PostMapping("/{id}/like")
    String likeComment(@ModelAttribute Comment likedComment,
                       @PathVariable Long id) {

//        Comment comment = repository.findById(id).orElseThrow();
//        Reaction r = new Reaction();
//        r.setLiked(true);
//        r.setUser(usersService.getCurrentAuthenticatedUser());
//        r.setComment(comment);
//        reactionsRepository.save(r);

        service.reactToCommentByButtonClick(id, true);
        return "redirect:/questions/" + repository.findById(id).orElseThrow().getQuestion().getId();
    }

    @PostMapping("/{id}/dislike")
    String dislikeComment(@ModelAttribute Comment likedComment,
                          @PathVariable Long id) {
//        Comment comment = repository.findById(id).orElseThrow();
//        Reaction r = new Reaction();
//        r.setLiked(false);
//        r.setUser(usersService.getCurrentAuthenticatedUser());
//        r.setComment(comment);
//        reactionsRepository.save(r);
//        comment.setDislikesCount(comment.getDislikesCount() + 1);
//        repository.save(comment);
//        System.out.println(service.getCommentsDislikesCount(id));

        service.reactToCommentByButtonClick(id, false);
        return "redirect:/questions/" + repository.findById(id).orElseThrow().getQuestion().getId();
    }

    @RequestMapping("/{id}/delete")
    String deleteComment(@PathVariable Long id) {
        var questionId = repository.findById(id).orElseThrow().getQuestion().getId();
        repository.deleteById(id);
        return "redirect:/questions/" + questionId;
    }
}
