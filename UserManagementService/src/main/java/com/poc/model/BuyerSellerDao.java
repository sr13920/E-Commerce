package com.poc.model;

import java.util.*;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerSellerDao {
    @NotNull
    private String username;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String email;
}
