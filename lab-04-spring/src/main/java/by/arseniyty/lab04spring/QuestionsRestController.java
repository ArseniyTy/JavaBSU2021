package by.arseniyty.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/questions")
public class QuestionsRestController {

    @Autowired
    QuestionsRepository repository;

    @GetMapping("/")
    public Iterable<Question> getAll(@RequestParam(defaultValue = "") String value) {
        return repository.findAllByTextContainsIgnoreCase(value);
    }

    @GetMapping("/{id}")
    public Question get(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Question add(@RequestBody Question new_question) {
        return repository.save(new_question);
    }

    @PutMapping("/{id}")
    public Question change(@RequestBody Question new_question,
                           @PathVariable Long id) {
        // удалить и добавить новый с таким же id?
        return repository.findById(id)
                .map(question -> {
                    question.setText(new_question.getText());
                    return repository.save(question);
                })
                .orElseGet(() -> {
                    new_question.setId(id);
                    return repository.save(new_question);
                });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
