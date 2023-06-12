package peaksoft.api;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.*;

import peaksoft.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandApi {

    private final BrandService brandService;

    public BrandApi(BrandService brandService) {
        this.brandService = brandService;
    }


    @PreAuthorize("permitAll()")
    @PostMapping
    public SimpleResponse save (@RequestBody @Valid BrandRequest request){
        return brandService.saveBrand(request);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<BrandResponse> getAll(){
        return brandService.getAllBrand();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public BrandResponse findById (@PathVariable Long id){
        return brandService.getBrandById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateBrand(@PathVariable Long id, @RequestBody @Valid BrandRequest request){
        return brandService.updateBrandById(id,request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteBrand(@PathVariable Long id){
        return brandService.deleteById(id);
    }
}