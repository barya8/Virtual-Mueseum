package com.company.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photoId")
    private Integer photoId;
    @Column(name = "exhibitionId")
    private Integer exhibitionId;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "description")
    private String description;
    @Column(name = "uploadDate")
    private Date uploadDate;
    @Column(name = "location")
    private String location;
    @Column(name = "artist")
    private String artist;

    @Lob
    @Column(name = "base64Data")
    private String imageData;

    public Integer getPhotoId() {
        return photoId;
    }

    public Photo setPhotoId(Integer photoId) {
        this.photoId = photoId;
        return this;
    }

    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public Photo setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Photo setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Photo setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public Photo setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Photo setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public Photo setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getImageData() {
        return imageData;
    }

    public Photo setImageData(String imageData) {
        this.imageData = imageData;
        return this;
    }
}
