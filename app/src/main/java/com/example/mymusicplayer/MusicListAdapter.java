package com.example.mymusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private Activity activity;
    private ArrayList<Song> musicList;
    MediaPlayer mediaPlayer;

    public MusicListAdapter(Activity activity, ArrayList<Song> musicList) {
        this.activity = activity;
        this.musicList = musicList;
    }


    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(activity).inflate(R.layout.music_list_child, viewGroup, false);
        return new MusicListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int i) {
        Song song = musicList.get(i);
        holder.songName.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        onClickListener(holder,song);
    }

    private void onClickListener(MusicListViewHolder holder, final Song song) {
        holder.parentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,PlayMusicActivity.class);
                intent.putExtra("title",song.getTitle());
                intent.putExtra("artist",song.getArtist());
                intent.putExtra("rawFile",song.getRawFile());
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicListViewHolder extends RecyclerView.ViewHolder{

        TextView songName;
        TextView artist;
        LinearLayout parentItem;

        public MusicListViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artist = itemView.findViewById(R.id.artist);
            parentItem = itemView.findViewById(R.id.parentItem);
        }
    }

}
