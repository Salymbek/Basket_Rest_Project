package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;

import peaksoft.service.BasketService;


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
                                @RequestBody  BasketRequest request){
        return basketService.saveBasket(userId,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public BasketResponse getById(@PathVariable Long id){
        return basketService.getByIdBasket(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("permitAll()")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return basketService.delete(id);
    }
}
