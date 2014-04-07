package com.drank.mixology.model;

/**
 * Created by Chris on 3/21/14.
 */
public class Ingredient {

    private boolean isDefault;

    private double alcoholContent;
    private double totalVolume;

    private String category;
    private String imageFile;
    private String name;
    private String volumeUnits;

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

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public String getCategory() {
        return category;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getName() {
        return name;
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
}
