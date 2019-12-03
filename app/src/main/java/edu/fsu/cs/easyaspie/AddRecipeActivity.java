package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    EditText RecipeName;

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
        setContentView(R.layout.activity_add_recipe);
        ingredientsRecyclerView =  findViewById(R.id.recycler_view_ingredients);
        stepsRecyclerView =  findViewById(R.id.recycler_view_steps);
        RecipeName = findViewById(R.id.edit_name);
        recipeProvider = new RecipesProvider();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RecipeName.getText().toString().isEmpty()) {
                    RecipeName.setError("This field can not be blank");
                }
                else {
                    // TODO: check if RecipeName is already in the database
                    // TODO: If it already is, show "Would you like to overwrite this recipe?" Dialog

                    ContentValues newRecipe = new ContentValues();
                    newRecipe.put("name", RecipeName.getText().toString());
                    newRecipe.put("ingredients", RecipeName.getText().toString() + " ingredients");
                    recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
                    finish();
                }
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add_ingredient:
                // display add ingredient dialog
                li = LayoutInflater.from(this);
                final View ingredientDialog = li.inflate(R.layout.add_ingredient_dialog, null);
                alertDialogBuilder = new AlertDialog.Builder(AddRecipeActivity.this);
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
                                    ingredientsRecyclerView.setVisibility(View.VISIBLE); // display recycler view
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
                li = LayoutInflater.from(AddRecipeActivity.this);
                final View stepDialog = li.inflate(R.layout.add_step_dialog, null);

                alertDialogBuilder = new AlertDialog.Builder(AddRecipeActivity.this);
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
                dataAdapter = new ArrayAdapter<>(AddRecipeActivity.this,
                        android.R.layout.simple_spinner_item, list24);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hourSpinner.setAdapter(dataAdapter);

                List<String> list60 = new ArrayList<>();
                for (int i = 0; i < 60; i++){                   // display numbers 0-59 as 2 digits
                    list60.add(String.format("%02d", i));
                }
                dataAdapter = new ArrayAdapter<>(AddRecipeActivity.this,
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
                        "beginning recipe.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //}
                if (RecipeName.getText().toString().isEmpty()) {
                    RecipeName.setError("This field can not be blank");
                }
                else {
                    Intent myIntent = new Intent(AddRecipeActivity.this, RecipeSteps.class);
                    AddRecipeActivity.this.startActivity(myIntent);
                }
                break;
        }
    }
}
