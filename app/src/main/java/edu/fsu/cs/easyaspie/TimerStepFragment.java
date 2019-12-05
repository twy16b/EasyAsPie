package edu.fsu.cs.easyaspie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
    String stepDirections;
    int recipeID;
    int stepNumber;
    int seconds;
    RecipesProvider recipeProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_timer_step, container, false);
        stepText = v.findViewById(R.id.text_step);
        recipeProvider = new RecipesProvider();

        //Get Arguments from RecipeSteps activity
        Bundle arguments = getArguments();
        recipeName = arguments.getString("recipeName");
        recipeID = arguments.getInt("recipeID",0);
        stepNumber = arguments.getInt("stepNumber",0);

        String selection = "recipeID = ?";
        String [] selectionArgs = { "" + recipeID };
        final Cursor myCursor = recipeProvider.query(
                RecipesProvider.StepsURI,
                null,
                selection,
                selectionArgs,
                "_ID");
        myCursor.moveToPosition(stepNumber-1);
        stepDirections = myCursor.getString(1);
        stepText.setText(stepDirections);
        seconds = myCursor.getInt(2);

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
                                if(stepNumber < myCursor.getCount()) {
                                    myCursor.moveToNext();
                                    Bundle arguments = new Bundle();
                                    arguments.putString("recipeName", recipeName);
                                    arguments.putInt("recipeID", recipeID);
                                    arguments.putInt("stepNumber", stepNumber+1);

                                    int nextStepTime = myCursor.getInt(2);
                                    if(nextStepTime > 0) {
                                        fragment = new TimerStepFragment();
                                    }
                                    else {
                                        fragment = new StandardStepFragment();
                                    }

                                    fragment.setArguments(arguments);
                                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                            R.anim.slide_in_right, R.anim.slide_out_left);
                                    ft.replace(R.id.fragment_container, fragment, null).addToBackStack(null).commit();
                                }
                            }
                            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                // left to right swipe detected
                                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0){
                                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                                }
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
        return v;
    }
}
