package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_recipes:
                Intent myIntent = new Intent(MainActivity.this, RecipesActivity.class);
                MainActivity.this.startActivity(myIntent);
                break;
            case R.id.button_grocery:
                break;
            case R.id.button_times:
                break;
        }
    }
}
