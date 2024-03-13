package ru.kduskov.vkapi.utils;

public class StringUtils {
    public static String substringIfMore(String input, int maxL) {
        if(input != null && input.length() > maxL)
            return input.substring(0, maxL);
        return input;
    }

    public static String cutWithEllipsis(String input, int maxL) {
        return substringIfMore(input, maxL - 3) + "...";
    }
}
