package com.example.musicapp_daolv.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp_daolv.R;
import com.example.musicapp_daolv.fragment.FragmentHome;
import com.example.musicapp_daolv.fragment.FragmentMusic;
import com.example.musicapp_daolv.model.Song;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicHomeRcvAdapter extends RecyclerView.Adapter<MusicHomeRcvAdapter.SongHolder> {

    private ArrayList<Song> lSong;
    private Activity activity;
    private Fragment fragment;

    public MusicHomeRcvAdapter(Activity activity, ArrayList<Song> lSong, Fragment fragment){
        this.lSong = lSong;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View v = activity.getLayoutInflater().inflate(R.layout.item_song,parent,false);
        return new SongHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        Song song = lSong.get(position);
        holder.name.setText(song.getsTenBai());
        if (song.getsCaSi() == ""){
            holder.singer.setText("Unknown");
        }
        else {
            holder.singer.setText(song.getsCaSi());
        }

        if(song.getbAnh() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(song.getbAnh(), 0, song.getbAnh().length);
            holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150, 150, false));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity,"Chọn bài",Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putInt("positionInList", position);
                bundle.putSerializable("listSong",lSong);
                FragmentMusic fragmentMusic = new FragmentMusic();
                fragmentMusic.setArguments(bundle);

                FragmentManager fragmentManager = fragment.getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("music");
                fragmentTransaction.replace(R.id.mainframelayout,fragmentMusic);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lSong.size();
    }

    class SongHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name,singer;
        public SongHolder(@NonNull View v) {
            super(v);
            imageView = v.findViewById(R.id.img);
            name = v.findViewById(R.id.songname);
            singer = v.findViewById(R.id.songsinger);
        }
    }
}