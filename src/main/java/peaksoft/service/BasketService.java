package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AddBasketResponse;
import peaksoft.dto.response.AllBasketResponse;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.model.Product;
import peaksoft.model.User;

import java.util.List;

public interface BasketService {
    SimpleResponse saveBasket(Long userId,BasketRequest basketRequest);
    List<BasketResponse> getAllBasket(Long userId);
    SimpleResponse delete(Long id);

//    AddBasketResponse addToBasket(User user, Product product);
//    AddBasketResponse removeBasket(User user, Product product);

//    SimpleResponse updateBasketById(Long id,BasketRequest basketRequest);
//    SimpleResponse deleteById(Long id);
}
