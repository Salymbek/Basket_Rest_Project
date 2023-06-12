package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.method.P;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "brands")
@Setter
@Getter
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(generator = "brand_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "brand_gen",sequenceName = "brand_seq",allocationSize = 1)
    private Long id;
    private String brandName;
    private String image;
    @OneToMany(mappedBy = "brand",cascade = {DETACH,MERGE,REFRESH})
    private List<Product>products;

    public void addProduct(Product product){
        if (products == null){
            products = new ArrayList<>();
        }
        products.add(product);
    }

}