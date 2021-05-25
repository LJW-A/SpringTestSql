package com.gksl.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
//某一个字段为空不返回
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TestCityAnnyOne implements Serializable {

    private int  id;
    @NonNull
    private String  Tity;
    @NonNull
    private int Gdp;
    @NonNull
    private int Pcgdp;

    private List<TestCityAnny> testCityAnny;


}
