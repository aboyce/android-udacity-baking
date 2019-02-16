package uk.ab.baking.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

    private RecipeViewModel viewModel;

    private SimpleExoPlayer videoPlayer;

    @BindView(R.id.fragment_step_sepv_player)
    PlayerView videoPlayerView;

    @BindView(R.id.fragment_step_instructions_tv_short_description)
    TextView stepShortDescription;

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

        // TODO: Remove the hardcoded Url.
        String videoUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
        initialiseVideoPlayer(Uri.parse(videoUrl));
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
                stepShortDescription.setText(step.getShortDescription());
                stepDescription.setText(step.getDescription());
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

    private void initialiseVideoPlayer(Uri mediaUri) {
        if (videoPlayer != null) {
            Timber.d("The video player is already initialised.");
            return;
        }

        videoPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        videoPlayerView.setPlayer(videoPlayer);
        String userAgent = Util.getUserAgent(getContext(), StepFragment.class.getSimpleName());
        DefaultDataSourceFactory sourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(sourceFactory).createMediaSource(mediaUri);
        videoPlayer.prepare(mediaSource);
        videoPlayer.setPlayWhenReady(true);
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
