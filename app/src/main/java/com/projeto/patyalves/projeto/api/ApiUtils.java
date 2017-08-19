package com.projeto.patyalves.projeto.api;

/**
 * Created by logonrm on 25/07/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://192.168.1.122";

    public static LocalAPI getLocalAPI() {
        return RetrofitClient.getClient(BASE_URL).create(LocalAPI.class);
    }
}
