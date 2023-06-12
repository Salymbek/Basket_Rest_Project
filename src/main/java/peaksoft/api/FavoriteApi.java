package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.FavoriteRequest;
import peaksoft.dto.response.FavoriteResponse;
import peaksoft.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteApi {
    private final FavoriteService favoriteService;

    public FavoriteApi(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @PreAuthorize("permitAll()")
    @PostMapping
    public SimpleResponse addFavorite(@RequestBody @Valid FavoriteRequest request){
        return favoriteService.addFavorite(request);
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public FavoriteResponse findById(@PathVariable Long id){
        return favoriteService.getFavoriteById(id);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<FavoriteResponse> getAll(){
        return favoriteService.getAllFavorite();
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("permitAll()")
//    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid FavoriteRequest request){
//        return favoriteService.updateById(id,request);
//    }
    @DeleteMapping("/{id}")
    @PreAuthorize("permitAll()")
    public SimpleResponse delete(@PathVariable Long id){
        return favoriteService.delete(id);
    }



}
