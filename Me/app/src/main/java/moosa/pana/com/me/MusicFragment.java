package moosa.pana.com.me;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import MusicBox.SongsAdaptor;
import MusicBox.SongsDatabase;
import MusicBox.SongsManager;
import MusicBox.Utilities;
import MusicBox.Variables;

public class MusicFragment extends Fragment implements View.OnClickListener {
    public ArrayList<HashMap<String, String>> songList = new ArrayList<>();
    private ImageView forwardButton, playPause, previousButton,refreshButton;
    private ListView songsListView;
    private OnFragmentInteractionListener mListener;
    private SongsDatabase database;
    private SongsAdaptor songsAdaptor;
    /////////////////////////////////////////////////
    private LinearLayout allMusic;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView currentSongTitle, currentSongAlbumArtist;
    private TextView totalDurationLabel;
    private TextView currentDurationLabel;
    private Handler mHandler;
    private SongsManager songsManager;
    private Utilities util;
    private int currentSongIndex = 0;
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

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(updateTimeTask);
        SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Variables.SHARED_SONG_INDEX, currentSongIndex).apply();


    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
        currentSongIndex = sharedPreferences.getInt(Variables.SHARED_SONG_INDEX, 0);
        playSong(currentSongIndex);
        updateProgressBar();
        mediaPlayer.pause();
        playPause.setImageResource(android.R.drawable.ic_media_play);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new SongsDatabase(getActivity());
        mediaPlayer = new MediaPlayer();
        mHandler = new Handler();
        songsManager = new SongsManager();
        util = new Utilities();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        songsListView = (ListView) view.findViewById(R.id.MusicListView);

        SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
        boolean dataCheck = sharedPreferences.getBoolean(Variables.SHARED_CHK_DB, false);
        if (dataCheck) {

            ArrayList<HashMap<String, String>> songsListData = new ArrayList<>();
            this.songList = database.getFilesFromDataBase();//songsManager.getPlaylist();
            for (int i = 0; i < songList.size(); i++) {
                HashMap<String, String> singleSong = songList.get(i);
                songsListData.add(singleSong);

            }
            refreshView(songsListData);

        } else {
            refreshPlaylist();
        }
        forwardButton = (ImageView) view.findViewById(R.id.forwardButton);
        previousButton = (ImageView) view.findViewById(R.id.previousButton);
        playPause = (ImageView) view.findViewById(R.id.playPauseButton);
        playPause.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playPause.setImageResource(android.R.drawable.ic_media_play);

            }
        });
        refreshButton= (ImageView) view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);
        currentSongTitle = (TextView) view.findViewById(R.id.songTitleCurrentlyPlaying);
        currentSongTitle.setMovementMethod(new ScrollingMovementMethod());
        totalDurationLabel = (TextView) view.findViewById(R.id.totalDurationOfSongCurrentlyPlaying);
        currentDurationLabel = (TextView) view.findViewById(R.id.currentDurationOfSongCurrentlyPlaying);
        currentSongAlbumArtist = (TextView) view.findViewById(R.id.textViewCurrentAlbumArtist);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        seekBar();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.playPauseButton):
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        playPause.setImageResource(android.R.drawable.ic_media_play);
                    }
                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                        playPause.setImageResource(android.R.drawable.ic_media_pause);
                    }
                }
                break;
            case (R.id.previousButton):
                if (currentSongIndex > 0) {
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    playSong(songList.size() - 1);
                    currentSongIndex = songList.size() - 1;
                }
                break;
            case (R.id.forwardButton):
                if (currentSongIndex < (songList.size() - 1)) {
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    playSong(0);
                    currentSongIndex = 0;
                }
                break;
            case (R.id.refreshButton):
                refreshPlaylist();
                break;
            default:
                break;
        }

    }

    private void refreshView(ArrayList<HashMap<String, String>> viewList) {
        Log.d("SONGSLIST", "SIZIE IS--->" + viewList.size());
        songsAdaptor = new SongsAdaptor(getActivity(), viewList);
        songsListView.setAdapter(songsAdaptor);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSong(position);


            }
        });
    }
    private void refreshPlaylist() {


        AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> ref = new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {
            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
                //AlertDialog.Builder builder = new AlertDialog.Builder(SongsView.this);
                //builder.setTitle("Alert");
                //builder.setMessage("Loading Data Please Wait...");
                //AlertDialog dialog = builder.create();
                //dialog.show();
                SongsManager songsManager = new SongsManager();
                ArrayList<HashMap<String, String>> songsListData = new ArrayList<>();
                songList = songsManager.getPlaylist();
                for (int i = 0; i < songList.size(); i++) {
                    HashMap<String, String> singleSong = songList.get(i);
                    songsListData.add(singleSong);
                }
                database.saveFilesToDataBase(songsListData);


                return songsListData;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String, String>> aVoid) {
                refreshView(aVoid);
                SharedPreferences sharedPreferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Variables.SHARED_CHK_DB, true);
                editor.commit();
                Utilities.toast(getActivity(), "Files Added to the List");
                //aVoid.dismiss();
            }
        };
        ref.execute();


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

    private void playSong(int songIndex) {
        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(songList.get(songIndex).get(Variables.hashMapsrc));
            mediaPlayer.prepare();
            mediaPlayer.start();
            /////Title///////
            String songTitle = songList.get(songIndex).get(Variables.hashMaptitle);
            currentSongTitle.setText(songTitle);
            currentSongAlbumArtist.setText("Album: " + songList.get(songIndex).get(Variables.hashMapAlbumTitle) + "\nArtist: " + songList.get(songIndex).get(Variables.hashMapAtrist));
            /////play to pause///
            playPause.setImageResource(android.R.drawable.ic_media_pause);
            ///progress Bar////
            seekBar.setProgress(0);
            seekBar.setMax(100);
            ///////////Updating Progress Bar////
            updateProgressBar();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (currentSongIndex < (songList.size() - 1)) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
    ////////////////////////////

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
