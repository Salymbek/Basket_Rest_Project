package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.BrandRequest;
import peaksoft.dto.response.BrandResponse;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Brand;
import peaksoft.repository.BrandRepository;
import peaksoft.service.BrandService;

import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;


    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public SimpleResponse saveBrand(BrandRequest request) {

        Brand brand = new Brand();
        brand.setBrandName(request.brandName());
        brand.setImage(request.image());

        brandRepository.save(brand);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Brand with name: "+request.brandName()+" is successfully saved!")
                .build();
    }

    @Override
    public List<BrandResponse> getAllBrand() {
        return brandRepository.findAllBrand();
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        return brandRepository.findByBrandId(id).orElseThrow(
                ()-> new NotFoundException("Brand with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateBrandById(Long id, BrandRequest request) {

        Brand brand = brandRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Brand with id: "+id+" not found!")
        );

        brand.setBrandName(request.brandName());
        brand.setImage(request.image());

        brandRepository.save(brand);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Brand with id: "+id+" is successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {

        Brand brand = brandRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Brand with id: "+id+" not found!")
        );
        //
        brandRepository.delete(brand);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Brand with id - %s is deleted!", id)
        );
    }
}
