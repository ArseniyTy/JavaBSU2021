package by.arseniyty.lab04spring.service;

import by.arseniyty.lab04spring.entity.Comment;
import by.arseniyty.lab04spring.entity.Reaction;
import by.arseniyty.lab04spring.repository.CommentsRepository;
import by.arseniyty.lab04spring.repository.ReactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CommentsService {

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ReactionsRepository reactionsRepository;

    @Autowired
    private CommentsRepository repository;

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Long getQuestionsId(Long id) {
        return repository.findById(id).orElseThrow().getQuestion().getId();
    }

    public void createComment(String text, Long questionsId) {
        Comment newComment = new Comment(text);
        newComment.setQuestion(questionsService.getById(questionsId));
        newComment.setUser(usersService.getCurrentAuthenticatedUser());
        repository.save(newComment);
    }

    public List<Comment> getCommentsSortedByPopularity(Long questionId) {
        return StreamSupport.stream(
                repository
                        .findAllByQuestion(
                                questionsService.getById(questionId))
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

    /**
     * Updates specified comment likes/dislikes according to the standard process of social networking like/dislike
     * button clicking.
     * @param commentId comment, that the user reacted to
     * @param isLiked the logic representation of the clicked button
     */
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
