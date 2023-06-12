package peaksoft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "product_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_gen",sequenceName = "product_seq",allocationSize = 1)
    private Long id;
    private String name;
    private BigDecimal price;
    @CollectionTable(name = "product_images",joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection
    @Column(name = "image")
    private List<String> images;
    private String characteristic;
    private Boolean isFavorite;
    private String madeIn;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int amount;

    @ManyToMany(mappedBy = "products",cascade = {DETACH,REFRESH,MERGE,REMOVE})
    private List<Basket>baskets;
    @ManyToOne(cascade = {DETACH,MERGE,REMOVE,REFRESH})
    private Brand brand;
    @OneToMany(mappedBy = "product",cascade = {DETACH,REFRESH,MERGE,REMOVE})
    private List<Comment>comments;

    public void addComments(Comment comment){
        if (comments == null){
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
    @OneToMany(mappedBy = "product",cascade = {DETACH,REFRESH,MERGE,REMOVE})
    private List<Favorite>favorites;

    public void addFavorite(Favorite favorite){
        if (favorites == null){
            favorites = new ArrayList<>();
        }
        favorites.add(favorite);
    }

}