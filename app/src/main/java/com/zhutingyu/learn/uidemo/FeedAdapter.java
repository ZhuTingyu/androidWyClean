package com.zhutingyu.learn.uidemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhutingyu.learn.R;

/**
 * @author Quiet Zhu
 * @date 2020-03-07
 * @description
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int i) {

        //nickname
        feedHolder.mTvNickname.setText("NetEase " + i);
    }


    @Override
    public int getItemCount() {
        return 100;
    }

    public static class FeedHolder extends RecyclerView.ViewHolder{

        ImageView mIvAvatar;
        ImageView mIvContent;
        TextView mTvNickname;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
        }
    }

}

