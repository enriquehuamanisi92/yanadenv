package com.example.yanadenv.data.remote;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://161.132.216.3/samaycov-api/";


    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}