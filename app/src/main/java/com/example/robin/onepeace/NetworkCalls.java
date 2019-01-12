package com.example.robin.onepeace;

import com.example.robin.onepeace.ResponseModel.RecepieResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface NetworkCalls {
    @GET("receipes/{receipeName}/")
    Observable<RecepieResponse> fetchReceipes(@Path("receipeName") String id);
}
