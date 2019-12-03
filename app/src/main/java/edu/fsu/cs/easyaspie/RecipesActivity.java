package edu.fsu.cs.easyaspie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.ItemClickListener {

    RecipesAdapter mRecipesAdapter;
    RecipesProvider recipeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Recipes");
        setContentView(R.layout.activity_recipes);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RecipesActivity.this, AddRecipeActivity.class);
                RecipesActivity.this.startActivity(myIntent);
            }
        });

        recipeProvider = new RecipesProvider();

        ArrayList<String> foodNames = new ArrayList<>();

        Cursor myCursor = recipeProvider.query(
                RecipesProvider.RecipesURI,
                null,
                null,
                null,
                null);

        myCursor.moveToFirst();

        for(int i = 0; i < myCursor.getCount(); ++i) {
            foodNames.add(myCursor.getString(1));
            myCursor.moveToNext();
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recipes_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipesAdapter = new RecipesAdapter(this, foodNames);
        mRecipesAdapter.setClickListener(this);
        recyclerView.setAdapter(mRecipesAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO: open AddRecipeActivity for the correct recipe
        Snackbar.make(view, "You clicked " + mRecipesAdapter.getItem(position) + " on row number " + position, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
