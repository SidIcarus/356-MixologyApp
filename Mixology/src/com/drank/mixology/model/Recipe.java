package com.drank.mixology.model;

import java.util.Date;

/**
 * Created by Chris on 3/21/14.
 */
public class Recipe {

    private boolean favorite;
    private boolean isDefault;

    private int difficulty;

    private double alcoholContent;
    private double rating;
    private double totalVolume;

    private Date lastMade;
    private String imageFile;
    private String name;
    private String volumeUnits;
    private String description;

    public Recipe(String name, String imageFile, double totalVolume, String volumeUnits,
                  double alcoholContent, double rating, int difficulty, boolean favorite,
                  boolean isDefault, Date lastMade, String description) {
        this.name = name;
        this.imageFile = imageFile;
        this.totalVolume = totalVolume;
        this.volumeUnits = volumeUnits;
        this.alcoholContent = alcoholContent;
        this.rating = rating;
        this.difficulty = difficulty;
        this.favorite = favorite;
        this.isDefault = isDefault;
        this.lastMade = lastMade;
        this.description = description;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Date getLastMade() {
        return lastMade;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public String getVolumeUnits() {
        return volumeUnits;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
