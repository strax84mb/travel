package com.htec.travel.cities;

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
public class CreateCityDto {

    @Pattern(regexp = "^[^\\/\\.\\;\\'\\\"\\\\\\#]{1,30}$", message = "City name must be between 1 and 30 characters long")
    @NotBlank
    private String name;

    @Pattern(regexp = "^[^\\/\\.\\;\\\"\\\\\\#]{1,50}$", message = "Country name must be between 1 and 50 characters long")
    @NotBlank
    private String country;

    @Pattern(regexp = "^[^\\/\\;\\\\\\#]{1,255}$", message = "City description must be between 1 and 255 characters long")
    @NotBlank
    private String description;
}
