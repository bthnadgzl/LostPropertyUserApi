package com.kayipesyaUser.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
public enum AvailableUniversities {
    ITU("@itu.edu.tr");

    private String universityMailCode;


    @Override
    public String toString() {
        return this.universityMailCode;
    }
}
