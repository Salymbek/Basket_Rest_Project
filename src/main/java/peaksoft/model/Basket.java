package peaksoft.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "baskets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {
    @Id
    @GeneratedValue(generator = "basket_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "basket_gen",sequenceName = "basket_seq",allocationSize = 1)
    private Long id;
    @ManyToMany(cascade = {MERGE,DETACH,REFRESH})
    private List<Product>products;

    public void addProduct(Product product){
        if (products == null){
            products = new ArrayList<>();
        }
        products.add(product);
    }
    @OneToOne(cascade = {DETACH,REFRESH,MERGE})
    private User user;

}