package edu.fsu.cs.easyaspie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TimerStepFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: load step and timer from database and start timer on button click
        return inflater.inflate(R.layout.fragment_timer_step, container, false);
    }
}
