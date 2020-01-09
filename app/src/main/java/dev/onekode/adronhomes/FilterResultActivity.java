package dev.onekode.adronhomes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FilterResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);

        findViews();
        setupListeners();
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.filter_result_activity_toolbar);
//        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back_black));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupListeners() {
        //
    }
}
