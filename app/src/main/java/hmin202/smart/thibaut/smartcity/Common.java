package hmin202.smart.thibaut.smartcity;

import hmin202.smart.thibaut.smartcity.Model.MyPlaces;
import hmin202.smart.thibaut.smartcity.Model.Results;
import hmin202.smart.thibaut.smartcity.Remote.IGoogleAPIService;
import hmin202.smart.thibaut.smartcity.Remote.RetrofitClient;

import hmin202.smart.thibaut.smartcity.Model.Results;
import retrofit2.Retrofit;

public class Common {

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";
    public static Results currentResult;

    public static IGoogleAPIService getGoogleAPIService(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
