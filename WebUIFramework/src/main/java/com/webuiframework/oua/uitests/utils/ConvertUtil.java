package com.webuiframework.oua.uitests.utils;

public class ConvertUtil {

    /**
     * Convert String[] to String
     *
     * @param str - input String [] for converting
     * @param separator - separator. used while converting input String [] to String
     * @return Convert String[] to String
     */
    public static String arrayToString( String[] str, String separator){
        StringBuilder result = new StringBuilder();

        if (str.length > 0) {
            result.append(str[0]);
            for (int i=1; i<str.length; i++) {
                result.append(separator);
                result.append(str[i]);
            }
        }
        return result.toString();
    }

    /**
     * Set first character from text(e.g. chemicalName) to LOW CASE
     * @param text - text for changing
     * @return text with first character in LOWER Case
     */
    public static String setFirstCharToLowerCase(String text){
        return text.substring(0,1).toLowerCase() + text.replaceAll("\\D{1}([\\D]*)","$1").trim();
    }
}

