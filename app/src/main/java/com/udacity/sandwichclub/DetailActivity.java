package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mMainNameTextView;
    private TextView mAKATextView;
    private TextView mOriginLabelTextView;
    private TextView mOriginNameTextView;
    private TextView mIngredientsLabelTextView;
    private TextView mIngredientsNamesTextView;
    private TextView mDescriptionLabelTextView;
    private TextView mDescriptionContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mMainNameTextView = findViewById(R.id.mainName_tv);
        mAKATextView = findViewById(R.id.also_known_label_tv);
        mOriginLabelTextView = findViewById(R.id.origin_label_tv);
        mOriginNameTextView = findViewById(R.id.origin_name_tv);
        mIngredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
        mIngredientsNamesTextView = findViewById(R.id.ingredients_name_tv);
        mDescriptionLabelTextView = findViewById(R.id.description_label_tv);
        mDescriptionContentTextView = findViewById(R.id.description_content_tv);

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        mMainNameTextView.setText(sandwich.getMainName());

        if(sandwich.getAlsoKnownAs().size() > 0){
            for(String name : sandwich.getAlsoKnownAs()) {
                mAKATextView.append(" " + name + " ");
            }
        }else {
            // If there's no additional names, remove the TextView
            mAKATextView.setVisibility(View.GONE);
        }

        if(!sandwich.getPlaceOfOrigin().equals("")) {
            mOriginNameTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            // If there's no Origin, remove the Origin Textviews
            mOriginLabelTextView.setVisibility(View.GONE);
            mOriginNameTextView.setVisibility(View.GONE);
        }

        for(String ingredient : sandwich.getIngredients()) {
            mIngredientsNamesTextView.append((ingredient) + "\n");
        }

        mDescriptionContentTextView.setText(sandwich.getDescription());

    }
}