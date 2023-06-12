package peaksoft.dto.response;

import peaksoft.enums.Role;

import java.time.ZonedDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        ZonedDateTime createdDate,
        ZonedDateTime updateDate,
        Role role
) {
}
