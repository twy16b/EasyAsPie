package edu.fsu.cs.easyaspie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class StandardStepFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: load step from database
        return inflater.inflate(R.layout.fragment_standard_step, container, false);
    }
}
