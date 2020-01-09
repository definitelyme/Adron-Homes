package dev.onekode.adronhomes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterActivity extends AppCompatActivity {
    // Dynamic Props
    private String rooms;
    // Views
    private ChipGroup chipGroup;
    private MaterialButton continueFilterBtn;
    private View previousCheckedChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        findViews();
        setupListeners();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.filter_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chipGroup = findViewById(R.id.filter_activity_chip_group);
        continueFilterBtn = findViewById(R.id.filter_activity_continue_btn);
    }

    private void setupListeners() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip checkedChip = findViewById(checkedId);
            if (previousCheckedChip != null)
                chipInActive((Chip) previousCheckedChip); // Reset the previous Chip
            chipActive(checkedChip); // Set the current Chip as Active
            previousCheckedChip = checkedChip; // Assign the variable to the current chip

            rooms = (String) checkedChip.getText();
        });

        continueFilterBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, FilterResultActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void chipActive(@NonNull Chip chip) {
        chip.setChipBackgroundColor(ContextCompat.getColorStateList(this, R.color.darkGrey));
        chip.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void chipInActive(@NonNull Chip chip) {
        chip.setChipBackgroundColor(ContextCompat.getColorStateList(this, R.color.transparent));
        chip.setTextColor(ContextCompat.getColor(this, R.color.black));
    }
}
