package example.moosa.com.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Player extends ActionBarActivity {
    private LinearLayout allMusic;
    private ImageView playPause, forwardButton, previousButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView totalSongs;
    private TextView currentSongTitle;
    private TextView totalDurationLabel;
    private TextView currentDurationLabel;
    private Handler mHandler;
    private SongsManager songsManager;
    private Utilities util;
    private int currentSongIndex = 0;
    private ArrayList<HashMap<String, String>> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playPause.setImageResource(R.drawable.play);

            }
        });
        mHandler = new Handler();
        songsManager = new SongsManager();
        util = new Utilities();
        forwardButton = (ImageView) findViewById(R.id.forwardButton);
        previousButton = (ImageView) findViewById(R.id.previousButton);
        songsList = songsManager.getPlaylist();
        totalSongs = (TextView) findViewById(R.id.totalSongsInAllMusic);
        currentSongTitle = (TextView) findViewById(R.id.songTitleCurrentlyPlaying);
        totalDurationLabel = (TextView) findViewById(R.id.totalDurationOfSongCurrentlyPlaying);
        currentDurationLabel = (TextView) findViewById(R.id.currentDurationOfSongCurrentlyPlaying);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        playPause = (ImageView) findViewById(R.id.playPauseButton);
         allMusic = (LinearLayout) findViewById(R.id.viewallmusic);

        viewAllMusic();
        playPause();
        forwardButton();
        previousButton();
        totalSongs();
        seekBar();

    }

    private void seekBar() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(updateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(updateTimeTask);
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = util.progressToTimer(seekBar.getProgress(), totalDuration);
                mediaPlayer.seekTo(currentPosition);
                updateProgressBar();
            }
        });
    }

    private void totalSongs() {
        totalSongs.setText(songsList.size() + " musics");
    }

    private void previousButton() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }
            }
        });
    }

    private void forwardButton() {
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex < (songsList.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    playSong(0);
                    currentSongIndex = 0;
                }
            }
        });
    }


    private void playPause() {
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        playPause.setImageResource(R.drawable.play);
                    }
                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                        playPause.setImageResource(R.drawable.pause);
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            currentSongIndex = data.getExtras().getInt(Variables.SONGINDEX);
            playSong(currentSongIndex);
        }
    }

    private void playSong(int songIndex) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(songsList.get(songIndex).get(Variables.hashMapsrc));
            mediaPlayer.prepare();
            mediaPlayer.start();
            /////Title///////
            String songTitle = songsList.get(songIndex).get(Variables.hashMaptitle);
            currentSongTitle.setText(songTitle);
            /////play to pause///
            playPause.setImageResource(R.drawable.pause);
            ///progress Bar////
            seekBar.setProgress(0);
            seekBar.setMax(100);
            ///////////Updating Progress Bar////
            updateProgressBar();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (currentSongIndex < (songsList.size() - 1)) {
                        playSong(currentSongIndex + 1);
                        currentSongIndex = currentSongIndex + 1;
                    } else {
                        playSong(0);
                        currentSongIndex = 0;
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateProgressBar() {
        mHandler.postDelayed(updateTimeTask, 100);
    }

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            currentDurationLabel.setText(util.milliSecondToTimer(currentDuration));
            totalDurationLabel.setText(util.milliSecondToTimer(totalDuration));
            int progress = (int) (util.getProgressPercentage(currentDuration, totalDuration));
            seekBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };


    private void viewAllMusic() {
        allMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SongsView.class);
                startActivityForResult(i, 100);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
}
