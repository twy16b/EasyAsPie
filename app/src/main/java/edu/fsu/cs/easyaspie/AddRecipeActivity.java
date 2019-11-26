package edu.fsu.cs.easyaspie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Recipe");
        setContentView(R.layout.activity_add_recipe);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add_ingredient:
                break;
            case R.id.button_add_step:
                break;
            case R.id.button_accept:
                break;
            case R.id.button_cancel:
                this.finish();
                break;
        }
    }
}
