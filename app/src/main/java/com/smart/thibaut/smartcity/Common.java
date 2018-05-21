package com.smart.thibaut.smartcity;

import com.smart.thibaut.smartcity.Model.MyPlaces;
import com.smart.thibaut.smartcity.Model.Results;
import com.smart.thibaut.smartcity.Remote.IGoogleAPIService;
import com.smart.thibaut.smartcity.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common {

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";
    public static Results currentResult;

    public static IGoogleAPIService getGoogleAPIService(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
