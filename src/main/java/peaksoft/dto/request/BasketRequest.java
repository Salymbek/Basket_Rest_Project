package peaksoft.dto.request;

import lombok.Builder;

import java.util.List;
@Builder
public record BasketRequest(
        List<Long> productsId,
        Long userId
) {
}
