package peaksoft.dto.response;

import peaksoft.enums.Category;

import java.math.BigDecimal;
import java.util.List;

public record AllProductsResponse(
        Long id,
        String name,
        BigDecimal price,
        List<String> images,
        String characteristic,
        String madeIn,
        Category category
) {
}
