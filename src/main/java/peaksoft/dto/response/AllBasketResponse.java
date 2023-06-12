package peaksoft.dto.response;

import java.util.List;

public record AllBasketResponse(
        Long id,
        String userName,
        List<String>productNames
) {
}
