package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Basket;
import peaksoft.model.Product;
import peaksoft.model.User;
import peaksoft.repository.BasketRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.BasketService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public BasketServiceImpl(BasketRepository basketRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public SimpleResponse saveBasket(Long userId, BasketRequest request) {

        List<Product> products = new ArrayList<>();
        Basket basket = new Basket();
        for (Long productId : request.productsId()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException(String.format("Product with id - %s is not found!", productId)));
            products.add(product);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id - %S not found!", userId)));
        basket.setUser(user);

        for (Product product : products) {
            product.getBaskets().add(basket);
        }
        basket.setProducts(products);
        basketRepository.save(basket);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your cheque has been accepted!")
                .build();
    }




    @Override
    public BasketResponse getByIdBasket(Long basketId) {
        List<Basket> baskets = basketRepository.findAll();
        BasketResponse basketResponse = new BasketResponse();
        Long count = 0L;
        BigDecimal totalSum = new BigDecimal(0);

        for (Basket basket : baskets) {
            basketResponse.setProducts(basketRepository.getMeals(basketId));
            basketResponse.setUser(basket.getUser().getFirstName() + " " + basket.getUser().getLastName());

            for (Product product : basket.getProducts()) {
                totalSum = totalSum.add(product.getPrice());
                count++;
            }
        }

        basketResponse.setCountOfProducts(count);
        basketResponse.setGrandTotal(totalSum);

        return basketResponse;
    }






    @Override
    public SimpleResponse delete(Long id) {
        if (!basketRepository.existsById(id)) {
            throw new AlreadyExistException("Cheque with id - " + id + " doesn't exists!");

        }
        Basket basket = basketRepository.findById(id).orElseThrow(()->
                new NotFoundException("Basket with id - " + id +" doesn't exists!"));
        basket.getProducts().forEach(product -> product.getBaskets().remove(basket));
        basket.setUser(null);
        basketRepository.delete(basket);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Basket with id: %s successfully deleted", id))
                .build();
    }


}
