package ru.kduskov.vkapi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class GeneralInfo {
    public static String user(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(auth))
            return "empty";
        return auth.getName();
    }
}
