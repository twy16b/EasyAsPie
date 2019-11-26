package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddRecipeActivity extends AppCompatActivity {
    EditText RecipeName;

    RecipesProvider recipeProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Recipe");
        setContentView(R.layout.activity_add_recipe);
        RecipeName = findViewById(R.id.edit_name);
        recipeProvider = new RecipesProvider();
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add_ingredient:
                break;
            case R.id.button_add_step:
                break;
            case R.id.button_accept:
                ContentValues newRecipe = new ContentValues();
                newRecipe.put("name", RecipeName.getText().toString());
                newRecipe.put("ingredients", RecipeName.getText().toString() + " ingredients");
                recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
                finish();
                break;
            case R.id.button_cancel:
                finish();
                break;
        }
    }
}
