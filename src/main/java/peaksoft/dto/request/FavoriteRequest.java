package peaksoft.dto.request;

public record FavoriteRequest(
        Long productId,
        Long userId
) {
}
