package com.example.practicaltest.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.ColorSpace;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicaltest.Adapters.PostAdapter;
import com.example.practicaltest.Models.PostModel;
import com.example.practicaltest.R;
import com.example.practicaltest.Utils.APIClient;
import com.example.practicaltest.Utils.APIInterface;
import com.example.practicaltest.databinding.FragmentPostBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    FragmentPostBinding binding;
    ProgressDialog dialog;
    APIInterface apiInterface;
    PostAdapter adapter;
    private List<PostModel> dataList =null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        getPostDetail();
        return binding.getRoot();
    }

    private void getPostDetail() {

        dialog.show();
        if (isInternetAvailable()) {
            Call<List<PostModel>> call = apiInterface.getPosts();
            call.enqueue(new Callback<List<PostModel>>() {
                @Override
                public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            Log.d("RESPONSE", response.body().toString());
                            dialog.dismiss();
                            dataList  = response.body();

                            adapter = new PostAdapter(dataList, getActivity());
                            binding.rvPost.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                            binding.rvPost.setAdapter(adapter);
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<PostModel>> call, Throwable t) {
                    dialog.dismiss();
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            dialog.dismiss();
            Toast.makeText(getActivity(), "Internet is not connected", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }
}