package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_gen",sequenceName = "comment_seq",allocationSize = 1)
    private Long id;
    private String comment;
    private ZonedDateTime createDate;
    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private User user;
    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private Product product;

    public Comment(String comment, ZonedDateTime createDate) {
        this.comment = comment;
        this.createDate = createDate;
    }
}