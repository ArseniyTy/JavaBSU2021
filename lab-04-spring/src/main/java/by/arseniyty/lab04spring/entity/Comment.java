package by.arseniyty.lab04spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String text;
//    private Long likesCount = 0L;
//    private Long dislikesCount = 0L;

    @JsonBackReference  // to drop recursive json serialisation
    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "comment",
            orphanRemoval = true,
            cascade=CascadeType.ALL)
    private List<Reaction> reactions;

    public int getPopularity() {
        return reactions.size();
    }
}
