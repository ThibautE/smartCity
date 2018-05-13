package com.example.thibaut.smartcity.Remote;

import com.example.thibaut.smartcity.Model.MyPlaces;
import com.example.thibaut.smartcity.Model.PlaceDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearbyPlaces(@Url String url);

    @GET
    Call<PlaceDetail> getDetailPlace(@Url String url);
}
