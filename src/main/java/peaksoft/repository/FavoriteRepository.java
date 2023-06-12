package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.model.Favorite;
import peaksoft.model.Product;
import peaksoft.model.User;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {


    @Query("select new peaksoft.dto.response.FavoriteResponse(f.id,p.name,u.firstName)from Favorite f join f.product p join f.user u")
    List<FavoriteResponse>findAllFavorite();


    @Query("select new peaksoft.dto.response.FavoriteResponse(f.id,p.name,u.firstName)from Favorite f join f.product p join f.user u where f.id=:id")
    Optional<FavoriteResponse> findByFavoriteId(Long id);



}