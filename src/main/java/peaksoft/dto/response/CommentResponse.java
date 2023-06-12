package peaksoft.dto.response;

import java.time.ZonedDateTime;

public record CommentResponse(
        Long id,
        String comment,
        ZonedDateTime createdAt
) {
}
