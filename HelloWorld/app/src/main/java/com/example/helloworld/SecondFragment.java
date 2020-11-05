package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<datamodel> dataholder;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder = new ArrayList<>();

        datamodel ob1 = new datamodel(R.drawable.blackpanther, "Black Panther", "WAKANDA FOREVER");
        dataholder.add(ob1);

        datamodel ob2 = new datamodel(R.drawable.bucin, "Bucin", "Film by Chandra Liow");
        dataholder.add(ob2);

        datamodel ob3 = new datamodel(R.drawable.captainmarvel, "Captain Marvel", "Film Captain Marvel");
        dataholder.add(ob3);

        datamodel ob4 = new datamodel(R.drawable.dilan, "Dilan 1990", "Film Dilan 1990");
        dataholder.add(ob4);

        datamodel ob5 = new datamodel(R.drawable.endgame, "Avengers End Game", "Some people move on, but not us");
        dataholder.add(ob5);

        datamodel ob6 = new datamodel(R.drawable.farfromhome, "Spiderman Far From home", "Film Spiderman Jauh dari Rumah");
        dataholder.add(ob6);

        datamodel ob7 = new datamodel(R.drawable.homecoming, "Spiderman Homecoming", "Film Spiderman Pulang ke Rumah");
        dataholder.add(ob7);

        datamodel ob8 = new datamodel(R.drawable.mulan, "Mulan", "Film adaptasi Animasi Disney Mulan");
        dataholder.add(ob8);

        datamodel ob9 = new datamodel(R.drawable.parasite, "Parasite", "Film Parasite");
        dataholder.add(ob9);

        datamodel ob10 = new datamodel(R.drawable.thorragnarok, "Thor Ragnarok", "Film Tentang Dewa Petir");
        dataholder.add(ob10);

        recyclerView.setAdapter(new myadapter(dataholder));
        return view;
    }
}