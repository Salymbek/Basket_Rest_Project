package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.AllProductsResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductApi {

    private final ProductService productService;


    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{brandId}")
    public SimpleResponse saveProduct(@PathVariable Long brandId,
                                      @RequestBody  ProductRequest request){
        return productService.saveProduct(brandId, request);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<AllProductsResponse> getAll(){
        return productService.getAllProduct();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse update (@PathVariable Long id, @RequestBody ProductRequest request){
        return productService.updateProductById(id,request);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete (@PathVariable Long id){
        return productService.deleteById(id);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public List<AllProductsResponse> search(@RequestParam(name = "keyWord", required = false) String keyWord) {
        return productService.search(keyWord);
    }

}
