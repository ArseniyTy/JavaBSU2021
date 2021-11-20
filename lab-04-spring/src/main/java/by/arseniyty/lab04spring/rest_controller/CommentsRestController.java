package by.arseniyty.lab04spring.rest_controller;

import by.arseniyty.lab04spring.repository.CommentsRepository;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/comments")
public class CommentsRestController {

    @Autowired
    CommentsRepository repository;

    @Autowired
    QuestionsRepository questionsRepository;

    @GetMapping("/{id}")
    public Comment get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Comment add(@RequestBody Comment newComment) {
        return repository.save(newComment);
    }

    @PutMapping("/{id}")
    public Comment change(@RequestBody Comment newComment,
                          @PathVariable Long id) {
        return repository.findById(id)
                .map(comment -> {
                    comment.setText(newComment.getText());
                    return repository.save(comment);
                })
                .orElseGet(() -> {
                    newComment.setId(id);
                    return repository.save(newComment);
                });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
