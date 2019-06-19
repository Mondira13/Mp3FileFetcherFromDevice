package com.example.mymusicplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicActivity extends AppCompatActivity {

    TextView mSongName;
    TextView mArtist;
    CircleImageView circleSmallImage;
    CircleImageView songIcon;
    boolean isPlay = false;

    RotateAnimation animation;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    String title;
    String artist;
    String rawFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        initializeView();
        title = getIntent().getStringExtra("title");
        artist = getIntent().getStringExtra("artist");
        rawFile = getIntent().getStringExtra("rawFile");

        mSongName.setText(title);
        mArtist.setText(artist);

        Uri uri = Uri.parse(rawFile.toString());
        mediaPlayer =MediaPlayer.create(this, uri);

        circleSmallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlay) {
                    circleSmallImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                    isPlay = true;
                    mediaPlayer.start();
                    animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(1000);
                    animation.setRepeatCount(Animation.INFINITE);
                    animation.setInterpolator(new LinearInterpolator());
                    songIcon.setAnimation(animation);
                    songIcon.startAnimation(animation);
                } else {
                    circleSmallImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_filled_black_24dp));
                    isPlay = false;
                    mediaPlayer.pause();
                    animation.cancel();
                }
            }
        });


    }

    private void initializeView() {
        circleSmallImage = findViewById(R.id.circleSmallImage);
        mSongName = findViewById(R.id.songName);
        mArtist = findViewById(R.id.artist);
        songIcon = findViewById(R.id.songIcon);
        seekBar = findViewById(R.id.seekbar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}
