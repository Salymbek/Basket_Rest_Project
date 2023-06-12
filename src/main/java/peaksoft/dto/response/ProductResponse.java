package peaksoft.dto.response;

import peaksoft.model.Comment;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        List<String>images,
        String characteristic,
        String madeIn,
        Boolean favorite,
        List<Comment> comments
) {
}
