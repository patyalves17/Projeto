package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.Local;
import com.projeto.patyalves.projeto.model.ResponseLocal;
import com.projeto.patyalves.projeto.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by abceducation on 08/09/17.
 */

public interface LocalsAPI {
    @GET("v2/5977bd381100004b11d89a6d")
    Call<User> getUser();

   // @GET("locais/lista")
    //Call<ResponseLocal>getLocais();

//    @GET("locais/lista")
//    Observable<List<Local>> getLocais();

    @GET("locais/lista")
    Call<List<Local>> getLocais();
}
