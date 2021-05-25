package com.gksl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCityAnnyTwo implements Serializable {

    private int id;
    @NonNull
    private String Tity;
    @NonNull
    private String Boy;
    @NonNull
    private String Girl;

    private List<TestCityAnnyOne> testCityAnnyOnes;
}
