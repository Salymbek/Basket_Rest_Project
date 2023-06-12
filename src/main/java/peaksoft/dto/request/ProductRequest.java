package peaksoft.dto.request;

import peaksoft.enums.Category;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
        String name,
        BigDecimal price,
        List<String>images,
        String characteristic,
        Boolean isFavorite,
        String madeIn,
        Category category
) {
}
