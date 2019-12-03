package edu.fsu.cs.easyaspie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FinalStepFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: add step from database and configure make finish button end steps
        return inflater.inflate(R.layout.fragment_final_step, container, false);
    }
}
