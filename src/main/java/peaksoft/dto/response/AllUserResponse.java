package peaksoft.dto.response;

import peaksoft.enums.Role;

import java.time.ZonedDateTime;

public record AllUserResponse(
        Long id,
        String fullName,
        String email,
        Role role
) {
}
