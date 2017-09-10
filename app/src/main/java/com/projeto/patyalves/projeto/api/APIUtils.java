package com.projeto.patyalves.projeto.api;

/**
 * Created by abceducation on 03/09/17.
 */

public class APIUtils {
    private APIUtils() {}
    public static final String BASE_URL = "http://www.mocky.io";
    public static final String BASE_URL_servidor = "http://192.168.1.113:8084/api/";
 

    public static UsersAPI getUserAPI() {
        return RetrofitClient.getClient(BASE_URL).create(UsersAPI.class);
    }

    public static LocalsAPI getLocalsAPI() {
        return RetrofitClient.getClient(BASE_URL_servidor).create(LocalsAPI.class);
    }
}
