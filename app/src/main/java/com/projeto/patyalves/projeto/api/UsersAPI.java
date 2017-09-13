package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.User;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by abceducation on 03/09/17.
 */

public interface UsersAPI {
    //@GET("v2/5185415ba171ea3a00704eed")
    @GET("v2/58b9b1740f0000b614f09d2f")
    Call<User> getUser();


}
