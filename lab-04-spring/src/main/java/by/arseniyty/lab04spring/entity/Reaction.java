package by.arseniyty.lab04spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isLiked;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "commentId", nullable = false)
    private Comment comment;
}
