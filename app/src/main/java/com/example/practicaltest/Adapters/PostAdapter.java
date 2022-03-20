package com.example.practicaltest.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaltest.Activities.UserDetailActivity;
import com.example.practicaltest.Models.PostModel;
import com.example.practicaltest.R;
import com.example.practicaltest.databinding.RowPostBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ItemViewHolder>{

    RowPostBinding mBinding;
    List<PostModel> dataList;
    Context context;

    public PostAdapter(List<PostModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_post,parent,false);
        return new ItemViewHolder(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mBinding.tvTitle.setText(dataList.get(position).getTitle());
        holder.mBinding.tvBody.setText(dataList.get(position).getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("userID", dataList.get(position).getUserId());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        RowPostBinding mBinding;
        public ItemViewHolder(@NonNull View itemView, RowPostBinding mBinding) {
            super(itemView);
            this.mBinding = mBinding;
        }
    }
}

