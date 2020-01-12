package dev.onekode.adronhomes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class FilterActivity extends AppCompatActivity implements RootInterface {
    // Dynamic Props
    private String rooms;
    private String propertyType;
    // Views
    private ChipGroup chipGroup;
    private MaterialButton continueFilterBtn;
    private RadioGroup propertyRadioGroup;
    private View previousCheckedChip;
    private Group roomsGroup;

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
        roomsGroup = findViewById(R.id.filter_activity__roomsGroup);
        propertyRadioGroup = findViewById(R.id.filter_activity_radio_group);
    }

    private void setupListeners() {
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                Chip checkedChip = findViewById(checkedId);
                if (previousCheckedChip != null && previousCheckedChip.getId() != checkedId)
                    chipInActive((Chip) previousCheckedChip); // Reset the previous Chip
                chipActive(checkedChip); // Set the current Chip as Active
                previousCheckedChip = checkedChip; // Assign the variable to the current chip

                rooms = (String) checkedChip.getText();
            }
        });

        propertyRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            MaterialRadioButton radioButton = findViewById(i);
            propertyType = radioButton.getText().toString();
        });

        continueFilterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, FilterResultActivity.class);
            intent.putExtra("rooms", rooms);
            intent.putExtra("propType", propertyType);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    private void chipActive(@NonNull Chip chip) {
        chip.setChipBackgroundColor(ContextCompat.getColorStateList(this, R.color.darkGrey));
        chip.setTextColor(ContextCompat.getColor(this, R.color.white));

        if (chip.getText().toString().equalsIgnoreCase(getString(R.string.filter_activity_chip_5_plus)))
            setVisibility(roomsGroup, true); // Display the input
    }

    private void chipInActive(@NonNull Chip chip) {
        chip.setChipBackgroundColor(ContextCompat.getColorStateList(this, R.color.transparent));
        chip.setTextColor(ContextCompat.getColor(this, R.color.black));

        if (chip.getText().toString().equalsIgnoreCase(getString(R.string.filter_activity_chip_5_plus)))
            setVisibility(roomsGroup, false); // Display the input
    }
}
