package by.arseniyty.lab04spring.service;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Question;
import by.arseniyty.lab04spring.entity.Reaction;
import by.arseniyty.lab04spring.repository.CommentsRepository;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.repository.ReactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionsService {
    @Autowired
    private QuestionsRepository repository;

    @Autowired
    private UsersService usersService;

    public Iterable<Question> findAllThatContainsSubstr(String substr) {
        return repository.findAllByTextContainsIgnoreCase(substr);
    }

    public void save(Question newQuestion) {
        newQuestion.setUser(usersService.getCurrentAuthenticatedUser());
        repository.save(newQuestion);
    }

    public void provideAnswer(Long id, String answer) {
        Question edited = getById(id);
        edited.setAnswer(answer);
        repository.save(edited);
    }

    public Question getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
