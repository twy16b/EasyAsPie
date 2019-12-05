package edu.fsu.cs.easyaspie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecipeSteps extends FragmentActivity {
    private Fragment fragment;

    RecipesProvider recipeProvider;
    String recipeName;
    int recipeID;
    int stepNumber;
    ArrayList<String> RecipeSteps;
    int[] stepTimes;

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

        recipeProvider = new RecipesProvider();
        RecipeSteps = new ArrayList<>();

        //Import steps into array
        Intent intent = getIntent();
        recipeName = intent.getStringExtra("recipeName");
        recipeID = intent.getIntExtra("recipeID", 0);
        stepNumber = intent.getIntExtra("stepNumber",1);

        String selection = "recipeID = ?";
        String [] selectionArgs3 = { "" + recipeID };
        Cursor myCursor = recipeProvider.query(
                RecipesProvider.StepsURI,
                null,
                selection,
                selectionArgs3,
                "_ID");
        myCursor.moveToFirst();
        stepTimes = new int[myCursor.getCount()];
        for(int i = 0; i < myCursor.getCount(); ++i) {
            RecipeSteps.add(myCursor.getString(1));
            stepTimes[i] = myCursor.getInt(2);
            myCursor.moveToNext();
        }

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
            String newStep = RecipeSteps.get(stepNumber-1);
            arguments.putString( "newStep" , newStep);
            if(stepTimes[stepNumber-1] != 0) {
            fragment = new TimerStepFragment();
            int seconds = stepTimes[stepNumber-1];
            arguments.putInt( "seconds" , seconds);
            }
            else {
                fragment = new StandardStepFragment();
            }

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
        }
    }

}
