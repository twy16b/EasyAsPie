package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RecipesProvider recipeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeProvider = new RecipesProvider();

        //Empty any existing data
        ClearAllData();

        //Load Example Data
        InsertPreMadeRecipes();
    }

    public void InsertPreMadeRecipes() {
        ContentValues newRecipe = new ContentValues();
        newRecipe.put("name", "Spaghetti");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        ContentValues newIngredient = new ContentValues();
        newIngredient.put("ingredient", "Spaghetti ingredient");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        ContentValues newStep = new ContentValues();
        newStep.put("directions", "Cook Pasta");
        newStep.put("time", "900");
        newStep.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newRecipe.put("name", "Chicken");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        newStep = new ContentValues();
        newStep.put("directions", "Cook chicken");
        newStep.put("time", "600");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newRecipe.put("name", "Salad");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        newRecipe.put("name", "Pie");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
        newRecipe.put("name", "Cake");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
    }

    public void ClearAllData() {
        recipeProvider.delete(RecipesProvider.RecipesURI, null, null);
        recipeProvider.delete(RecipesProvider.IngredientsURI, null, null);
        recipeProvider.delete(RecipesProvider.StepsURI, null, null);
        recipeProvider.RecipesCount = 0;
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
