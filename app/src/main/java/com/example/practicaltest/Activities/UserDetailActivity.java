package com.example.practicaltest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.practicaltest.Adapters.PostAdapter;
import com.example.practicaltest.Models.PostModel;
import com.example.practicaltest.Models.UserDetailModel;
import com.example.practicaltest.R;
import com.example.practicaltest.Utils.APIClient;
import com.example.practicaltest.Utils.APIInterface;
import com.example.practicaltest.databinding.ActivityUserDetailBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {

    ActivityUserDetailBinding binding;
    UserDetailActivity context = UserDetailActivity.this;
    ProgressDialog dialog;
    APIInterface apiInterface;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(context, R.layout.activity_user_detail);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        userID = getIntent().getIntExtra("userID", 0);

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        getUserDetail2();
    }

    private void getUserDetail2() {
        dialog.show();
        if (isInternetAvailable()) {
            Call<UserDetailModel> call = apiInterface.getUserDetail(userID);
            call.enqueue(new Callback<UserDetailModel>() {
                @Override
                public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Log.d("RESPONSE", response.body().toString());
                            dialog.dismiss();

                            UserDetailModel user = response.body();
                            binding.tvId.setText(String.valueOf(user.id));
                            binding.tvUserId.setText(String.valueOf(user.userId));
                            binding.tvTitle.setText(user.title);
                            binding.tvBody.setText(user.body);
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(context, "No success", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserDetailModel> call, Throwable t) {
                    dialog.dismiss();
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            dialog.dismiss();
            Toast.makeText(context, "Internet is not connected", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }
}