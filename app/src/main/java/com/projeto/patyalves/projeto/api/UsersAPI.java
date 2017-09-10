package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.User;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by abceducation on 03/09/17.
 */

public interface UsersAPI {
    @GET("v2/5977bd381100004b11d89a6d")
    Call<User> getUser();
}
