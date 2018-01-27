package com.example.circleseek;

import android.media.MediaPlayer;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
    // SDCard Path
    //final String MEDIA_PATH = new String("/sdcard/");
    //final String MEDIA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    //final String MEDIA_PATH_WHATSAPP = Environment.getExternalStorageDirectory().getPath() + "/WhatsApp";
    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    //final String MEDIA_PATH_SONGS = Environment.getExternalStorageDirectory().getPath() + "/songs";
    //final String MEDIA_PATH_CINEMELODY = Environment.getExternalStorageDirectory().getPath() + "/CineMelody";
    //final String MEDIA_PATH_BLUETOOTH = Environment.getExternalStorageDirectory().getPath() + "/bluetooth";
    //final String MEDIA_PATH_EXTERNAL = getSDcardDirectoryPath() + "/";
    final String MEDIA_PATH_EXTERNAL_SAMSUNG = Environment.getExternalStorageDirectory().getParent() + "/extSdCard";
    final String MEDIA_PATH_EXTERNAL_SAMSUNG_ALT = Environment.getExternalStorageDirectory().getParent() + "/external_sd";
    final String MEDIA_PATH_EXTERNAL = System.getenv("SECONDARY_STORAGE");
    final String MEDIA_PATH_EXTERNAL_SAMSUNG_ALT2 = Environment.getExternalStorageDirectory().getParent() + "/sdcard0";

    private MediaPlayer mp;
    //private String songTitle_whatsapp;

    private long duration;

    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    //private ArrayList<HashMap<String, String>> songsList_whatsapp = new ArrayList<HashMap<String, String>>();
    private String mp3Pattern = ".mp3";

    //private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    // Constructor
    public SongsManager() {

    }


    private String getSDcardDirectoryPath() {
        return System.getenv("SECONDARY_STORAGE");
    }

    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     */
    public ArrayList<HashMap<String, String>> getPlayList() {
        //File home = new File(MEDIA_PATH);


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
        if (songsList.size() < 5) {
            // Toast.makeText(SongsManager.this, "Congrats entered into force stop", Toast.LENGTH_SHORT).show();
            HashMap<String, String> songMap = new HashMap<String, String>();
            songMap.put("songTitle", "songTitle");
            songMap.put("songPath", "songPath");

            songsList.add(songMap);
            return songsList;
        } else {

            return songsList;
        }
        // return songs list array


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
                    if (file.getName().equalsIgnoreCase("Bluetooth") || file.getName().equalsIgnoreCase("Downloads")) {
                        System.out.println(file.getName());
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
        if (song.getName().endsWith(mp3Pattern)) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            songMap.put("songTitle",
                    song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());
            songsList.add(songMap);
        }
    }


}
