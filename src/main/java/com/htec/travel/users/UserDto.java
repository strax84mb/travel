package com.htec.travel.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private BigInteger id;
    private String firstName;
    private String lastName;
    private String username;
}
