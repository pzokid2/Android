package com.example.musicapp_daolv.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp_daolv.MainActivity;
import com.example.musicapp_daolv.R;
import com.example.musicapp_daolv.model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMusic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMusic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvName, tvTimeStart, tvTimeEnd, tvSinger;
    private SeekBar SB;
    private ImageButton btnPlay, btnBack, btnNext, btnReturn;
    private ImageView imCd;
    private ActionBar actionBar;

    private ArrayList<Song> arrayListSong;
    private MediaPlayer mp;
    private int position, totalSong, count;
    private ObjectAnimator animation;

    public FragmentMusic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMusic.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMusic newInstance(String param1, String param2) {
        FragmentMusic fragment = new FragmentMusic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Get tham so duoc truyen vao
        position = getArguments().getInt("positionInList");
        arrayListSong = (ArrayList<Song>) getArguments().getSerializable("listSong");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        // Khoi tao cac nut
        tvName = view.findViewById(R.id.tvName);
        tvSinger = view.findViewById(R.id.tvSinger);
        tvTimeStart = view.findViewById(R.id.tvTimeStart);
        tvTimeEnd = view.findViewById(R.id.tvTimeEnd);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        btnReturn = view.findViewById(R.id.btnreturn);
        SB = view.findViewById(R.id.sb);
        imCd = view.findViewById(R.id.cd);
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Trình phát nhạc");

        mp = new MediaPlayer();
        totalSong = arrayListSong.size();
        // set animation
        animation = ObjectAnimator.ofFloat(imCd, "rotation", 0, 360);
        animation.setDuration(15000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ObjectAnimator.RESTART);

        play();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    animation.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }
                else{
                    mp.start();
                    animation.resume();
                    btnPlay.setImageResource(R.drawable.pause);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                position++;
                if(position > arrayListSong.size() - 1)
                {
                    position = 0;
                }
                if(mp.isPlaying())
                {
                    mp.stop();
                }
                play();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                position--;
                if(position < 0)
                {
                    //position = arrayListSong.size() - 1;
                    position = 0;
                }
                if(mp.isPlaying())
                {
                    mp.stop();
                }
                play();
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                actionBar.setTitle("Bài hát cá nhân");
                getParentFragmentManager().popBackStack();
            }
        });
        SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(SB.getProgress());
            }
        });

        return view;
    }

    private void mediaPlayer()
    {
        mp = MediaPlayer.create(getActivity(), Uri.parse(arrayListSong.get(position).getsPath()));
        tvName.setText(arrayListSong.get(position).getsTenBai());
        tvSinger.setText(arrayListSong.get(position).getsCaSi());
        if(arrayListSong.get(position).getbAnh() == null)
        {
            imCd.setImageResource(R.drawable.cd);
        }
        else
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(arrayListSong.get(position).getbAnh(), 0, arrayListSong.get(position).getbAnh().length);
            imCd.setImageBitmap(Bitmap.createScaledBitmap(bmp, 700, 700, false));
        }
    }

    private void setTimeEnd()
    {
        // set tong thoi gian cua bai hat tu mili giay sang phut giay
        SimpleDateFormat phutGiay = new SimpleDateFormat("mm:ss");
        tvTimeEnd.setText(phutGiay.format(mp.getDuration()));
        // set tong seek bar
        SB.setMax(mp.getDuration());
    }

    private void updateTimeSong()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat phutGiay = new SimpleDateFormat("mm:ss");
                tvTimeStart.setText(phutGiay.format(mp.getCurrentPosition()));
                SB.setProgress(mp.getCurrentPosition());
                // kiem tra het bai thi next
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mP) {
                        count++;
                        if(count == totalSong)
                        {
                            mp.stop();
                            animation.pause();
                        }
                        else
                        {
                            position++;
                            if(position > arrayListSong.size() - 1)
                            {
                                position = 0;
                            }
                            play();
                        }
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void play()
    {
        mediaPlayer();
        setTimeEnd();
        mp.start();
        btnPlay.setImageResource(R.drawable.pause);
        animation.start();
        updateTimeSong();
    }
}