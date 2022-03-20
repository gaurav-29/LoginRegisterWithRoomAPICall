package com.example.practicaltest.Utils;

import com.example.practicaltest.Models.PostModel;
import com.example.practicaltest.Models.UserDetailModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("posts")
    Call<List<PostModel>> getPosts();

    @GET("posts/{userId}")
    Call<UserDetailModel> getUserDetail(@Path("userId") int userId);
}
