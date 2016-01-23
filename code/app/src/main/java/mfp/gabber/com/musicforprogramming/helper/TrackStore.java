package mfp.gabber.com.musicforprogramming.helper;

import java.util.ArrayList;
import java.util.List;

import mfp.gabber.com.musicforprogramming.pojos.Track;

/**
 * Created by gabber12 on 23/01/16.
 */
public class TrackStore {
    private static TrackStore instance;
    private List<Track> tracks;

    public static TrackStore getInstance() {
        if (instance == null) {
            instance = new TrackStore();
            instance.tracks = new ArrayList<Track>();
        }
        return instance;
    }

    public void init() {
        for (int i = 0; i < 15; i++) {
            Track track = new Track();
            track.setTrackName(i+" Michael Hicks");
            track.setTrackUrl("http://datashat.net/music_for_programming_27-michael_hicks.mp3");
            tracks.add(track);
        }
    }

    public List<Track> getTrackList() {
        return tracks;
    }


}
