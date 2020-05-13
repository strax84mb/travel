package com.htec.travel.cities;

import com.htec.travel.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private BigInteger id;
    private String description;
    private LocalDate created;
    private LocalDate modified;
    private BigInteger posterId;
}
