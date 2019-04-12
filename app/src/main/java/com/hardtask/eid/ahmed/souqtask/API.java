package com.hardtask.eid.ahmed.souqtask;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    String Base_URL = "http://souq.hardtask.co/app/app.asmx/";

    @GET("GetCategories")
    Call<List<Category>> getCategories(
            @Query("categoryId") String categoryId,
            @Query("countryId") String countryId
    );
}
