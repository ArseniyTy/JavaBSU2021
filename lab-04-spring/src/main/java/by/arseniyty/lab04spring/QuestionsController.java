package by.arseniyty.lab04spring;

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

    @GetMapping("/{id}")
    String getQuestion(Model model, @PathVariable Long id) {
        model.addAttribute("question", repository.findById(id).orElseThrow());
        model.addAttribute("comments", commentsRepository.findAllByOrderBygetPopularityDesc());
        model.addAttribute("newComment", new Comment());
        model.addAttribute("answer", "");
        return "question";
    }

    @GetMapping("/secret")
    String test() {
        return "redirect:../";
    }

    @PostMapping("/{id}/addComment")
    String addComment(@ModelAttribute Comment newComment, @PathVariable Long id) {
        newComment.setId(0L);  // by default: newComment.id == id (magic)
        newComment.setQuestion(repository.findById(id).orElseThrow());
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
}
