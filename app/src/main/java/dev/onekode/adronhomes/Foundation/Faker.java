package dev.onekode.adronhomes.Foundation;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import dev.onekode.adronhomes.Models.Apartment;
import dev.onekode.adronhomes.Models.Rooms;
import dev.onekode.adronhomes.RootInterface;

public class Faker implements RootInterface {
    private static TENURE_TYPE[] cats = {TENURE_TYPE.SALE, TENURE_TYPE.LEASE, TENURE_TYPE.RENT};
    private static String[] names = {"Adiba Suites", "Concord Lounge", "Little Africa", "Lekki Gardens", "Rose Gardens", "Ajebo Estate", "Amen Villa"};
    private static String[] locations = {"Ikoyi Lagos", "Gariki Abuja", "Idumota, Lagos", "Lekki, Lagos", "Ikeja, Lagos", "Owerri, Imo", "Woolwich, London"};

    public Faker() {
    }

    public static void fakeLocations() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Map<String, String> data = new HashMap<>();
        for (String place : locations) {
            data.put(new Slugify.Builder().make(place), place);
        }
        database.collection("settings")
                .document("locations")
                .set(data, SetOptions.merge());
    }

    public static void makeFakeApartments(int quantity) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        int items_count = 7;

        for (int i = 0; i < quantity; i++) {
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
                    .setName(names[(int) (Math.floor(Math.random() * items_count - 1) + 1)])
                    .setPropertyType(PROPERTY_TYPE.HOUSE.toString())
                    .setTenureType(cats[(int) (Math.floor(Math.random() * 2) + 1)]
                            .toString())
                    .setRooms(rooms)
                    .setPrice(String.valueOf((int) (Math.random() * 1000000)))
                    .setLocation(locations[(int) (Math.floor(Math.random() * items_count - 1) + 1)])
                    .setRating(4.4)
                    .setDescription("Here's an unbeatable opportunity to get into the market at a stunning price. As you walk into this 2011 home, you will see all the original characters such as Epoxy coated Concrete floors (Radiant in-floor heat).")
                    .build();
            documentReference.set(apartment)
                    .addOnSuccessListener(aVoid -> Log.i(TAG, "makeFakeApartments: Document added!"));
        }
    }
}
