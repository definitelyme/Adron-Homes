package dev.onekode.adronhomes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {
    private RoundedImageView userImageView;
    private ConstraintLayout buyButton;
    private ConstraintLayout sellButton;
    private ConstraintLayout rentButton;
    private AutoCompleteTextView locationSpinner;
    private MaterialButton creatorButton, filterButton;
    private View selectedButton;

    @Override
    @com.google.android.material.imageview.ExperimentalImageView
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        userImageView = findViewById(R.id.main_activity_user_imageView);
        buyButton = findViewById(R.id.main_activity_buy_button);
        sellButton = findViewById(R.id.main_activity_sell_button);
        rentButton = findViewById(R.id.main_activity_rent_button);
        locationSpinner = findViewById(R.id.main_activity_location_spinner);
        creatorButton = findViewById(R.id.main_activity_creator_btn);
        filterButton = findViewById(R.id.main_activity_filter_btn);

        userImageView.bringToFront();

        setupListeners();
    }

    private void setupListeners() {
        buyButton.setOnClickListener(this::toggleButton);
        sellButton.setOnClickListener(this::toggleButton);
        rentButton.setOnClickListener(this::toggleButton);
        creatorButton.setOnClickListener(v -> {
            startActivity(new Intent(this, FilterActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void toggleButton(View aView) {
        if (selectedButton != null)
            selectedButton.setSelected(false);
        // Note: I created a drawable selector -> main_activity_btn_selector
        // This drawable is incharge of toggling the selected and unselected drawables
        // If you still don't get it, ask & i'll explain better
        aView.setSelected(!aView.isSelected());
        selectedButton = aView;
    }
}
