package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.Local;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by logonrm on 25/07/2017.
 */

public interface LocalAPI {

   // @GET("/v2/58b9b1740f0000b614f09d2f")
    @GET("/matchLocals")
    Call<List<Local>> getLocais();
}
