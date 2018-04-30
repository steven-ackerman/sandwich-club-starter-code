package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import java.util.List;


import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

/***
 * Resources and Credits:
 * String Builder Documentation:
 * https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
 *
 * Complete Help from Slack Threads:
 * https://gwgnanodegrees.slack.com/threads/convo/CACEDSDJP-1524935029.000037/
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /**Class Variables **/
    TextView mAlsoKnownAs;
    TextView mPlaceOfOrigin;
    TextView mDescription;
    TextView mIngredients;

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Sets the Description Texview for the sandwich object. Checks for empty & null.

        TextView descriptionTextView = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (description == null || description.equals("")) {
            description = getString(R.string.no_description);
        }else
        descriptionTextView.setText(description);

        //Sets the TexView, Checks for empty List & Creates
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        String ingredients;
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty()) {
            ingredients = getString(R.string.unknown_ingredients);
        } else {

            //Builds a string from the chars:
            StringBuilder ingredientString = new StringBuilder();
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredientString.append(ingredientsList.get(i));
                if (i != ingredientsList.size() - 1) {
                    ingredientString.append('\n');
                }//End if.
            }//End for loop.
            ingredients = ingredientString.toString();
        }
        //Sets Text with the ingedients String put together by String Builder.
        ingredientsTextView.setText(ingredients);

        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        //Null OR Empty Check.
        if (placeOfOrigin == null || placeOfOrigin.equals("")) {
            placeOfOrigin = getString(R.string.unknown_origin);
        }
        placeOfOriginTextView.setText(placeOfOrigin);

        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        String alsoKnownAs;
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.isEmpty()) {
            alsoKnownAs = getString(R.string.no_aliases);

        } else {
            StringBuilder akaBuilder = new StringBuilder();
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                akaBuilder.append(alsoKnownAsList.get(i));
                if (i != alsoKnownAsList.size() - 1)
                    akaBuilder.append('\n');
            }
            alsoKnownAs = akaBuilder.toString();
        }
        alsoKnownAsTextView.setText(alsoKnownAs);
    }

}
