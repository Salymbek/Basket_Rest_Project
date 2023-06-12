package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.AddBasketResponse;
import peaksoft.dto.response.AllBasketResponse;
import peaksoft.dto.response.BasketResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        basketRepository.save(basket);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your cheque has been accepted!")
                .build();
    }




    @Override
    public List<BasketResponse> getAllBasket(Long userId) {
        List<BasketResponse> baskets = new ArrayList<>();
        for (BasketResponse ch : basketRepository.findAllBasket(userId)) {
            List<Product>products = new ArrayList<>();
            int totalQuantity = 0;
            for (Product product : products) {
                totalQuantity += product.getAmount();
            }
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (Product product : products) {
                BigDecimal productPrice = product.getPrice();
                BigDecimal productQuantity = BigDecimal.valueOf(product.getAmount());
                BigDecimal productTotal = productPrice.multiply(productQuantity);
                totalAmount = totalAmount.add(productTotal);
            }

            ch.setName(productRepository.getProducts(ch.getId()).toString());
            ch.setAmount(totalQuantity);
            ch.setSum(totalAmount);

            baskets.add(ch);


//            BigDecimal total = ch.getAveragePrice().multiply(new BigDecimal(ch.getService())).divide(new BigDecimal(100)).add(ch.getAveragePrice());
//            ch.setGrandTotal(total);
//            ch.setMeals(chequeRepository.getFoods(ch.getId()));
//            cheques.add(ch);
        }
        return baskets;
//
//        Integer totalQuantity = 0;
//        for (Product product : products) {
//            totalQuantity +=
//        }
//
//
//        List<BasketResponse> cheques = new ArrayList<>();
//        for (BasketResponse ch : basketRepository.findAllBasket(userId)) {
//            BigDecimal total = ch.getAveragePrice().multiply(new BigDecimal(ch.getService())).divide(new BigDecimal(100)).add(ch.getAveragePrice());
//            ch.sum(total);
//            ch.setMeals(chequeRepository.getFoods(ch.getId()));
//            cheques.add(ch);
//        }
//        return cheques;
//    }
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!basketRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("Basket with id: %s not found", id))
                    .build();
        }
        Basket basket = basketRepository.findById(id).orElseThrow(()->
                new NotFoundException("Basket with id - " + id +" doesn't exists!"));
        basket.getProducts().forEach(product -> product.getBaskets().remove(basket));
        basket.setUser(null);
        basketRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Basket with id: %s successfully deleted", id))
                .build();
    }




//        User user = userRepository.findById(userId).orElseThrow(
//                ()-> new NotFoundException("User with email: "+userId+" not found!!!"));
//
//        Basket basket = Basket.builder().user(user).build();
//        user.setBasket(basket);
//
//        basketRepository.save(basket);
//
//        return SimpleResponse.builder()
//                .status(HttpStatus.OK)
//                .message("Basket successfully saved!")
//                .build();
//    }

//    @Override
//    public BasketResponse getBasket() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new NotFoundException("User email: %s not found".formatted(email)));
//        return basketRepository.getBasket(user.getId());
//    }
//
//    @Override
//    public AddBasketResponse addToBasket(User user, Product product) {
//        Basket basket = new Basket();
//        basket.setUser(user);
//        basket.setProducts(List.of(product));
//        basketRepository.save(basket);
//        return AddBasketResponse.builder()
//                .id(basket.getId())
//                .productId(basket.getProducts().get(0).getId())
//                .userId(basket.getUser().getId())
//                .build();
//    }
//
//    @Override
//    public AddBasketResponse removeBasket(User user, Product product) {
//        Basket basket = new Basket();
//        basketRepository.deleteByUserAndProduct(user, product);
//        return AddBasketResponse.builder()
//                .id(basket.getId())
//                .productId(basket.getProducts().get(0).getId())
//                .build();
//    }


//    @Override
//    public SimpleResponse updateBasketById(Long id, BasketRequest request) {
//
//        User user = userRepository.findById(request.userId()).orElseThrow(
//                ()-> new NotFoundException("User with id: "+request.userId()+" not found!!!"));
//
//        Basket basket = basketRepository.findById(id).orElseThrow(
//                ()-> new NotFoundException("Basket with id: "+id+" not found!")
//        );
//        List<Product> products = new ArrayList<>();
//
//
//        basket.setProducts(products);
//        basket.setUser(user);
//
//
//        basketRepository.save(basket);
//
//        return SimpleResponse.builder()
//                .status(HttpStatus.OK)
//                .message("Basket successfully saved!")
//                .build();
//    }
//
//    @Override
//    public SimpleResponse deleteById(Long id) {
//        Basket basket = basketRepository.findById(id).orElseThrow(
//                ()-> new NotFoundException("Basket with id: "+id+" not found"));
//
//        basket.addProduct(null);
//        basket.setUser(null);
//        basketRepository.delete(basket);
//
//        return SimpleResponse.builder()
//                .status(HttpStatus.OK)
//                .message("Successfully deleted!")
//                .build();
//    }
}
