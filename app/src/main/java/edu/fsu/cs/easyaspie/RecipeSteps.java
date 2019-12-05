package edu.fsu.cs.easyaspie;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeSteps extends FragmentActivity {
    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // leave fragments, return to previous activity
                finish();
            }
        });
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            Bundle arguments = new Bundle();
            String newStep = "First instruction";  // TODO: replace this with database query for next step
            arguments.putString( "newStep" , newStep);
            /* TODO: if (step includes timer) {
            fragment = new TimerStepFragment();
            String seconds = "60";                       // TODO: replace this with database query for seconds
            arguments.putString( "seconds" , seconds);
            }
            else { */
                fragment = new StandardStepFragment();
            //}

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
        }
    }

}
