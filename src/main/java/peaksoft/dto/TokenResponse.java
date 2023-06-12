package peaksoft.dto;

import lombok.Builder;

@Builder
public record TokenResponse(
        String token,
        String email
) {
}
