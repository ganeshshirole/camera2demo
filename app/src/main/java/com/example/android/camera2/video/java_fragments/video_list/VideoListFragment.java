package com.example.android.camera2.video.java_fragments.video_list;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.camera2.video.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment {

    private NavController navController;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) requireActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Video List");
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        }
        // Set up a click listener for the back button
        toolbar.setNavigationOnClickListener(v -> {
            navController.popBackStack();
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Get all video files from the specified path
        List<File> videoFiles = getAllVideosFromPath(requireContext());

        // Set adapter to the RecyclerView
        VideoAdapter videoAdapter = new VideoAdapter(requireContext(), videoFiles, this::onVideoItemClick);
        recyclerView.setAdapter(videoAdapter);
    }

    private void onVideoItemClick(File video) {
        // Handle item click event
//        Toast.makeText(getContext(), "Clicked: " + video.getName(), Toast.LENGTH_SHORT).show();
        // Navigate or perform actions as needed
        VideoListFragmentDirections.ActionVideoListFragmentToVideoPlayerFragment action =
                VideoListFragmentDirections.actionVideoListFragmentToVideoPlayerFragment(Uri.fromFile(video).toString());
        navController.navigate(action);
    }

    private List<File> getAllVideosFromPath(Context context) {
        List<File> videoFiles = new ArrayList<>();
        File dir = context.getExternalFilesDir(null);

        if (dir != null && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".mp4")) {
                        videoFiles.add(file);
                    }
                }
            }
        }

        return videoFiles;
    }

}