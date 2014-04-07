package com.drank.mixology.model;

/**
 * Created by Chris on 3/21/14.
 */
public class Ingredient {

    private String name;
    private String imageFile;
    private double totalVolume;
    private String volumeUnits;
    private double alcoholContent;
    private boolean isDefault;
    private String category;

    public Ingredient(String name, String imageFile, double totalVolume, String volumeUnits,
                  double alcoholContent, boolean isDefault, String category) {
        this.name = name;
        this.imageFile = imageFile;
        this.totalVolume = totalVolume;
        this.volumeUnits = volumeUnits;
        this.alcoholContent = alcoholContent;
        this.isDefault = isDefault;
        this.category = category;
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

    public boolean isDefault() {
        return isDefault;
    }

    public String getCategory() { return category; }
}
