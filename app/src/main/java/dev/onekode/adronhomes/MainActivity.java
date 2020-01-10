package dev.onekode.adronhomes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import dev.onekode.adronhomes.Models.Apartment;
import dev.onekode.adronhomes.Models.Rooms;

public class MainActivity extends AppCompatActivity implements RootInterface {
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

        findViews();
        setupListeners();
//        generateFakeData();
    }

    private void findViews() {
        userImageView = findViewById(R.id.main_activity_user_imageView);
        buyButton = findViewById(R.id.main_activity_buy_button);
        sellButton = findViewById(R.id.main_activity_sell_button);
        rentButton = findViewById(R.id.main_activity_rent_button);
        locationSpinner = findViewById(R.id.main_activity_location_spinner);
        creatorButton = findViewById(R.id.main_activity_creator_btn);
        filterButton = findViewById(R.id.main_activity_filter_btn);
        userImageView.bringToFront();
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

    private void generateFakeData() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        TENURE_TYPE[] cats = {TENURE_TYPE.SALE, TENURE_TYPE.LEASE, TENURE_TYPE.RENT};
        String[] names = {"Adiba Suites", "Concord Lounge", "Little Africa", "Lekki Gardens", "Rose Gardens", "Ajebo Estate", "Amen Villa"};
        String[] locations = {"Ikoyi Lagos", "Gariki Abuja", "Idumota, Lagos", "Lekki, Lagos", "Ikeja, Lagos", "Owerri, Imo", "Woolwich, London"};
        int total = 7;

        Log.i(TAG, "generateFakeData: Generating Data..........");

        for (int i = 0; i < total; i++) {
            DocumentReference documentReference = database.collection("apartments").document();

            Rooms rooms = new Rooms.Builder()
                    .setId(String.valueOf(Math.round(Math.random() * 1024)))
                    .setBathrooms(String.valueOf((int) (Math.floor(Math.random() * 5) + 1)))
                    .setBedrooms(String.valueOf((int) (Math.floor(Math.random() * 5) + 1)))
                    .setKitchens(String.valueOf((int) (Math.floor(Math.random() * 5) + 1)))
                    .setLobbies(String.valueOf((int) (Math.floor(Math.random() * 5) + 1)))
                    .setParking_rooms("3")
                    .build();
            Apartment apartment = new Apartment.Builder()
                    .setId(documentReference.getId())
                    .setName(names[(int) (Math.floor(Math.random() * total - 1) + 1)])
                    .setPropertyType(PROPERTY_TYPE.HOUSE.toString())
                    .setTenureType(cats[(int) (Math.floor(Math.random() * 2) + 1)]
                            .toString())
                    .setRooms(rooms)
                    .setPrice(String.valueOf((int) (Math.random() * 1000000)))
                    .setLocation(locations[(int) (Math.floor(Math.random() * total - 1) + 1)])
                    .setRating(4.4)
                    .setDescription("Here's an unbeatable opportunity to get into the market at a stunning price. As you walk into this 2011 home, you will see all the original characters such as Epoxy coated Concrete floors (Radiant in-floor heat).")
                    .build();
            documentReference.set(apartment)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Document added!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(this, e -> {
                        Toast.makeText(this, "Oops! => " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
