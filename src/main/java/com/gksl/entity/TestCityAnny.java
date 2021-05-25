package com.gksl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCityAnny implements Serializable {


    private int  id;
    @NonNull
    private String  Tity;
    @NonNull
    private String District;
    @NonNull
    private String Towns;


}
