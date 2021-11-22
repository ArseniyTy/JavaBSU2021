package by.arseniyty.lab04spring.service;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Reaction;
import by.arseniyty.lab04spring.entity.Role;
import by.arseniyty.lab04spring.entity.User;
import by.arseniyty.lab04spring.repository.CommentsRepository;
import by.arseniyty.lab04spring.repository.QuestionsRepository;
import by.arseniyty.lab04spring.repository.ReactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CommentsService {
    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ReactionsRepository reactionsRepository;

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

    public long getCommentsLikesCount(Long id) {
        return StreamSupport.stream(
                        reactionsRepository.findAllByCommentId(id)
                                .spliterator(), false)
                .filter(Reaction::isLiked)
                .count();
    }

    public long getCommentsDislikesCount(Long id) {
        return StreamSupport.stream(
                        reactionsRepository.findAllByCommentId(id)
                                .spliterator(), false)
                .filter(r -> !r.isLiked())
                .count();
    }

    public void reactToCommentByButtonClick(Long commentId, boolean isLiked) {
        Reaction reaction = reactionsRepository.findByUserIdAndCommentId(
                usersService.getCurrentAuthenticatedUser().getId(),
                commentId);

        if (reaction == null) {
            Reaction newReaction = new Reaction();
            newReaction.setLiked(isLiked);
            newReaction.setUser(usersService.getCurrentAuthenticatedUser());
            newReaction.setComment(repository.findById(commentId).orElseThrow());
            reactionsRepository.save(newReaction);
        } else {
            if (reaction.isLiked() == isLiked) {
                reactionsRepository.delete(reaction);
            } else {
                reaction.setLiked(isLiked);
                reactionsRepository.save(reaction);
            }
        }
    }
}
