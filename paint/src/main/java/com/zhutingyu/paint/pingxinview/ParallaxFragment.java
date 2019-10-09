package com.zhutingyu.paint.pingxinview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Quiet Zhu
 * @date 2019-10-08
 * @description
 */
public class ParallaxFragment extends Fragment {
    private List<View> parallaxViews = new ArrayList<View>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        int layoutId = args.getInt("layoutId");
        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater, getActivity(), this);
        return parallaxLayoutInflater.inflate(layoutId, null);
    }

    public List<View> getParallaxViews() {
        return parallaxViews;
    }

}
