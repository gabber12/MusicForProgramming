package mfp.gabber.com.musicforprogramming.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mfp.gabber.com.musicforprogramming.pojos.Track;

/**
 * Created by gabber12 on 23/01/16.
 */
public class TrackStore {
    private static TrackStore instance;
    private List<Track> tracks;
    private Context context;

    public static TrackStore getInstance(Context context) {
        if (instance == null) {
            instance = new TrackStore();
            instance.context = context;
            instance.tracks = new ArrayList<>();
        }
        return instance;
    }

    public void init() {

        try {
            String json = Utils.jsonToStringFromAssetFolder("mfp.json", context);
            Type listType = new TypeToken<ArrayList<Track>>() {
            }.getType();
            List<Track> tracksList = new Gson().fromJson(json, listType);
            tracksList.size();
            tracks.addAll(tracksList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Track> getTrackList() {
        return tracks;
    }


}
