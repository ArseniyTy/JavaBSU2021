package by.arseniyty.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CommentsService {
    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private CommentsRepository repository;

    public List<Comment> getCommentsSortedByPopularity(Long questionId) {
        return StreamSupport.stream(
                repository
                        .findAllByQuestion(
                                questionsRepository.findById(questionId).orElseThrow())
                        .spliterator(), false)
                .sorted(Comparator.comparing(
                        Comment::getPopularity).reversed())
                .toList();
    }
}
