package dev.onekode.adronhomes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Apartment extends Model implements Parcelable {
    public static final Creator<Apartment> CREATOR = new Creator<Apartment>() {
        @Override
        public Apartment createFromParcel(Parcel in) {
            return new Apartment(in);
        }

        @Override
        public Apartment[] newArray(int size) {
            return new Apartment[size];
        }
    };

    private String id;
    private String name;
    private String propertyType;
    private String tenureType;
    private String description;
    private Rooms rooms;
    private String price;
    private String location;
    private Double rating;

    public Apartment() {
    }

    private Apartment(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.propertyType = builder.propertyType;
        this.tenureType = builder.tenureType;
        this.description = builder.description;
        this.rooms = builder.rooms;
        this.price = builder.price;
        this.location = builder.location;
        this.rating = builder.rating;
    }

    // Parcelable Impl
    protected Apartment(Parcel in) {
        id = in.readString();
        name = in.readString();
        propertyType = in.readString();
        tenureType = in.readString();
        description = in.readString();
        rooms = in.readParcelable(Rooms.class.getClassLoader());
        price = in.readString();
        location = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(propertyType);
        dest.writeString(tenureType);
        dest.writeString(description);
        dest.writeParcelable(rooms, flags);
        dest.writeString(price);
        dest.writeString(location);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getTenureType() {
        return tenureType;
    }

    public String getDescription() {
        return description;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public Double getRating() {
        return rating;
    }

    public static class Builder {
        private String id;
        private String name;
        private String propertyType;
        private String tenureType;
        private String description;
        private Rooms rooms;
        private String price;
        private String location;
        private Double rating;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPropertyType(String propertyType) {
            this.propertyType = propertyType;
            return this;
        }

        public Builder setTenureType(String tenureType) {
            this.tenureType = tenureType;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setRooms(Rooms rooms) {
            this.rooms = rooms;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setRating(Double rating) {
            this.rating = rating;
            return this;
        }

        public Apartment build() {
            return new Apartment(this);
        }
    }
}
