package com.htec.travel.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDto {

    @Pattern(regexp = "^[^\\.\\;\\'\\\"\\\\\\#]{1,20}$", message = "First name must be between 1 and 20 characters long")
    @NotBlank
    private String firstName;

    @Pattern(regexp = "^[^\\.\\;\\'\\\"\\\\\\#]{1,30}$", message = "Last name must be between 1 and 20 characters long")
    @NotBlank
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,30}$", message = "Username must be between 8 and 30 characters long and can contain small and upper case letters and numbers")
    @NotBlank
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "Last name must be between 8 and 15 characters long  and can contain small and upper case letters and numbers")
    @NotBlank
    private String password;
}
