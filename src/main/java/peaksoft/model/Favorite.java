package peaksoft.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "favorites")
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(generator = "favorite_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "favorite_gen",sequenceName = "favorite_seq",allocationSize = 1)
    private Long id;
    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private User user;
    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private Product product;
}