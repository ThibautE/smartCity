package hmin202.smart.thibaut.smartcity.Remote;

import hmin202.smart.thibaut.smartcity.Model.MyPlaces;
import hmin202.smart.thibaut.smartcity.Model.PlaceDetail;

import hmin202.smart.thibaut.smartcity.Model.MyPlaces;
import hmin202.smart.thibaut.smartcity.Model.PlaceDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearbyPlaces(@Url String url);

    @GET
    Call<PlaceDetail> getDetailPlace(@Url String url);
}
