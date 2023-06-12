package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BasketRequest;
import peaksoft.dto.response.BasketResponse;

public interface BasketService {
    SimpleResponse saveBasket(Long userId,BasketRequest basketRequest);
    BasketResponse getByIdBasket(Long userId);
    SimpleResponse delete(Long id);

}
