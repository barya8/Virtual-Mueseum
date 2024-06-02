package com.company.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class PhotoDto {
    private Integer photoId;
    private Integer exhibitionId;
    private String fileName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;
    private String location;
    private String artist;
    private MultipartFile fileInput;

    // getters and setters

    public Integer getExhibitionId() {
        return exhibitionId;
    }

    public PhotoDto setExhibitionId(Integer exhibitionId) {
        this.exhibitionId = exhibitionId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public PhotoDto setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PhotoDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public PhotoDto setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public PhotoDto setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public PhotoDto setArtist(String artist) {
        this.artist = artist;
        return this;
    }


    public MultipartFile getFileInput() {
        return fileInput;
    }

    public PhotoDto setFileInput(MultipartFile fileInput) {
        this.fileInput = fileInput;
        return this;
    }
}
