package edu.fsu.cs.easyaspie;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity implements RecipesAdapter.ItemClickListener {

    RecipesAdapter mRecipesAdapter;
    RecipesProvider recipeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Recipes");
        setContentView(R.layout.activity_recipes_list);
        recipeProvider = new RecipesProvider();
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                RecipeListActivity.this.startActivity(myIntent);
            }
        });

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
        Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
        intent.putExtra("recipeID", position+1);
        startActivity(intent);
    }
}
