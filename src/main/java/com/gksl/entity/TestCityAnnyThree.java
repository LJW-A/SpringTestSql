package com.gksl.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//某一个字段为空不返回
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)

public class TestCityAnnyThree implements Serializable {

    private int id;
    @NonNull
    private int  Nunber;
    @NonNull
    private String Name;


}
