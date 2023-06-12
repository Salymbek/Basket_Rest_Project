package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.dto.response.CommentResponse;

import java.util.List;

public interface BrandService {
    SimpleResponse saveBrand(BrandRequest request);
    List<BrandResponse> getAllBrand();
    BrandResponse getBrandById(Long id);
    SimpleResponse updateBrandById(Long id,BrandRequest request);
    SimpleResponse deleteById(Long id);
}
