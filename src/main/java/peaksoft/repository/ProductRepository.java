package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.AllProductsResponse;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select new peaksoft.dto.response.AllProductsResponse(p.id,p.name,p.price,p.images,p.characteristic,p.madeIn,p.category)from Product p order by p.category")
    List<AllProductsResponse> findAllProduct();

    @Query("select new peaksoft.dto.response.ProductResponse(p.id,p.name,p.price,p.images,p.characteristic,p.madeIn,p.isFavorite,p.comments)from Product p join p.comments c where p.id=:id")
    Optional<ProductResponse> findByProductId(Long id);

    @Query("select p from Product p where p.category=:keyWord group by p")
    List<AllProductsResponse> search (String keyWord);

    @Query("select new peaksoft.dto.response.ProductBasketResponse(p.id, p.name )from Product p join p.baskets b where b.id = ?1 group by p.id, p.name")
    List<BasketResponse> getProducts (Long basketId);

}