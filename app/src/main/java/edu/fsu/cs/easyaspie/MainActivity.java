package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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

        //Empty any existing data for testing purposes
        ClearAllData();

        //Load Example Data
        InsertPreMadeRecipes();
    }

    public void InsertPreMadeRecipes() {

        //Create default recipes

        //Spaghetti recipe Begin
        ContentValues newRecipe = new ContentValues();
        newRecipe.put("name", "Simple Spaghetti");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);

        //Ingredient list for Spaghetti
        ContentValues newIngredient = new ContentValues();
        newIngredient.put("ingredient", "1 pound ground beef");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "3 cups water");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "18 fluid ounces tomato juice");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 (6 ounce) can tomato paste");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "2 tablespoons dried minced onion");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 1/2 teaspoons chili powder, or more to taste");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 teaspoon white sugar");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 teaspoon dried oregano, crushed");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 teaspoon garlic salt");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 teaspoon salt");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "7 ounces spaghetti");
        newIngredient.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);

        //Steps for Spaghetti
        ContentValues newStep = new ContentValues();
        newStep.put("directions", "Heat a large skillet over medium-high heat. Cook and stir beef in the hot skillet until browned and crumbly");
        newStep.put("time", "5");
        newStep.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Mix water, tomato juice, tomato paste, minced onion, chili powder, sugar, oregano, garlic salt, and salt into ground beef; cover skillet and bring to a boil. Reduce heat to medium-low and simmer beef mixture, stirring occasionally, until flavors have blended");
        newStep.put("time", "5");
        newStep.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Place spaghetti into beef mixture and stir to separate strands; cover skillet and simmer, stirring frequently, until spaghetti is tender");
        newStep.put("time", "1800");
        newStep.put("recipeID", "1");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        //End recipe for Spaghetti

        //Begin recipe for Chicken Parmesan
        newRecipe.put("name", "Chicken Parmesan");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);

        //Ingredients for Chicken Parmesan
        newIngredient.put("ingredient", "4 skinless, boneless chicken breast halves");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "Salt and freshly ground black pepper to taste");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "2 eggs");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 cup panko bread crumbs, or more as needed");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup grated Parmesan cheese");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "2 tablespoons all-purpose flour, or more if needed");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 cup olive oil for frying");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup prepared tomato sauce");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/4 cup fresh mozzarella, cut into small cubes");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/4 cup chopped fresh basil");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup grated provolone cheese");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/4 cup grated Parmesan cheese");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 tablespoon olive oil");
        newIngredient.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);

        //Steps for Chicken Parmesan
        newStep.put("directions", "Preheat an oven to 450 degrees F (230 degrees C).");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Place chicken breasts between two sheets of heavy plastic (resealable freezer bags work well) on a solid, level surface. Firmly pound chicken with the smooth side of a meat mallet to a thickness of 1/2-inch. Season chicken thoroughly with salt and pepper.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Beat eggs in a shallow bowl and set aside.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Mix bread crumbs and 1/2 cup Parmesan cheese in a separate bowl, set aside.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Place flour in a sifter or strainer; sprinkle over chicken breasts, evenly coating both sides.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Dip flour coated chicken breast in beaten eggs. Transfer breast to breadcrumb mixture, pressing the crumbs into both sides. Repeat for each breast. Set aside breaded chicken breasts for about 15 minutes.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Heat 1 cup olive oil in a large skillet on medium-high heat until it begins to shimmer. Cook chicken until golden, about 2 minutes on each side. The chicken will finish cooking in the oven.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Place chicken in a baking dish and top each breast with about 1/3 cup of tomato sauce. Layer each chicken breast with equal amounts of mozzarella cheese, fresh basil, and provolone cheese. Sprinkle 1 to 2 tablespoons of Parmesan cheese on top and drizzle with 1 tablespoon olive oil.");
        newStep.put("time", "0");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Bake in the preheated oven until cheese is browned and bubbly, and chicken breasts are no longer pink in the center. An instant-read thermometer inserted into the center should read at least 165 degrees F (74 degrees C).");
        newStep.put("time", "1080");
        newStep.put("recipeID", "2");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        //End recipe for Chicken Parmesan

        //Begin recipe for Caesar Salad Supreme
        newRecipe.put("name", "Caesar Salad Supreme");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);

        //Ingredients for Caesar Salad Supreme
        newIngredient.put("ingredient", "6 cloves garlic, peeled, divided");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "3/4 cup mayonnaise");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "5 anchovy fillets, minced");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "6 tablespoons grated Parmesan cheese, divided");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 teaspoon Worcestershire sauce");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 teaspoon Dijon mustard");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 tablespoon lemon juice, or more to taste");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "Salt to taste");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "Ground black pepper to taste");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/4 cup olive oil");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "4 cups day-old bread, cubed");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 head romaine lettuce, torn into bite-size pieces");
        newIngredient.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);

        //Steps for Caesar Salad Supreme
        newStep.put("directions", "Mince 3 cloves of garlic, and combine in a small bowl with mayonnaise, anchovies, 2 tablespoons of the Parmesan cheese, Worcestershire sauce, mustard, and lemon juice. Season to taste with salt and black pepper. Refrigerate until ready to use.");
        newStep.put("time", "0");
        newStep.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Heat oil in a large skillet over medium heat. Cut the remaining 3 cloves of garlic into quarters, and add to hot oil. Cook and stir until brown, and then remove garlic from pan. Add bread cubes to the hot oil. Cook, turning frequently, until lightly browned. Remove bread cubes from oil, and season with salt and pepper.");
        newStep.put("time", "0");
        newStep.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Place lettuce in a large bowl. Toss with dressing, remaining Parmesan cheese, and seasoned bread cubes.");
        newStep.put("time", "0");
        newStep.put("recipeID", "3");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        //End recipe for Caesar Salad Supreme

        //Start recipe for Apple Pie
        newRecipe.put("name", "Apple Pie");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);

        //Apple Pie ingredients
        newIngredient.put("ingredient", "1 recipe pastry for a 9 inch double crust pie");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup unsalted butter");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "3 tablespoons all-purpose flour");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/4 cup water");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup white sugar");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup packed brown sugar");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "8 Granny Smith apples - peeled, cored and sliced");
        newIngredient.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);

        //Apple Pie Steps
        newStep.put("directions", "Preheat oven to 425 degrees F (220 degrees C). Melt the butter in a saucepan. Stir in flour to form a paste. Add water, white sugar and brown sugar, and bring to a boil. Reduce temperature and let simmer.");
        newStep.put("time", "0");
        newStep.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Place the bottom crust in your pan. Fill with apples, mounded slightly. Cover with a lattice work crust. Gently pour the sugar and butter liquid over the crust. Pour slowly so that it does not run off.");
        newStep.put("time", "0");
        newStep.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Bake 15 minutes in the preheated oven. Reduce the temperature to 350 degrees F (175 degrees C), until apples are soft.");
        newStep.put("time", "2400");
        newStep.put("recipeID", "4");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        //End Recipe for Apple Pie

        //Start Recipe for Double Layer Pumpkin Cheesecake
        newRecipe.put("name", "Double Layer Pumpkin Cheesecake");
        recipeProvider.insert(RecipesProvider.RecipesURI, newRecipe);

        //Double later pumpkin cheesecake ingredients
        newIngredient.put("ingredient", "2 (8 ounce) packages cream cheese, softened");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup white sugar");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 teaspoon vanilla extract");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "2 eggs");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 (9 inch) prepared graham cracker crust");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup pumpkin puree");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 teaspoon ground cinnamon");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 pinch ground cloves");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1 pinch ground nutmeg");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);
        newIngredient.put("ingredient", "1/2 cup frozen whipped topping, thawed");
        newIngredient.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.IngredientsURI, newIngredient);

        //Double Layer Pumpkin Cheesecake Steps
        newStep.put("directions", "Preheat oven to 325 degrees F (165 degrees C).");
        newStep.put("time", "0");
        newStep.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "In a large bowl, combine cream cheese, sugar and vanilla. Beat until smooth. Blend in eggs one at a time. Remove 1 cup of batter and spread into bottom of crust; set aside.");
        newStep.put("time", "0");
        newStep.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Add pumpkin, cinnamon, cloves and nutmeg to the remaining batter and stir gently until well blended. Carefully spread over the batter in the crust.");
        newStep.put("time", "0");
        newStep.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Bake in preheated oven for 35 to 40 minutes, or until center is almost set.");
        newStep.put("time", "2100");
        newStep.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);
        newStep.put("directions", "Allow to cool, then refrigerate for 3 hours or overnight. Cover with whipped topping before serving.");
        newStep.put("time", "0");
        newStep.put("recipeID", "5");
        recipeProvider.insert(RecipesProvider.StepsURI, newStep);


    }

    public void ClearAllData() {
        recipeProvider.delete(RecipesProvider.RecipesURI, null, null);
        recipeProvider.delete(RecipesProvider.IngredientsURI, null, null);
        recipeProvider.delete(RecipesProvider.StepsURI, null, null);
        recipeProvider.RecipesCount = 0;
    }

    public void onClick(View v) {
        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
