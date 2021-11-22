package by.arseniyty.lab04spring.controller;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Question;
import by.arseniyty.lab04spring.repository.CommentsRepository;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.service.CommentsService;
import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    QuestionsRepository repository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    CommentsService commentsService;

    @Autowired
    UsersService usersService;

    @GetMapping("/{id}")
    String getQuestion(Model model, @PathVariable Long id) {
        model.addAttribute("question", repository.findById(id).orElseThrow());
        model.addAttribute("commentsService", commentsService);
        model.addAttribute("newComment", new Comment());
        model.addAttribute("answer", "");
        return "question";
    }

    @GetMapping("/back")
    String goBack() {
        return "redirect:../";
    }

    @PostMapping("/{id}/addComment")
    String addComment(@ModelAttribute Comment newComment, @PathVariable Long id) {
        newComment.setId(0L);  // by default: newComment.id == id (magic)
        newComment.setQuestion(repository.findById(id).orElseThrow());
        newComment.setUser(usersService.getCurrentAuthenticatedUser());
        commentsRepository.save(newComment);
        return "redirect:/questions/" + id;
    }

    @PostMapping("/{id}/provideAnswer")
    String addQuestion(@ModelAttribute Question editedQuestion, @PathVariable Long id) {
        Question q = repository.findById(id).orElseThrow();
        q.setAnswer(editedQuestion.getAnswer());
        repository.save(q);
        return "redirect:/questions/" + id;
    }

    @RequestMapping("/{id}/delete")
    String deleteQuestion(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
