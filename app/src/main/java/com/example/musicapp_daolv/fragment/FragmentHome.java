package com.example.musicapp_daolv.fragment;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicapp_daolv.R;
import com.example.musicapp_daolv.adapter.MusicHomeRcvAdapter;
import com.example.musicapp_daolv.model.Song;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    private RecyclerView songrcv;
    private ArrayList<Song> lSong;
    private MusicHomeRcvAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lSong = new ArrayList<>();
        songrcv = view.findViewById(R.id.songrcv);
        getOfflineMusic();
        adapter = new MusicHomeRcvAdapter(getActivity(), lSong, FragmentHome.this);
        songrcv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        songrcv.setAdapter(adapter);

        return view;
    }

    private void getOfflineMusic(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download";
        File file = new File(path);
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++)
        {
            String sFileName = files[i].getName();
            if(sFileName.endsWith(".mp3")){
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(path + "/" + sFileName);
                String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                String sCaSi = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                byte[] bAnh = retriever.getEmbeddedPicture();
                if(title == "" || title == null){
                    title = sFileName;
                    title = title.substring(0, sFileName.length() - 4);
                }
                if(sCaSi == null) {
                    sCaSi = "";
                }
                if(bAnh == null){
                    lSong.add(new Song(title, files[i].getAbsolutePath(), sCaSi));
                }
                else{
                    lSong.add(new Song(title, files[i].getAbsolutePath(), sCaSi, bAnh));
                }
            }
        }
    }
}