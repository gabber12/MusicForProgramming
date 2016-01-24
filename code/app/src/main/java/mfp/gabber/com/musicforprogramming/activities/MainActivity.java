package mfp.gabber.com.musicforprogramming.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;

import java.util.List;

import mfp.gabber.com.musicforprogramming.R;
import mfp.gabber.com.musicforprogramming.helper.MediaPlayerUtils;
import mfp.gabber.com.musicforprogramming.helper.TrackStore;
import mfp.gabber.com.musicforprogramming.pojos.Track;

public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl, MediaPlayer.OnPreparedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private FloatingActionButton fab;
    private List<Track> tracks;
    private TextView current_playing;
    private MediaController mediaController;
    private TracksAdapter adatper;
    private Handler handler = new Handler();

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        final CollapsingToolbarLayout mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                String mCollapsedTitle = "Programming Music";
                String mExpandedTitle = "";
                if (mCollapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(mCollapsingToolbar)) {
                    mCollapsingToolbar.setTitle(mCollapsedTitle);
                } else {
                    mCollapsingToolbar.setTitle(mExpandedTitle);
                }

            }
        });
        mediaPlayer = new MediaPlayer();
        fab = (FloatingActionButton) findViewById(R.id.fab);

        TrackStore.getInstance(getApplicationContext()).init();
        tracks = TrackStore.getInstance(getApplicationContext()).getTrackList();
        final ListView frame = (ListView) findViewById(R.id.tracks_frame);
        adatper = new TracksAdapter(tracks);
        frame.setAdapter(adatper);

        current_playing = (TextView) findViewById(R.id.current_playing_track_name);
        adatper.notifyDataSetChanged();
        frame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                current_playing.setText(tracks.get(position).getTrackName());
                MediaPlayerUtils.getInstance(MainActivity.this).playSong(position);
                View old_view = adatper.getSelectedView();
                adatper.setSelectedView(view);
                if (old_view != null)
                    ((TextView) old_view.findViewById(R.id.track_name)).setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                ((TextView) view.findViewById(R.id.track_name)).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color_selected));
                setFab(true);
            }
        });
        MediaPlayerUtils.getInstance(this).setTracks(tracks);
        mediaController = new MediaController(this);
        mediaPlayer.setOnPreparedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isPlaying = MediaPlayerUtils.getInstance(MainActivity.this).getPlaying();
                setFab(isPlaying);
                if (isPlaying)
                    MediaPlayerUtils.getInstance(MainActivity.this).pauseCurrentSong();
                else
                    MediaPlayerUtils.getInstance(MainActivity.this).playCurrentSong();
            }
        });
        current_playing.setText("--");
    }

    public void setFab(Boolean isPlaying) {
        Log.i(TAG, "Play status " + isPlaying);
        if (!isPlaying) {
            fab.setImageResource(R.drawable.ic_play_arrow_white_36dp);
        } else {
            fab.setImageResource(R.drawable.ic_pause_white_36dp);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 100;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.media_controls));
        mediaController.setEnabled(true);

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }

    public static class viewHolder {
        TextView tName;
        TextView sno;
    }

    public class TracksAdapter extends BaseAdapter {
        private List<Track> tracks;
        private View selectedView;

        public TracksAdapter(List<Track> tracks) {
            this.tracks = tracks;
        }

        public View getSelectedView() {
            return selectedView;
        }

        public void setSelectedView(View selectedView) {
            this.selectedView = selectedView;
        }

        @Override
        public int getCount() {
            if (tracks == null) return 0;
            return tracks.size();
        }

        @Override
        public Object getItem(int position) {
            return tracks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater li = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = li.inflate(R.layout.track_layout, null);
                viewHolder = new viewHolder();
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (viewHolder) convertView.getTag();
            }
            Track track = (Track) getItem(position);
            viewHolder.tName = (TextView) convertView.findViewById(R.id.track_name);
            viewHolder.sno = (TextView) convertView.findViewById(R.id.sno);
            viewHolder.tName.setText(track.getTrackName());
            viewHolder.sno.setText((position + 1) + "");
            return convertView;
        }
    }
}
