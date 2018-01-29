package com.example.circleseek.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 1/29/2018.
 */

public class SongsCache {

    private static final String TAG = SongsCache.class.getSimpleName();
    private static SongsCache mHelper;

    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    public boolean isSongServiceCompleted = false;

    private SongsCache() {
    }

    public static SongsCache getInstance() {
        if (mHelper == null) {
            mHelper = new SongsCache();
        }
        return mHelper;
    }


    public ArrayList<HashMap<String, String>> getSongsList() {
        return songsList;
    }

    public void setSongsList(ArrayList<HashMap<String, String>> songsList1) {
        this.songsList = songsList1;
    }


    public boolean getIsSongsServiceCompleted() {
        return isSongServiceCompleted;
    }

    public void setIsSongsServiceCompleted(boolean booleanValue) {
        this.isSongServiceCompleted = booleanValue;

    }


}
