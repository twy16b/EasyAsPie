package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    RecipesProvider recipeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeProvider = new RecipesProvider();
        recipeProvider.delete(RecipesProvider.RecipesURI, null, null);
        recipeProvider.delete(RecipesProvider.StepsURI, null, null);
        ContentValues newRecipe = new ContentValues();
        newRecipe.put("name", "Spaghetti");
        newRecipe.put("ingredients", "Spaghetti ingredients");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        ContentValues newStep = new ContentValues();
        newStep.put("directions", "Cook Pasta");
        newStep.put("time", "15");
        newStep.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newRecipe.put("name", "Chicken");
        newRecipe.put("ingredients", "Chicken ingredients");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        newRecipe.put("name", "Salad");
        newRecipe.put("ingredients", "Salad ingredients");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        newRecipe.put("name", "Pie");
        newRecipe.put("ingredients", "Pie ingredients");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        newRecipe.put("name", "Cake");
        newRecipe.put("ingredients", "Cake ingredients");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_recipes:
                Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
                MainActivity.this.startActivity(myIntent);
                break;
            case R.id.button_grocery:
                break;
            case R.id.button_times:
                break;
        }
    }
}
