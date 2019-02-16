package uk.ab.baking.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import uk.ab.baking.R;
import uk.ab.baking.viewmodels.RecipeViewModel;

public class StepFragment extends Fragment implements Player.EventListener {

    private static final boolean START_VIDEO_WHEN_READY = false;

    private RecipeViewModel viewModel;

    private SimpleExoPlayer videoPlayer;

    @BindView(R.id.fragment_step_cl_video)
    ConstraintLayout videoPlayerContainer;

    @BindView(R.id.fragment_step_sepv_player)
    PlayerView videoPlayerView;

    @Nullable
    @BindView(R.id.fragment_step_instructions_tv_short_description)
    TextView stepShortDescription;

    @Nullable
    @BindView(R.id.fragment_step_instructions_tv_description)
    TextView stepDescription;

    public StepFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate has been called");
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView has been called");
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, rootView);
        setupViewModelEvents();
        loadVideoPlayer();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseVideoPlayer();
    }

    private void setupViewModelEvents() {
        // Update the view when the step changes.
        viewModel.getClickedOnStep().observe(this, step -> {
            Timber.d("ViewModel step has been updated.");
            if (step != null) {
                if (stepDescription != null) {
                    stepDescription.setText(step.getDescription());
                }
                if (stepShortDescription != null) {
                    stepShortDescription.setText(step.getShortDescription());
                }
                if (step.getVideoUrl() != null) {
                    loadContentVideoPlayer(step.getVideoUrl());
                    Timber.v("Video to be loaded '" + step.getVideoUrl() + "'.");
                } else {
                    Timber.w("There is no video provided for step '" + step.getShortDescription() + "'.");
                }
            } else {
                Timber.w("ViewModel step was null.");
            }
        });
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        long currentPosition = videoPlayer.getCurrentPosition();
        if((playbackState == Player.STATE_READY) && playWhenReady){
            Timber.d("onPlayerStateChanged: Playing, current position '" + currentPosition + "'.");
        } else if((playbackState == Player.STATE_READY)){
            Timber.d("onPlayerStateChanged: Paused, current position '" + currentPosition + "'.");
        }
    }

    private void loadVideoPlayer() {
        if (videoPlayer != null) {
            Timber.d("The video player is already loaded.");
            return;
        }
        videoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        videoPlayerView.setPlayer(videoPlayer);
        // We want to keep the aspect ratio to ensure that the video looks as it should.
        videoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        videoPlayer.setPlayWhenReady(START_VIDEO_WHEN_READY);
    }

    private void loadContentVideoPlayer(String videoUriRaw) {
        if (videoPlayer == null) {
            Timber.w("The video player is null, make sure you call loadVideoPlayer() first.");
            return;
        }

        if (videoUriRaw == null || videoUriRaw.equals("")) {
            // If there is nothing to display, hide the player.
            videoPlayerContainer.setVisibility(View.GONE);
            Timber.w("The raw video uri is null or empty, hiding the video player.");
            return;
        }

        Uri videoUri = Uri.parse(videoUriRaw);
        // If there is something to display, show the player and load the content.
        videoPlayerContainer.setVisibility(View.VISIBLE);
        String userAgent = Util.getUserAgent(getContext(), StepFragment.class.getSimpleName());
        DefaultDataSourceFactory sourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(sourceFactory).createMediaSource(videoUri);
        videoPlayer.prepare(mediaSource);
    }

    private void releaseVideoPlayer() {
        Timber.i("releaseVideoPlayer()");
        if (videoPlayer != null) {
            videoPlayer.stop();
            videoPlayer.release();
            videoPlayer = null;
        }
    }
}
