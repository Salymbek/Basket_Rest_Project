package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.BrandResponse;
import peaksoft.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {


    @Query("select new peaksoft.dto.response.BrandResponse(b.id,b.brandName,b.image)from Brand b")
    List<BrandResponse> findAllBrand();


    @Query("select new peaksoft.dto.response.BrandResponse(b.id,b.brandName,b.image)from Brand b where b.id=:id")
    Optional<BrandResponse> findByBrandId(Long id);
}