package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_not_interested)
                .error(R.drawable.ic_error_outline)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs().size() > 0) {
            alsoKnownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else {
            TextView alsoKnownAsLabel = findViewById(R.id.also_known_tv_label);
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAs.setVisibility(View.GONE);
        }
        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        TextView ingredients = findViewById(R.id.ingredients_tv);
        ingredients.setText(TextUtils.join(", ", sandwich.getIngredients()));
        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());
    }
}
