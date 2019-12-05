package edu.fsu.cs.easyaspie;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

public class StandardStepFragment extends Fragment {
    private Fragment fragment;
    private TextView stepText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: load step from database
        final View v = inflater.inflate(R.layout.fragment_standard_step, container, false);
        stepText = v.findViewById(R.id.text_step);
        stepText.setText(getArguments().getString("newStep"));
        // detect swipes
        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                // right to left swipe detected

                                // change fragment with next fragment
                                // TODO: if (NEXT step exists) {
                                    Bundle arguments = new Bundle();
                                    String newStep = "Sample instruction";  // TODO: replace this with database query for NEXT step
                                    arguments.putString( "newStep" , newStep);
                                    /* TODO: if (instruction includes timer) {
                                        fragment = new TimerStepFragment();
                                        String seconds = "60";                       // TODO: replace this with database query for seconds
                                        arguments.putString( "seconds" , seconds);
                                    // }
                                    else { */
                                        fragment = new StandardStepFragment();
                                    //}

                                    fragment.setArguments(arguments);
                                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                        R.anim.slide_in_right, R.anim.slide_out_left);
                                    ft.replace(R.id.fragment_container, fragment, null).addToBackStack(null).commit();
                                // }
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                // left to right swipe detected
                                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0){
                                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                                }
                                /*
                                // TODO: if (PREVIOUS instruction exists) {
                                    // change fragment with next fragment
                                    TimerStepFragment fragment = new TimerStepFragment();
                                    Bundle arguments = new Bundle();
                                    String newInstruction = "Sample instruction";  // TODO: replace this with database query for PREVIOUS instruction
                                    arguments.putString( "newInstruction" , newInstruction);
                                    fragment.setArguments(arguments);
                                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment_container, fragment, null);
                                    ft.commit();
                                // }*/
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });
        // TODO: when this is completed, put a similar GestureDetector with setOnTouchListener in TimerStepFragment

        return v;
    }
}
