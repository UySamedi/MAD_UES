package com.example.uesapp.Myapi;

import android.content.Context;
import android.util.Log;
import com.example.uesapp.Myapi.AuthTokenManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitInstance {
    private static volatile RetrofitInstance instance;
    private ApiInterface apiInterface;
    private static final String BASE_URL = "http://10.0.2.2:8000/";

    private RetrofitInstance(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String token = AuthTokenManager.getAuthToken(context);

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json");

                    if (token != null && !token.isEmpty()) {
                        String authToken = token.startsWith("Bearer ") ? token : "Bearer " + token;
                        requestBuilder.header("Authorization", authToken);
                        Log.d("RetrofitInstance", "Adding auth token to header");
                    }

                    return chain.proceed(requestBuilder.build());
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static RetrofitInstance getInstance(Context context) {
        if (instance == null) {
            synchronized (RetrofitInstance.class) {
                if (instance == null) {
                    instance = new RetrofitInstance(context);
                }
            }
        }
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}