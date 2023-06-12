package peaksoft.dto.response;

import java.util.stream.LongStream;

public record FavoriteResponse(
        Long id,
        String productName,
        String firstName
) {
}
