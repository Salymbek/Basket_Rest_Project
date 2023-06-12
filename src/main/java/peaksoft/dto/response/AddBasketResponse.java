package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record AddBasketResponse(
        Long id,
        Long productId,
        Long userId
) {
}
