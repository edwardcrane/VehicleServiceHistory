package com.airanza.vehicleservicehistory;

import android.content.res.Resources;

import java.util.Arrays;

public class Utils {
    public static int findIndexOf(String stringToFind, Resources res, int id_of_string_array_resource) {
        return(Arrays.asList(res.getStringArray(id_of_string_array_resource)).indexOf(stringToFind));
    }
}
