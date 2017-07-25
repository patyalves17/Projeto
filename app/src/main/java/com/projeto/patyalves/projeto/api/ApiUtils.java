package com.projeto.patyalves.projeto.api;

/**
 * Created by logonrm on 25/07/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://www.mocky.io";

    public static UserAPI getUserAPI() {
        return RetrofitClient.getClient(BASE_URL).create(UserAPI.class);
    }
}
