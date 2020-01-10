package dev.onekode.adronhomes.Adapters.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import dev.onekode.adronhomes.Models.Apartment;
import dev.onekode.adronhomes.R;
import dev.onekode.adronhomes.RootInterface;

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.ApartmentHolder> implements RootInterface {
    private ArrayList<Apartment> apartments;

    @NonNull
    @Override
    public ApartmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_filter_result_activity, parent, false);
        return new ApartmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentHolder holder, int position) {
        Apartment apartment = apartments.get(holder.getAdapterPosition());
        holder.bind(apartment);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }

    public void setApartments(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
        notifyDataSetChanged();
    }

    class ApartmentHolder extends RecyclerView.ViewHolder {
        private ImageView propertyImage;
        private TextView tenureTextView, propertyName, propertyPrice, propertyLocation, propertyRating;

        ApartmentHolder(@NonNull View itemView) {
            super(itemView);
            propertyImage = itemView.findViewById(R.id.filter_property_imageView);
            tenureTextView = itemView.findViewById(R.id.recyclerView_tenureType_textView);
            propertyName = itemView.findViewById(R.id.filter_property_name_textView);
            propertyLocation = itemView.findViewById(R.id.filter_property_location_textView);
            propertyPrice = itemView.findViewById(R.id.filter_property_price_textView);
            propertyRating = itemView.findViewById(R.id.filter_property_rating_textView);

            itemView.setOnClickListener(v -> sendSnackbar(itemView, MAINTENANCE_TEXT));
        }

        void bind(Apartment apartment) {
            propertyImage.setClipToOutline(true);
            tenureTextView.setText(String.format(Locale.getDefault(), "FOR %s", apartment.getTenureType().toUpperCase()));
            propertyName.setText(apartment.getName());
            propertyLocation.setText(apartment.getLocation());
            propertyPrice.setText(String.format(Locale.getDefault(), "N%s", apartment.getPrice()));
            propertyRating.setText(String.format(Locale.getDefault(), "%s Reviews", apartment.getRating()));
        }
    }
}
