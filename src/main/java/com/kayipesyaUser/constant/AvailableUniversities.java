package com.kayipesyaUser.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum AvailableUniversities {
    ITU("@itu.edu.tr");

    private String universityMailCode;


}
