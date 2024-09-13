package com.example.android.camera2.video.java_fragments.video_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.camera2.video.R;

import java.io.File;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<File> videoList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public VideoAdapter(Context context, List<File> videoList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.videoList = videoList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        File videoFile = videoList.get(position);
        holder.tvVideoName.setText(videoFile.getName());
        // Set an OnClickListener for the item view
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(videoFile));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvVideoName;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVideoName = itemView.findViewById(R.id.textView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(File video);
    }
}
