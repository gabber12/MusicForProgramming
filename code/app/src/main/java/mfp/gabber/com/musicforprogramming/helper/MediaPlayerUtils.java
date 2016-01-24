package mfp.gabber.com.musicforprogramming.helper;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import mfp.gabber.com.musicforprogramming.activities.MainActivity;
import mfp.gabber.com.musicforprogramming.pojos.Track;

/**
 * Created by gabber12 on 23/01/16.
 */
public class MediaPlayerUtils {
    private static final String TAG = MediaPlayerUtils.class.getSimpleName();
    public static MediaPlayerUtils instance;
    public MediaPlayer mediaPlayer;
    private Integer currentPlaying;
    private List<Track> tracks;
    private Activity mActivity;
    private Boolean isPlaying;

    public static MediaPlayerUtils getInstance(Activity mActivity){
        if(instance == null) {
            instance = new MediaPlayerUtils();
            instance.mActivity = mActivity;
            instance.mediaPlayer = ((MainActivity) mActivity).getMediaPlayer();
            instance.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            instance.isPlaying =false;
        }
        return instance;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public void setCurrentPlaying(Integer currentPlaying) {
        this.currentPlaying = currentPlaying;
    }

    public void playCurrentSong() {
        isPlaying = true;
        notifyPlaying();
        mediaPlayer.start();
    }
    public void playSong(Integer i) {
        isPlaying = true;
        if (i.equals(getCurrentPlaying())) return;
        setCurrentPlaying(i);
        mediaPlayer.reset();
        notifyPlaying();
        String url = tracks.get(i).getTrackUrl();
        Uri myUri;
        myUri = Uri.parse(url)
                .buildUpon().build();
        try {
            Log.i(TAG, "Url Playing "+url);
            mediaPlayer.setDataSource(mActivity.getApplicationContext(), myUri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pauseCurrentSong() {
        isPlaying = false;
        notifyPause();
        mediaPlayer.pause();
    }
    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
    private void notifyPlaying() {
        ((MainActivity)mActivity).setFab(true);
    }
    private void notifyPause() {
        ((MainActivity)mActivity).setFab(false);

    }
}
