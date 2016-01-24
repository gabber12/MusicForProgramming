package mfp.gabber.com.musicforprogramming.helper;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gabber12 on 23/1/16.
 */
public class Utils {
    public static String jsonToStringFromAssetFolder(String fileName, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(fileName);

        byte[] data = new byte[file.available()];
        int dataRead = file.read(data);
        file.close();
        return new String(data);
    }
}
