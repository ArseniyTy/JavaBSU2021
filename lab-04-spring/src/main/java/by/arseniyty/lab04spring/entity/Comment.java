package by.arseniyty.lab04spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Comment {

    public Comment(String text) {
        setText(text);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT", nullable = false)
    private String text;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
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
