package edu.fsu.cs.easyaspie;

import android.content.Intent;
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

    RecipesAdapter adapter;

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
                /*Snackbar.make(view, "Add button clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        ArrayList<String> foodNames = new ArrayList<>();
        foodNames.add("Spaghetti");
        foodNames.add("Chicken");
        foodNames.add("Salad");
        foodNames.add("Pie");
        foodNames.add("Cake");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recipes_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipesAdapter(this, foodNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Snackbar.make(view, "You clicked " + adapter.getItem(position) + " on row number " + position, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
