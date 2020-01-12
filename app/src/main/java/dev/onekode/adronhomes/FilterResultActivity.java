package dev.onekode.adronhomes;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.onekode.adronhomes.Adapters.Recyclers.ApartmentAdapter;
import dev.onekode.adronhomes.Observers.ApartmentViewModel;

public class FilterResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApartmentAdapter adapter;
    private String rooms, propType;

    // Views
    private TextView propTypeTextView, priceRangeTextView, roomsTextView;
    private View previousSelectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);

        rooms = getIntent().getStringExtra("rooms");
        propType = getIntent().getStringExtra("propType");

        findViews();
        setupListeners();
        adapter = new ApartmentAdapter();
        putExtras();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.filter_result_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        propTypeTextView = findViewById(R.id.filter_result_activity_property_type_textView);
        priceRangeTextView = findViewById(R.id.filter_result_activity_price_range_textView);
        roomsTextView = findViewById(R.id.filter_result_activity_no_rooms_textView);
        recyclerView = findViewById(R.id.filter_result_activity_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
    }

    private void setupListeners() {
        ApartmentViewModel viewModel = ViewModelProviders.of(this).get(ApartmentViewModel.class);
        viewModel.all().observe(this, apartments -> {
            adapter.setApartments(apartments);
            recyclerView.setAdapter(adapter);
        });
        propTypeTextView.setOnClickListener(this::toggleButton);
        priceRangeTextView.setOnClickListener(this::toggleButton);
        roomsTextView.setOnClickListener(this::toggleButton);
    }

    private void toggleButton(View aView) {
        if (previousSelectedView != null)
            previousSelectedView.setSelected(false);
        // Note: I created a drawable selector -> main_activity_btn_selector
        // This drawable is incharge of toggling the selected and unselected drawables
        // If you still don't get it, ask & i'll explain better
        aView.setSelected(!aView.isSelected());
        previousSelectedView = aView;
    }

    private void putExtras() {
        propTypeTextView.setText(propType != null && !propType.equals("") ? propType : getString(R.string.filter_activity_flat_string));
        roomsTextView.setText(rooms != null && !propType.equals("") ? rooms : getString(R.string.filter_activity_chip_3));
    }
}
