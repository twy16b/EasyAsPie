package edu.fsu.cs.easyaspie;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TimerStepFragment extends Fragment {
    private Fragment fragment;
    public TextView stepText;
    Button startTimerButton;
    String recipeName;
    int recipeID;
    int stepNumber;
    int seconds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_timer_step, container, false);
        stepText = v.findViewById(R.id.text_step);

        //Get Arguments from RecipeSteps activity
        Bundle arguments = getArguments();
        seconds = arguments.getInt("seconds");
        recipeName = arguments.getString("recipeName");
        recipeID = arguments.getInt("recipeID",0);
        stepNumber = arguments.getInt("stepNumber",0);
        stepText.setText(arguments.getString("newStep") + " for " + seconds + " seconds");

        //Set button to start timer service
        startTimerButton = (Button) v.findViewById(R.id.button_timer);
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startTimerIntent = new Intent(getActivity(), StartTimerService.class);
                Bundle bundle = new Bundle();
                bundle.putLong("time", seconds);
                bundle.putString("recipeName", recipeName);
                bundle.putInt("recipeID", recipeID);
                bundle.putInt("stepNumber", stepNumber);
                startTimerIntent.putExtras(bundle);
                getActivity().startService(startTimerIntent);
            }
        });

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
