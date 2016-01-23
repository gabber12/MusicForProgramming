package mfp.gabber.com.musicforprogramming.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mfp.gabber.com.musicforprogramming.R;
import mfp.gabber.com.musicforprogramming.pojos.Track;

/**
 * Created by gabber12 on 23/01/16.
 */
public class TrackListHelper {
    private Activity mActivity;
    private static TrackListHelper instance;
    public static TrackListHelper getInstance(Activity mActivity){
        if(instance == null) {
            instance = new TrackListHelper();
            instance.mActivity = mActivity;
        }
        return instance;
    }
    public void addTracksToFrame(LinearLayout frame, List<Track> tracks) {
        for(int i = 0; i <tracks.size(); i++) {
            Track track = tracks.get(i);
            final Integer trackNum = i;
            View childTrack = LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.track_layout, null);
            TextView trackName = (TextView)childTrack.findViewById(R.id.track_name);
            childTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            trackName.setText(track.getTrackName());
            frame.addView(childTrack);
        }
    }
    public void setSelected(Integer idx) {

    }
}
