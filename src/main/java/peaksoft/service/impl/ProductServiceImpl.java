package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.response.AllProductsResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Brand;
import peaksoft.model.Product;
import peaksoft.repository.BrandRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.service.ProductService;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }


    @Override
    public SimpleResponse saveProduct(Long brandId,ProductRequest request) {

        Brand brand = brandRepository.findById(brandId).orElseThrow(
                ()-> new NotFoundException("Brand with id: "+brandId+" not found!"));



        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setImages(request.images());
        product.setCharacteristic(request.madeIn());
        product.setIsFavorite(false);
        product.setMadeIn(request.madeIn());
        product.setCategory(request.category());

        product.setBrand(brand);
        productRepository.save(product);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Product with id: "+request.name()+" successfully saved!!!")
                .build();
    }

    @Override
    public List<AllProductsResponse> getAllProduct() {
        return productRepository.findAllProduct();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findByProductId(id).orElseThrow(
                ()-> new NotFoundException("Product with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateProductById(Long id, ProductRequest request) {

        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Product with id: "+id+" not found!"));
        Brand brand = brandRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Brand with id: "+id+" not found!")
        );

        product.setName(request.name());
        product.setPrice(request.price());
        product.setImages(request.images());
        product.setCharacteristic(request.madeIn());
        product.setMadeIn(request.madeIn());

        brand.addProduct(product);
        productRepository.save(product);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Product with id: "+request.name()+" successfully updated!!!")
                .build();

    }

    @Override
    public SimpleResponse deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Product with id: "+id+" not found!"));
        Brand brand = brandRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Brand with id: "+id+" not found!")
        );
        product.setBrand(null);
        brand.addProduct(null);
        productRepository.delete(product);

        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id - %s is deleted!", id)
        );
    }

    @Override
    public List<AllProductsResponse> search(String keyWord) {
        if (keyWord!= null && !keyWord.trim().isEmpty()){
            return productRepository.search(keyWord);
        }else {
            return productRepository.findAllProduct();
        }
    }
}
