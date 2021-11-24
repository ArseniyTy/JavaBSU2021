package by.arseniyty.lab04spring.rest_controller;

import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.entity.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/questions")
public class QuestionsRestController {

    @Autowired
    QuestionsRepository repository;

    @GetMapping
    public ResponseEntity<Iterable<Question>> getAllBySubstring(@RequestParam(defaultValue = "") String substr) {
        var questions = repository.findAllByTextContainsIgnoreCase(substr);
        if (!questions.iterator().hasNext()){
            return new ResponseEntity<>(questions, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> get(@PathVariable Long id) {
        var question = repository.findById(id);
        if (question.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(question.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Question> createNew(@RequestBody Question newQuestion) {
        return new ResponseEntity<>(repository.save(newQuestion), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Question> change(@RequestBody Question newQuestion,
                                           @PathVariable Long id) {
        return new ResponseEntity<>(
                repository.findById(id)
                .map(question -> {
                    question.setText(newQuestion.getText());
                    question.setAnswer(newQuestion.getAnswer());
                    return repository.save(question);
                })
                .orElseGet(() -> {
                    newQuestion.setId(id);
                    return repository.save(newQuestion);
                }),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> delete(@PathVariable Long id) {
        var question = repository.findById(id);
        if (question.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
