package com.projeto.patyalves.projeto.api;

import com.projeto.patyalves.projeto.model.Local;
import com.projeto.patyalves.projeto.model.ResponseLocal;
import com.projeto.patyalves.projeto.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by abceducation on 08/09/17.
 */

public interface LocalsAPI {
    @GET("locais/lista")
    Call<List<Local>> getLocais();

    @GET("locais/{id}")
    Call<Local> getLocal(@Path("id") Long id);

    @GET("profile/{token}/{secret}/{idTwitter}")
    Call<User> getProfile(@Path("token") String token, @Path("secret") String secret, @Path("idTwitter") String idTwitter);

}
