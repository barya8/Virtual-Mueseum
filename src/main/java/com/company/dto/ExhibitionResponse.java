package com.company.dto;

import com.company.model.Photo;

import java.util.List;

public class ExhibitionResponse {
    private List<Photo> imagesList;

    public ExhibitionResponse(List<Photo> imagesList) {
        this.imagesList = imagesList;
    }

    public List<Photo> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Photo> imagesList) {
        this.imagesList = imagesList;
    }
}
