package com.kayipesyaUser.util;

import com.kayipesyaUser.constant.AvailableUniversities;
import com.kayipesyaUser.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UniversityMailValidation {
    private static final List<String> availableUniversitiesList = Arrays.stream(AvailableUniversities.values()).map(university -> university.getUniversityMailCode()).collect(Collectors.toList());

    public static void universityMailValidation(String registerRequestEmail){
        for(String emailCode:availableUniversitiesList){
            if(registerRequestEmail.contains(emailCode))
                break;
            else
                throw new CustomException(HttpStatus.BAD_REQUEST,"EMAIL FORBIDDEN.(Use Your University Mail.)");
        }
    }
}
