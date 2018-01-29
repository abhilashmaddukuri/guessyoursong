package com.example.circleseek.Service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.example.circleseek.Utils.SongsCache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by welcome on 11/13/17.
 */

public class SongsCollectionService extends IntentService {


    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    final String MEDIA_PATH_EXTERNAL_SAMSUNG = Environment.getExternalStorageDirectory().getParent() + "/extSdCard";
    final String MEDIA_PATH_EXTERNAL_SAMSUNG_ALT = Environment.getExternalStorageDirectory().getParent() + "/external_sd";
    final String MEDIA_PATH_EXTERNAL = System.getenv("SECONDARY_STORAGE");
    final String MEDIA_PATH_EXTERNAL_SAMSUNG_ALT2 = Environment.getExternalStorageDirectory().getParent() + "/sdcard0";

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> latestSongsList = new ArrayList<HashMap<String, String>>();
    private String mp3Pattern = ".mp3";

    public SongsCollectionService() {
        super("SongsCollectionService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            getPlayList();
        } catch (Exception e) {
        }
    }


    public ArrayList<HashMap<String, String>> getPlayList() {

        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.getName().equals("WhatsApp") || file.getName().equals("recordings") ||
                            file.getName().equalsIgnoreCase("Android")) {
                        continue;
                    }

                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }

        if (MEDIA_PATH_EXTERNAL != null) {
            File home = new File(MEDIA_PATH_EXTERNAL);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {

                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }


        if (MEDIA_PATH_EXTERNAL_SAMSUNG != null) {
            File home = new File(MEDIA_PATH_EXTERNAL_SAMSUNG);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        if (MEDIA_PATH_EXTERNAL_SAMSUNG_ALT != null) {
            File home = new File(MEDIA_PATH_EXTERNAL_SAMSUNG_ALT);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }

        if (MEDIA_PATH_EXTERNAL_SAMSUNG_ALT2 != null) {
            File home = new File(MEDIA_PATH_EXTERNAL_SAMSUNG_ALT2);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        if (songsList != null & songsList.size() < 5) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            songsList.add(songMap);
            SongsCache.getInstance().setSongsList(songsList);
            SongsCache.getInstance().setIsSongsServiceCompleted(true);
            return songsList;
        } else {
            SongsCache.getInstance().setSongsList(songsList);
            SongsCache.getInstance().setIsSongsServiceCompleted(true);
            return songsList;
        }
    }


    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.getName().equals("WhatsApp") || file.getName().equals("recordings") ||
                            file.getName().equals("android")) {
                        continue;
                    }
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }


    private void addSongToList(File song) {
        if (song.getName().endsWith(mp3Pattern) && isPassedLengthCheck(song)) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            songMap.put("songTitle",
                    song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());
            songsList.add(songMap);
        }
    }

    private boolean isPassedLengthCheck(File song) {
        Uri uri = Uri.parse(song.getAbsolutePath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getApplicationContext(), uri);
        String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int millSecond = Integer.parseInt(durationStr);
        if (millSecond > 50000) {
            return true;
        }
        return false;
    }
}
