package com.gksl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestUser implements Serializable {

    private String  id;
    @NonNull
    private String username;
    @NonNull
    private String password;

}
