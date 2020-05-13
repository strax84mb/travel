package com.htec.travel.cities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDto {

    private BigInteger id;
    private String name;
    private String country;
    private String description;
    private List<CommentDto> comments;
}
