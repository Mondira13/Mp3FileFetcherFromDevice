package com.example.mymusicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView musicListRecycler;
    ArrayList<Song> musicList;
    MusicListAdapter adapter;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicListRecycler = findViewById(R.id.musicList);
        musicList = new ArrayList<>();


        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getSongList();
            addRecyclerView(musicList);
        } else {
            if (checkPermissions()) {
                getSongList();
                addRecyclerView(musicList);
            } else {
                requestPermissionss();
            }
        }

    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            return false;
        }
        return true;
    }


    private void requestPermissionss() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    getSongList();
                    addRecyclerView(musicList);
                } else {
                    // permission denied
                    Toast.makeText(MainActivity.this, "Please grant the permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();

//        Uri fileUri = MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;
//        Uri imagesUri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        @SuppressLint("Recycle")
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            int rawFile = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int durationColum = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String track = musicCursor.getString(rawFile);
                String duration = musicCursor.getString(durationColum);
                musicList.add(new Song(thisId, thisTitle, thisArtist,track));
            }
            while (musicCursor.moveToNext());
        }
    }


    private void addRecyclerView(ArrayList<Song> musicList) {
        musicListRecycler.setHasFixedSize(true);
        musicListRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MusicListAdapter(this, musicList);
        musicListRecycler.setAdapter(adapter);
    }


}
