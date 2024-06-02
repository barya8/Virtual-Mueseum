package com.company.controller;

import com.company.dto.ExhibitionResponse;
import com.company.dto.PhotoDto;
import com.company.dto.ServiceResult;
import com.company.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<ServiceResult> handleFileUpload(@RequestParam("fileInput") MultipartFile fileInput,
                                                          @RequestParam("exhibitionId") Integer exhibitionId,
                                                          @RequestParam("fileName") String fileName,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("uploadDate") Date uploadDate,
                                                          @RequestParam("location") String location,
                                                          @RequestParam("artist") String artist) {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setFileInput(fileInput);
        photoDto.setExhibitionId(exhibitionId);
        photoDto.setFileName(fileName);
        photoDto.setDescription(description);
        photoDto.setUploadDate(uploadDate);
        photoDto.setLocation(location);
        photoDto.setArtist(artist);

        ServiceResult result = photoService.uploadPhoto(photoDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/get-exhibition-data/{exhibitionId}")
    public ResponseEntity<ExhibitionResponse> getExhibitionData(@PathVariable Integer exhibitionId) {
        return photoService.getExhibitionData(exhibitionId);
    }
}
