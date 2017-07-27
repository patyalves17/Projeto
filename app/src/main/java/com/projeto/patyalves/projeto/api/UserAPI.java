package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.User;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by logonrm on 25/07/2017.
 */

public interface UserAPI {

   // @GET("/v2/58b9b1740f0000b614f09d2f")
    @GET("/v2/58b9b1740f0000b614f09d2f")
    Observable<List<User>> getUsers();
}
