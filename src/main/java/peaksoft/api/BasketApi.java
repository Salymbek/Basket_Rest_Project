package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.TokenResponse;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AllUserResponse;
import peaksoft.dto.response.BasketResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketApi {
    private final BasketService basketService;


    public BasketApi(BasketService basketService) {
        this.basketService = basketService;
    }


    @PreAuthorize("permitAll()")
    @PostMapping("/{userId}")
    public SimpleResponse save (@PathVariable Long userId,
                                @RequestBody @Valid BasketRequest request){
        return basketService.saveBasket(userId,request);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("permitAll()")
    public List<BasketResponse> getAll(@PathVariable Long userId){
        return basketService.getAllBasket(userId);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("permitAll()")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return basketService.delete(id);
    }
}
