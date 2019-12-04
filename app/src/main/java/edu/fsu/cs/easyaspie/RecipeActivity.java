package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    final String TAG = "RecipeActivity";
    int recipeID;

    EditText EditRecipeName;
    String RecipeName;
    ArrayList<String> RecipeIngredients;
    ArrayList<String> RecipeSteps;

    Button addIngredient;
    Button addStep;

    RecipesProvider recipeProvider;
    LayoutInflater li;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    RecyclerView ingredientsRecyclerView;
    RecyclerView stepsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Recipe");
        setContentView(R.layout.activity_recipe);
        ingredientsRecyclerView =  findViewById(R.id.recycler_view_ingredients);
        stepsRecyclerView =  findViewById(R.id.recycler_view_steps);
        EditRecipeName = findViewById(R.id.edit_name);
        addIngredient = findViewById(R.id.button_add_ingredient);
        addStep = findViewById(R.id.button_add_step);
        recipeProvider = new RecipesProvider();
        FloatingActionButton fab = findViewById(R.id.fab);

        Intent intent = getIntent();
        recipeID = intent.getIntExtra("recipeID", 0);
        if (recipeID == 0) {
            //No recipe from intent, new recipe mode
            addIngredient.setVisibility(addIngredient.VISIBLE);
            addStep.setVisibility(addStep.VISIBLE);
            fab.setImageResource(android.R.drawable.ic_menu_save);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (EditRecipeName.getText().toString().isEmpty()) {
                        EditRecipeName.setError("This field can not be blank");
                    } else {
                        // TODO: check if RecipeName is already in the database
                        // TODO: If it already is, show "Would you like to overwrite this activity_recipe?" Dialog

                        ContentValues newRecipe = new ContentValues();
                        newRecipe.put("name", EditRecipeName.getText().toString());
                        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
                        finish();
                    }

                    Cursor myCursor = recipeProvider.query(
                            RecipesProvider.RecipesURI,
                            null,
                            null,
                            null,
                            null);
                    recipeID = myCursor.getCount();

                }
            });
        }
        else {
            //Display recipe from intent

            //Query Recipe Information
            String selection = "_ID = ?";
            String[] selectionArgs1 = { "" + recipeID };
            Cursor myCursor = recipeProvider.query(
                    RecipesProvider.RecipesURI,
                    null,
                    selection,
                    selectionArgs1,
                    "_ID");
            myCursor.moveToFirst();
            RecipeName = myCursor.getString(1);
            RecipeIngredients = new ArrayList<>();
            RecipeIngredients.add(myCursor.getString(1));

            selection = "recipeID = ?";
            String [] selectionArgs2 = { "" + recipeID };
            myCursor = recipeProvider.query(
                    RecipesProvider.IngredientsURI,
                    null,
                    selection,
                    selectionArgs2,
                    "_ID");
            myCursor.moveToFirst();
            RecipeIngredients = new ArrayList<>();
            for(int i = 0; i < myCursor.getCount(); ++i) {
                Log.d(TAG, "onCreate: Add ingredient " + myCursor.getString(1));
                RecipeIngredients.add(myCursor.getString(1));
                myCursor.moveToNext();
            }

            selection = "recipeID = ?";
            String [] selectionArgs3 = { "" + recipeID };
            myCursor = recipeProvider.query(
                    RecipesProvider.StepsURI,
                    null,
                    selection,
                    selectionArgs3,
                    "_ID");
            myCursor.moveToFirst();
            RecipeSteps = new ArrayList<>();
            for(int i = 0; i < myCursor.getCount(); ++i) {
                RecipeSteps.add(myCursor.getString(1));
                myCursor.moveToNext();
            }

            //Display Recipe Information
            EditRecipeName.setText(RecipeName);

            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecipesAdapter mRecipesAdapter = new RecipesAdapter(this, RecipeIngredients);
            ingredientsRecyclerView.setAdapter(mRecipesAdapter);

            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecipesAdapter = new RecipesAdapter(this, RecipeSteps);
            stepsRecyclerView.setAdapter(mRecipesAdapter);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Set to change the layout to edit mode
                    addIngredient.setVisibility(view.VISIBLE);
                    addStep.setVisibility(view.VISIBLE);
                }
            });
        }
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add_ingredient:
                // display add ingredient dialog
                li = LayoutInflater.from(this);
                final View ingredientDialog = li.inflate(R.layout.add_ingredient_dialog, null);
                alertDialogBuilder = new AlertDialog.Builder(RecipeActivity.this);
                alertDialogBuilder.setView(ingredientDialog);

                final EditText editQuantity = ingredientDialog.findViewById(R.id.edit_quantity);
                final Spinner unitSpinner = ingredientDialog.findViewById(R.id.unit_spinner);
                final EditText editIngredient = ingredientDialog.findViewById(R.id.edit_ingredient);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Accept", null)      // override in onShow
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.dismiss();
                                    }
                                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = (alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String quantityInput = editQuantity.getText().toString();
                                String unitInput = unitSpinner.getSelectedItem().toString();
                                String ingredientInput = editIngredient.getText().toString();
                                if (!quantityInput.isEmpty() && !ingredientInput.isEmpty()) {
                                    // TODO: add input to database and to StepsAdapter
                                    ContentValues newIngredient = new ContentValues();
                                    newIngredient.put("ingredient", quantityInput+" "+unitInput+" "+ingredientInput);
                                    newIngredient.put("recipeID", recipeID);
                                    recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
                                    alertDialog.dismiss();
                                }
                                if (quantityInput.isEmpty()) {
                                    editQuantity.setError("This field can not be blank");
                                }
                                if (ingredientInput.isEmpty()) {
                                    editIngredient.setError("This field can not be blank");
                                }
                            }
                        });
                    }
                });
                alertDialog.show();
                break;
            case R.id.button_add_step:
                // display add step dialog
                li = LayoutInflater.from(RecipeActivity.this);
                final View stepDialog = li.inflate(R.layout.add_step_dialog, null);

                alertDialogBuilder = new AlertDialog.Builder(RecipeActivity.this);
                alertDialogBuilder.setView(stepDialog);

                final EditText editStep = stepDialog.findViewById(R.id.edit_step);
                final Spinner hourSpinner = stepDialog.findViewById(R.id.hour_spinner);
                final Spinner minuteSpinner = stepDialog.findViewById(R.id.minute_spinner);
                final Spinner secondSpinner = stepDialog.findViewById(R.id.second_spinner);

                ArrayAdapter<String> dataAdapter;

                List<String> list24 = new ArrayList<>();
                for (int i = 0; i < 24; i++){
                    list24.add(String.format("%02d", i));       // display numbers 0-23 as 2 digits
                }
                dataAdapter = new ArrayAdapter<>(RecipeActivity.this,
                        android.R.layout.simple_spinner_item, list24);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hourSpinner.setAdapter(dataAdapter);

                List<String> list60 = new ArrayList<>();
                for (int i = 0; i < 60; i++){                   // display numbers 0-59 as 2 digits
                    list60.add(String.format("%02d", i));
                }
                dataAdapter = new ArrayAdapter<>(RecipeActivity.this,
                        android.R.layout.simple_spinner_item, list60);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                minuteSpinner.setAdapter(dataAdapter);
                secondSpinner.setAdapter(dataAdapter);

                // set dialog message
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Accept", null)
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button button = (alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String stepInput = editStep.getText().toString();
                                if (stepInput.isEmpty()) {
                                    editStep.setError("This field can not be blank");
                                }
                                else {
                                    // convert all time to seconds
                                    int totalSeconds = Integer.parseInt(hourSpinner.getSelectedItem().toString()) * 60 * 60
                                            + Integer.parseInt(minuteSpinner.getSelectedItem().toString()) * 60
                                            + Integer.parseInt(secondSpinner.getSelectedItem().toString());
                                    // TODO: add input to database and to IngredientsAdapter
                                    stepsRecyclerView.setVisibility(View.VISIBLE); // display recycler view
                                    alertDialog.dismiss();
                                }
                            }
                        });
                    }
                });
                alertDialog.show();
                break;
            case R.id.button_begin_recipe:
                //if (ingredients recyclerview is empty or steps recyclerview is empty) {
                Snackbar.make(v, "Need at least one step and one ingredient before " +
                        "beginning activity_recipe.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //}
                if (EditRecipeName.getText().toString().isEmpty()) {
                    EditRecipeName.setError("This field can not be blank");
                }
                else {
                    Intent myIntent = new Intent(RecipeActivity.this, RecipeSteps.class);
                    RecipeActivity.this.startActivity(myIntent);
                }
                break;
        }
    }
}
