package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.User;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by logonrm on 25/07/2017.
 */

public interface UserAPI {

    @GET("/v2/5977c5671100001112d89a72")
    Observable<List<User>> getUsers();
}
