package com.example.practicaltest.Models;

import com.google.gson.annotations.SerializedName;

public class UserDetailModel {

    @SerializedName("body")
    public String body;
    @SerializedName("title")
    public String title;
    @SerializedName("id")
    public int id;
    @SerializedName("userId")
    public int userId;
}
