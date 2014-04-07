package com.drank.mixology.model;

import java.util.Date;

/**
 * Created by Chris on 3/21/14.
 */
public class Recipe {

    private String name;
    private String imageFile;
    private double totalVolume;
    private String volumeUnits;
    private double alcoholContent;
    private double rating;
    private int difficulty;
    private boolean favorite;
    private boolean isDefault;
    private Date lastMade;

    public Recipe(String name, String imageFile, double totalVolume, String volumeUnits,
                  double alcoholContent, double rating, int difficulty, boolean favorite,
                  boolean isDefault, Date lastMade) {
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
    }

    public String getName() {
        return name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public String getVolumeUnits() {
        return volumeUnits;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public double getRating() {
        return rating;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public Date getLastMade() {
        return lastMade;
    }
}
