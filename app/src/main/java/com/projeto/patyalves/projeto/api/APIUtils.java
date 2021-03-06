package com.projeto.patyalves.projeto.api;

/**
 * Created by abceducation on 03/09/17.
 */

public class APIUtils {
    private APIUtils() {}
    public static final String BASE_URL = "http://www.mocky.io";
    public static final String BASE_URL_servidor = "http:/ec2-52-67-194-73.sa-east-1.compute.amazonaws.com:8080/api/";


    public static UsersAPI getUserAPI() {
        return RetrofitClient.getClient(BASE_URL).create(UsersAPI.class);
    }

    public static LocalsAPI getLocalsAPI() {
        return RetrofitClientLocal.getClient(BASE_URL_servidor).create(LocalsAPI.class);
    }
}
