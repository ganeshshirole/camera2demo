package com.example.android.camera2.video.java_fragments.video_player;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.camera2.video.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoPlayerFragment extends Fragment {

    private ExoPlayer player;
    private PlayerView playerView;
    private String videoPath;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve the File object
            VideoPlayerFragmentArgs args = VideoPlayerFragmentArgs.fromBundle(getArguments());
            videoPath = args.getVideoPath(); // Make sure your arguments are of type File
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.playerView);

        // Initialize ExoPlayer
        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);

        // Set up the media item
        Uri videoUri = Uri.parse(videoPath); // Assuming mParam1 is the video URL or URI
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}