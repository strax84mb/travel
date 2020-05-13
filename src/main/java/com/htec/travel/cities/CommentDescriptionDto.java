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
public class CommentDescriptionDto {

    @Pattern(regexp = "^[^\\;\\\\\\#]{1,}$")
    @NotBlank
    private String text;
}
