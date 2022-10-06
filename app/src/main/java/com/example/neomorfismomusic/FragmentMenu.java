package com.example.neomorfismomusic;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import javax.annotation.Nullable;

public class FragmentMenu extends Fragment {

    public View onCreate(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        return v;
    }
}
