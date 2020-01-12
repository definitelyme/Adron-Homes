package dev.onekode.adronhomes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Rooms extends Model implements Parcelable {
    public static final Creator<Rooms> CREATOR = new Creator<Rooms>() {
        @Override
        public Rooms createFromParcel(Parcel in) {
            return new Rooms(in);
        }

        @Override
        public Rooms[] newArray(int size) {
            return new Rooms[size];
        }
    };

    private String id;
    private String lobbies;
    private String bedrooms;
    private String bathrooms;
    private String kitchens;
    private String parking_rooms;

    public Rooms() {
    }

    private Rooms(Builder builder) {
        this.id = builder.id;
        this.lobbies = builder.lobbies;
        this.bedrooms = builder.bedrooms;
        this.bathrooms = builder.bathrooms;
        this.kitchens = builder.kitchens;
        this.parking_rooms = builder.parking_rooms;
    }

    // Parcelable Impl
    protected Rooms(Parcel in) {
        id = in.readString();
        lobbies = in.readString();
        bedrooms = in.readString();
        bathrooms = in.readString();
        kitchens = in.readString();
        parking_rooms = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(lobbies);
        dest.writeString(bedrooms);
        dest.writeString(bathrooms);
        dest.writeString(kitchens);
        dest.writeString(parking_rooms);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getLobbies() {
        return lobbies;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public String getKitchens() {
        return kitchens;
    }

    public String getParking_rooms() {
        return parking_rooms;
    }

    public static class Builder {
        private String id;
        private String lobbies;
        private String bedrooms;
        private String bathrooms;
        private String kitchens;
        private String parking_rooms;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setLobbies(String lobbies) {
            this.lobbies = lobbies;
            return this;
        }

        public Builder setBedrooms(String bedrooms) {
            this.bedrooms = bedrooms;
            return this;
        }

        public Builder setBathrooms(String bathrooms) {
            this.bathrooms = bathrooms;
            return this;
        }

        public Builder setKitchens(String kitchens) {
            this.kitchens = kitchens;
            return this;
        }

        public Builder setParking_rooms(String parking_rooms) {
            this.parking_rooms = parking_rooms;
            return this;
        }

        public Rooms build() {
            return new Rooms(this);
        }
    }
}
