package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.FavoriteRequest;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Favorite;
import peaksoft.model.Product;
import peaksoft.model.User;
import peaksoft.repository.FavoriteRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.FavoriteService;

import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SimpleResponse addFavorite(FavoriteRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(
                () -> new NotFoundException("User with id: " + request.userId() + " not found!"));

        Product product = productRepository.findById(request.productId()).orElseThrow(
                () -> new NotFoundException("Product with id: " + request.productId() + " not found!"));

        if (user != null && product != null) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setProduct(product);
            favoriteRepository.save(favorite);

            product.setIsFavorite(true);
            productRepository.save(product);

            product.addFavorite(favorite);
            user.addFavorite(favorite);

            return new SimpleResponse( HttpStatus.OK,("Is favorite successfully save!"));
        }else {
            return new SimpleResponse(HttpStatus.BAD_REQUEST,("User or Product not found!!!"));
        }
    }

    @Override
    public List<FavoriteResponse> getAllFavorite() {
        return favoriteRepository.findAllFavorite();
    }

    @Override
    public FavoriteResponse getFavoriteById(Long id) {
        return favoriteRepository.findByFavoriteId(id).orElseThrow(
                ()-> new NotFoundException("Favorite with id: "+id+" not found!"));
    }


    @Override
    public SimpleResponse delete(Long id) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Favorite with id: "+" not found!"));

        User user = userRepository.findById(favorite.getUser().getId()).orElseThrow(
                () -> new NotFoundException("User with id: " + favorite.getUser().getId() + " not found!"));

        Product product = productRepository.findById(favorite.getProduct().getId()).orElseThrow(
                () -> new NotFoundException("Product with id: " + favorite.getProduct().getId() + " not found!"));


                product.setIsFavorite(false);
                product.addFavorite(null);
                user.addFavorite(null);
                productRepository.save(product);

                favoriteRepository.delete(favorite);


        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();

    }
}
