package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.TokenResponse;
import peaksoft.dto.request.ProductRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AllProductsResponse;
import peaksoft.dto.response.AllUserResponse;
import peaksoft.dto.response.ProductResponse;
import peaksoft.dto.response.UserResponse;

import java.util.List;

public interface ProductService {
    SimpleResponse saveProduct(Long brandId,ProductRequest request);
    List<AllProductsResponse> getAllProduct();
    ProductResponse getProductById(Long id);
    SimpleResponse updateProductById(Long id,ProductRequest request);
    SimpleResponse deleteById(Long id);
    List<AllProductsResponse> search (String keyWord);
}
