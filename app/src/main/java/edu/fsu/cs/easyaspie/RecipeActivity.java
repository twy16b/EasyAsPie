package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    ArrayList<ContentValues> IngredientsToInsert;
    ArrayList<String> RecipeSteps;
    ArrayList<ContentValues> StepsToInsert;

    FloatingActionButton fabLeft;
    FloatingActionButton fabRight;
    Button addIngredient;
    Button addStep;
    Button beginRecipe;

    RecipesProvider recipeProvider;
    LayoutInflater li;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    RecyclerView ingredientsRecyclerView;
    RecyclerView stepsRecyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ingredientsRecyclerView =  findViewById(R.id.recycler_view_ingredients);
        stepsRecyclerView =  findViewById(R.id.recycler_view_steps);
        EditRecipeName = findViewById(R.id.edit_name);
        addIngredient = findViewById(R.id.button_add_ingredient);
        addStep = findViewById(R.id.button_add_step);
        beginRecipe = findViewById(R.id.button_begin_recipe);
        recipeProvider = new RecipesProvider();
        fabRight = findViewById(R.id.fab_right);
        fabLeft = findViewById(R.id.fab_left);

        RecipeIngredients = new ArrayList<>();
        IngredientsToInsert = new ArrayList<>();
        RecipeSteps = new ArrayList<>();
        StepsToInsert = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        IngredientsAdapter mIngredientsAdapter = new IngredientsAdapter(this, RecipeIngredients);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(),
                layoutManager.getOrientation()));
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        layoutManager = new LinearLayoutManager(this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        mIngredientsAdapter = new IngredientsAdapter(this, RecipeSteps);
        stepsRecyclerView.addItemDecoration(new DividerItemDecoration(stepsRecyclerView.getContext(),
                layoutManager.getOrientation()));
        stepsRecyclerView.setAdapter(mIngredientsAdapter);


        Intent intent = getIntent();
        recipeID = intent.getIntExtra("recipeID", 0);
        if (recipeID == 0) {
            //No recipe from intent, new recipe mode
            recipeID = RecipesProvider.RecipesCount+1;
            startEditMode();
        }
        else{
            startDisplayMode();
        }
        fabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(
                        RecipeActivity.this);
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete this recipe?")
                        .setCancelable(false)
                        .setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String selection = "_ID = ?";
                                String[] selectionArgs = {""+recipeID};
                                recipeProvider.delete(
                                        RecipesProvider.RecipesURI,
                                        selection,
                                        selectionArgs);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void startEditMode() {
        setTitle("Edit Recipe");
        beginRecipe.setVisibility(beginRecipe.GONE);
        EditRecipeName.setVisibility(EditRecipeName.VISIBLE);
        addIngredient.setVisibility(addIngredient.VISIBLE);
        addStep.setVisibility(addStep.VISIBLE);

        EditRecipeName.setText(RecipeName);

        fabRight.setImageResource(android.R.drawable.ic_menu_save);
        fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditRecipeName.getText().toString().isEmpty()) {
                    EditRecipeName.setError("This field can not be blank");
                }
                else {
                    if (recipeID <= RecipesProvider.RecipesCount) {
                        alertDialogBuilder = new AlertDialog.Builder(
                                RecipeActivity.this);
                        alertDialogBuilder
                                .setMessage("Would you like to update this recipe?")
                                .setCancelable(false)
                                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String newRecipeName = EditRecipeName.getText().toString();
                                        if (newRecipeName != RecipeName) {
                                            ContentValues updateRecipe = new ContentValues();
                                            updateRecipe.put("name", newRecipeName);
                                            String selection = "name = ?";
                                            String[] selectionArgs = {RecipeName};
                                            recipeProvider.update(
                                                    RecipesProvider.RecipesURI,
                                                    updateRecipe,
                                                    selection,
                                                    selectionArgs);
                                            RecipeName = newRecipeName;
                                        }
                                        for (int i = 0; i < IngredientsToInsert.size(); ++i) {
                                            ContentValues insertIngredient = IngredientsToInsert.get(i);
                                            recipeProvider.insert(RecipesProvider.IngredientsURI, insertIngredient);
                                        }
                                        for (int i = 0; i < StepsToInsert.size(); ++i) {
                                            ContentValues insertStep = StepsToInsert.get(i);
                                            recipeProvider.insert(RecipesProvider.StepsURI, insertStep);
                                        }
                                        startDisplayMode();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else {
                        ContentValues newRecipe = new ContentValues();
                        newRecipe.put("name", EditRecipeName.getText().toString());
                        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);
                        for (int i = 0; i < IngredientsToInsert.size(); ++i) {
                            ContentValues insertIngredient = IngredientsToInsert.get(i);
                            recipeProvider.insert(RecipesProvider.IngredientsURI, insertIngredient);
                        }
                        for (int i = 0; i < StepsToInsert.size(); ++i) {
                            ContentValues insertStep = StepsToInsert.get(i);
                            recipeProvider.insert(RecipesProvider.StepsURI, insertStep);
                        }
                        startDisplayMode();
                    }
                }
            }
        });
    }

    public void startDisplayMode() {
        setTitle(EditRecipeName.getText().toString() + " Recipe");

        beginRecipe.setVisibility(beginRecipe.VISIBLE);
        addIngredient.setVisibility(addIngredient.GONE);
        addStep.setVisibility(addStep.GONE);
        EditRecipeName.setVisibility(EditRecipeName.GONE);
        fabRight.setImageResource(android.R.drawable.ic_menu_edit);

        //Display Recipe Information
        layoutManager = new LinearLayoutManager(this);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        IngredientsAdapter mIngredientsAdapter = new IngredientsAdapter(this, RecipeIngredients);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(), layoutManager.getOrientation()));
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        layoutManager = new LinearLayoutManager(this);
        stepsRecyclerView.setLayoutManager(layoutManager);
        mIngredientsAdapter = new IngredientsAdapter(this, RecipeSteps);
        stepsRecyclerView.addItemDecoration(new DividerItemDecoration(stepsRecyclerView.getContext(), layoutManager.getOrientation()));
        stepsRecyclerView.setAdapter(mIngredientsAdapter);

        //Display Recipe Information

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
        setTitle(RecipeName + " Recipe");

        selection = "recipeID = ?";
        String [] selectionArgs2 = { "" + recipeID };
        myCursor = recipeProvider.query(
                RecipesProvider.IngredientsURI,
                null,
                selection,
                selectionArgs2,
                "_ID");
        myCursor.moveToFirst();
        RecipeIngredients.clear();
        for(int i = 0; i < myCursor.getCount(); ++i) {
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
        RecipeSteps.clear();
        for(int i = 0; i < myCursor.getCount(); ++i) {
            RecipeSteps.add(myCursor.getString(1) + " for " + myCursor.getString(2) + " seconds");
            myCursor.moveToNext();
        }

        fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditMode();
            }
        });
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
                                    String newIngredient = quantityInput+" "+unitInput+" "+ingredientInput;
                                    ContentValues newIngredientContent = new ContentValues();
                                    newIngredientContent.put("ingredient", newIngredient);
                                    newIngredientContent.put("recipeID", recipeID);
                                    IngredientsToInsert.add(newIngredientContent);
                                    RecipeIngredients.add(newIngredient);
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
                                    String stepDirections = editStep.getText().toString();
                                    int totalSeconds = Integer.parseInt(hourSpinner.getSelectedItem().toString()) * 60 * 60
                                            + Integer.parseInt(minuteSpinner.getSelectedItem().toString()) * 60
                                            + Integer.parseInt(secondSpinner.getSelectedItem().toString());
                                    ContentValues newStep = new ContentValues();
                                    newStep.put("directions", stepDirections);
                                    newStep.put("time", totalSeconds);
                                    newStep.put("recipeID", recipeID);
                                    StepsToInsert.add(newStep);
                                    RecipeSteps.add(stepDirections + " for " + totalSeconds + " seconds");
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
                if (RecipeIngredients.isEmpty() || RecipeSteps.isEmpty()) {
                    Snackbar.make(v, "Need at least one step and one ingredient before " +
                            "beginning activity_recipe.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Intent myIntent = new Intent(RecipeActivity.this, RecipeSteps.class);
                    myIntent.putExtra("recipeID", recipeID);
                    RecipeActivity.this.startActivity(myIntent);
                }
                break;
        }
    }
}