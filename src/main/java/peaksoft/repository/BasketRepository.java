package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.AllBasketResponse;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.model.Basket;
import peaksoft.model.Product;
import peaksoft.model.User;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Query("select new peaksoft.dto.response.BasketResponse(b.id, p.name,p.amount,b.sum)from Basket b join b.products p")
    List<BasketResponse> findAllBasket(Long id);



//    @Query("select b.id,b.user,b.products from User u join u.basket b where u.id=:id")
//    List<BasketResponse> findAllBasket(Long id);
//    @Query("select s from Basket s where s.id=:id")
//    Optional<BasketResponse> findBasketById(Long id);
//    @Query("select p.name,cast(count (p.id) as int),sum (p.price) from Basket b join b.products p where b.user.id = ?1")
//    BasketResponse getBasket(Long userId);

//    void deleteByUserAndProduct(User user, Product product);

}