package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.FavoriteRequest;
import peaksoft.dto.response.FavoriteResponse;

import java.util.List;

public interface FavoriteService {
    SimpleResponse addFavorite(FavoriteRequest request);
    List<FavoriteResponse> getAllFavorite();
    FavoriteResponse getFavoriteById(Long id);
//    SimpleResponse updateById(Long id, FavoriteRequest request);
    SimpleResponse delete(Long id);
}
