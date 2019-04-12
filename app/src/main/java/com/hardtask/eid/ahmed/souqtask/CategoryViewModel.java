package com.hardtask.eid.ahmed.souqtask;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<List<Category>> categoryList;

    public LiveData<List<Category>> getCategories(String categoryId, String countryId) {

        if (categoryList == null) {
            categoryList = new MutableLiveData<List<Category>>();
            loadCategoriesData(categoryId, countryId);
        }

        return categoryList;
    }

    private void loadCategoriesData(String categoryId, String countryId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<List<Category>> call = api.getCategories(categoryId, countryId);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoryList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("viewModel", "onFailure: " + "Error");
            }
        });
    }
}
